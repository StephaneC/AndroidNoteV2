package com.castrec.stephane.androidnotev2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.castrec.stephane.androidnotev2.fragment.MessagesFragment;
import com.castrec.stephane.androidnotev2.helper.NetworkHelper;
import com.castrec.stephane.androidnotev2.model.HttpResult;
import com.castrec.stephane.androidnotev2.session.Session;
import com.castrec.stephane.androidnotev2.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    MessagesFragment messagesFragment;

    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (EditText) findViewById(R.id.tchat_msg);
        Button btn = (Button) findViewById(R.id.tchat_send);
        btn.setText("send !!");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(Constants.TAG, "CLICK SEND");
                //closeKeyboard();
                if (!message.getText().toString().isEmpty()) {
                    //post message
                    new SendMessageAsyncTask(view.getContext()).execute(message.getText().toString());
                } else {
                    message.setError(MainActivity.this
                            .getString(R.string.error_missing_msg));
                }
            }
        });



        // ((TchatApp) getApplication()).getAppComponent().inject(this);
        // Add product list fragment if this is first creation
        /*if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new MessagesFragment(), MessagesFragment.TAG).commit();
        }*/
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(message.getWindowToken(), 0);
    }

    /**
     * AsyncTask for sign-in
     */
    protected class SendMessageAsyncTask extends AsyncTask<String, Void, Integer> {

        Context context;

        public SendMessageAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                Map<String, String> p = new HashMap<>();
                p.put("message", params[0]);
                HttpResult result = NetworkHelper.doPost(context.getString(R.string.url_msg), p, Session.token);

                return result.status;
            } catch (Exception e) {
                Log.d(Constants.TAG, "Error occured in your AsyncTask : ", e);
                return 500;
            }
        }

        @Override
        public void onPostExecute(Integer status) {
            if (status != 200) {
                Toast.makeText(context, context.getString(R.string.error_send_msg), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, context.getString(R.string.send_msg_ok), Toast.LENGTH_SHORT).show();
                message.setText("");
            }
        }
    }
}
