package ws.tilda.anastasia.catapp.data.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import ws.tilda.anastasia.catapp.data.database.CatDao;
import ws.tilda.anastasia.catapp.data.model.MainCat;

public class Repository {
    private CatDao mCatDao;

    public Repository(CatDao catDao) {
        mCatDao = catDao;
    }

    public List<MainCat> getAllCats() {
        List<MainCat> cats = mCatDao.getAllCats();
        return cats;
    }

    public void insertCats(List<MainCat> cats) {
        mCatDao.insertCats(cats);

    }

    public LiveData<List<MainCat>> getAllCatsLive() {
        LiveData<List<MainCat>> cats = mCatDao.getAllCatsLive();
        return cats;
    }

    public interface RepositoryOwner {
        Repository obtainRepository();
    }
}
