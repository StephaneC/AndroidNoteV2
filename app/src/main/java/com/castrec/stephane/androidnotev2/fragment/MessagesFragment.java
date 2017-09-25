package com.castrec.stephane.androidnotev2.fragment;

import com.castrec.stephane.androidnotev2.R;
import com.castrec.stephane.androidnotev2.adapters.MessageAdapter;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.viewmodel.MessageListViewModel;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sca on 06/06/15.
 */
public class MessagesFragment extends LifecycleFragment {

    public static final String TAG = "MessagesFragment";
    //UI
    SwipeRefreshLayout swipeLayout;
    RecyclerView recyclerView;
    MessageAdapter adapter;

    MessageListViewModel viewModel;

    //private ListFragmentBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(
                R.layout.fragment_messages, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.messages_list);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.messages_swiperefresh);
        setupRefreshLayout();
        setupRecyclerView();
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        loading();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                ViewModelProviders.of(this).get(MessageListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(MessageListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> messages) {
                swipeLayout.setRefreshing(false);
                if (messages != null) {
                    //mBinding.setIsLoading(false);
                    adapter.setMessages(messages);
                } else {
                    //mBinding.setIsLoading(true);
                }
            }
        });
    }

    /**
     * Load messages
     */
    private void loading() {
        swipeLayout.setRefreshing(true);
        //ask for an update
        viewModel.getMessages();
    }

    /**
     * Setup refresh layout
     */
    private void setupRefreshLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading();
            }
        });
        //swipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);
    }

    /**
     * Setup recycler view.
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new MessageAdapter(this.getActivity());
        recyclerView.setAdapter(adapter);
        
        // Add this. 
        // Two scroller could have problem. 
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }
}
