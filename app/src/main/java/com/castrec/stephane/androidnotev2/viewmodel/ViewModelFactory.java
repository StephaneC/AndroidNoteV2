package com.castrec.stephane.androidnotev2.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.castrec.stephane.androidnotev2.TchatApp;
import com.castrec.stephane.androidnotev2.di.AppComponent;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by sca on 25/09/17.
 */
@Singleton
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final TchatApp application;

    @Inject
    public ViewModelFactory(TchatApp application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof AppComponent.Injectable) {
            ((AppComponent.Injectable) t).inject(application.getAppComponent());
        }
        return t;
    }
}
