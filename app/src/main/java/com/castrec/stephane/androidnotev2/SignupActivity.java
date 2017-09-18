package com.castrec.stephane.androidnotev2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.castrec.stephane.androidnotev2.helper.NetworkHelper;
import com.castrec.stephane.androidnotev2.model.HttpResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sca on 02/06/15.
 */
public class SignupActivity extends Activity {

    EditText username;
    EditText pwd;
    ProgressBar pg;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.signup_username);
        pwd = (EditText) findViewById(R.id.signup_pwd);
        pg = (ProgressBar) findViewById(R.id.signup_pg);

        btn = (Button) findViewById(R.id.signup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading(true);
                new SignupAsyncTask().execute();
            }
        });
    }

    private void loading(boolean loading) {
        if(loading){
            pg.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
        } else {
            pg.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * AsyncTask for sign-in
     */
    protected class SignupAsyncTask extends AsyncTask<String, Void, HttpResult> {

        @Override
        protected HttpResult doInBackground(String... params) {
            if(!NetworkHelper.isInternetAvailable(SignupActivity.this)){
                //error
                return new HttpResult(500, null);
            }
            try {

                Map<String, String> p = new HashMap<>();
                p.put("username", params[0]);
                p.put("pwd", params[1]);

                HttpResult result = NetworkHelper.doPost(
                        SignupActivity.this.getString(R.string.url_signup), p, null);

                return result;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (Exception e) {
                Log.e("NetworkHelper", e.getMessage());
                return null;
            }
        }

        @Override
        public void onPostExecute(final HttpResult response){
            loading(false);
            if(response.status == 200){
                SignupActivity.this.finish();
            } else {
                Toast.makeText(SignupActivity.this,
                        SignupActivity.this.getString(R.string.error_signup),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
