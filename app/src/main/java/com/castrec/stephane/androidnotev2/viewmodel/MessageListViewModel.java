package com.castrec.stephane.androidnotev2.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.castrec.stephane.androidnotev2.R;
import com.castrec.stephane.androidnotev2.db.DatabaseCreator;
import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.helper.JsonParser;
import com.castrec.stephane.androidnotev2.helper.NetworkHelper;
import com.castrec.stephane.androidnotev2.model.HttpResult;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.session.Session;

import org.json.JSONException;

import java.util.List;

/**
 * Created by sca on 18/09/17.
 */

/**
 * Use AndroidViewModel instead of ViewModel because need
 * application context
 */
public class MessageListViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<List<MessageEntity>> mObservableProducts;


    public MessageListViewModel(Application application) {
        super(application);

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        mObservableProducts = Transformations.switchMap(databaseCreator.isDatabaseCreated(),
                new Function<Boolean, LiveData<List<MessageEntity>>>() {
                    @Override
                    public LiveData<List<MessageEntity>> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
                            //noinspection unchecked
                            return ABSENT;
                        } else {
                            //noinspection ConstantConditions
                            return databaseCreator.getDatabase().messageDao().loadAllMessages();
                        }
                    }
                });

        databaseCreator.createDb(this.getApplication());
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<MessageEntity>> getProducts() {
        return new NetworkBoundResource<List<Message>, List<Message>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RepoSearchResponse item) {
                List<Integer> repoIds = item.getRepoIds();
                RepoSearchResult repoSearchResult = new RepoSearchResult(
                        query, repoIds, item.getTotal(), item.getNextPage());
                db.beginTransaction();
                try {
                    messageDao.insertRepos(item.getItems());
                    repoDao.insert(repoSearchResult);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Message> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<Message>> loadFromDb() {
                return Transformations.switchMap(repoDao.search(query), searchData -> {
                    if (searchData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return repoDao.loadOrdered(searchData.repoIds);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RepoSearchResponse>> createCall() {
                return githubService.searchRepos(query);
            }

            @Override
            protected RepoSearchResponse processResponse(ApiResponse<RepoSearchResponse> response) {
                RepoSearchResponse body = response.body;
                if (body != null) {
                    body.setNextPage(response.getNextPage());
                }
                return body;
            }

        }.asLiveData();
        //return mObservableProducts;
    }

    public List<Message> loadMessagesFromServer() throws JSONException {
        HttpResult result = NetworkHelper.doGet(getApplication().getString(R.string.url_msg), null, Session.token);

        if(result.status == 200) {
            // Convert the InputStream into a string
            return JsonParser.getMessages(result.json);
        }
        return null;
    }
}
