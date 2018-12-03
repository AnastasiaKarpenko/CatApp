package ws.tilda.anastasia.catapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.MainCat;

@Database(entities = {Cat.class, MainCat.class}, version = 2, exportSchema = false)
public abstract class CatDatabase extends RoomDatabase {

    public abstract CatDao getCatDao();
}
