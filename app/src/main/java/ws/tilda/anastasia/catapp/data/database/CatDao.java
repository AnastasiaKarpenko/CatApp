package ws.tilda.anastasia.catapp.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.MainCat;

@Dao
public interface CatDao {

    @Query("select * from maincat")
    List<MainCat> getAllCats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCats(List<MainCat> cats);

}
