package com.castrec.stephane.androidnotev2.di;

import android.app.Application;

import com.castrec.stephane.androidnotev2.MainActivity;
import com.castrec.stephane.androidnotev2.TchatApp;
import com.castrec.stephane.androidnotev2.fragment.MessagesFragment;
import com.castrec.stephane.androidnotev2.viewmodel.MessageListViewModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by sca on 25/09/17.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MessageListViewModel messageListViewModel);

    interface Injectable {
        void inject(AppComponent appComponent);
    }
}