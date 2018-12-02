package ws.tilda.anastasia.catapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ws.tilda.anastasia.catapp.data.model.Cat;

@Database(entities = {Cat.class}, version = 1, exportSchema = false)
public abstract class CatDatabase extends RoomDatabase {

    public abstract CatDao getCatDao();
}
