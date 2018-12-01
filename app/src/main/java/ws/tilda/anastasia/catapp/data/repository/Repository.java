package ws.tilda.anastasia.catapp.data.repository;

import java.util.List;

import ws.tilda.anastasia.catapp.data.database.CatDao;
import ws.tilda.anastasia.catapp.data.model.Cat;

public class Repository {
    private CatDao mCatDao;

    public Repository(CatDao catDao) {
        mCatDao = catDao;
    }

    public List<Cat> getAllCats() {
        List<Cat> cats = mCatDao.getAllCats();
        return cats;
    }

    public void insertCats(List<Cat> cats) {
        mCatDao.insertCats(cats);

    }

    public interface RepositoryOwner {
        Repository obtainRepository();
    }
}
