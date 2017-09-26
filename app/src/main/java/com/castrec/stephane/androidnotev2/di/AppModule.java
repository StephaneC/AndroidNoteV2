package com.castrec.stephane.androidnotev2.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.castrec.stephane.androidnotev2.api.MessagesApi;
import com.castrec.stephane.androidnotev2.db.AppDatabase;
import com.castrec.stephane.androidnotev2.db.dao.MessageDao;
import com.castrec.stephane.androidnotev2.db.messages.MessagesDB;
import com.castrec.stephane.androidnotev2.repositories.MessageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sca on 25/09/17.
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    MessagesApi provideMessagesApi(AppDatabase db) {
        return new MessagesApi();
    }


    @Singleton
    @Provides
    MessageRepository provideMessagesRepos(MessagesApi api, MessageDao dao) {
        return new MessageRepository(api, dao);
    }

    @Singleton @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    MessageDao provideMessagesDao(AppDatabase db) {
        return db.messageDao();
    }
}
