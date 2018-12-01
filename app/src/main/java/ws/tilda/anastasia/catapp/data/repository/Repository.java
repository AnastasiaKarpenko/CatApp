package ws.tilda.anastasia.catapp.data.repository;

import ws.tilda.anastasia.catapp.data.database.CatDao;

public class Repository {
    private CatDao mCatDao;

    public Repository(CatDao catDao) {
        mCatDao = catDao;
    }
}
