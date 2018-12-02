package ws.tilda.anastasia.catapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

@Database(entities = {Cat.class}, version = 1)
public abstract class CatDatabase extends RoomDatabase {

    public abstract CatDao getCatDao();
}
