package ws.tilda.anastasia.catapp;

import android.app.Application;
import android.arch.persistence.room.Room;

import ws.tilda.anastasia.catapp.data.database.CatDatabase;
import ws.tilda.anastasia.catapp.data.repository.Repository;

public class MyApplication extends Application {

    private Repository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        final CatDatabase database = Room.databaseBuilder(this, CatDatabase.class, "cat_database")
                .fallbackToDestructiveMigration()
                .build();

        mRepository = new Repository(database.getCatDao());
    }

    public Repository getRepository() {
        return mRepository;
    }
}
