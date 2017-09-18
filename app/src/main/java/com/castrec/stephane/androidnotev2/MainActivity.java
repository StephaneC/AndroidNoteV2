package com.castrec.stephane.androidnotev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.castrec.stephane.androidnotev2.fragment.MessagesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            MessagesFragment fragment = new MessagesFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, MessagesFragment.TAG).commit();
        }
    }
    
    
}
