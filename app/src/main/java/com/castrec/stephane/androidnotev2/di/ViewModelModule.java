package com.castrec.stephane.androidnotev2.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.castrec.stephane.androidnotev2.viewmodel.MessageListViewModel;
import com.castrec.stephane.androidnotev2.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by sca on 25/09/17.
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MessageListViewModel.class)
    abstract ViewModel bindMessagesListViewModel(MessageListViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}