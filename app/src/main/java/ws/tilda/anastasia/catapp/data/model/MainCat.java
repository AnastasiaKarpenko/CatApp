package ws.tilda.anastasia.catapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MainCat {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cat_id")
    private String mCatId;

    @ColumnInfo(name = "favorite_id")
    private int mFavoriteId;

    @ColumnInfo(name = "url")
    private String mPhotoUrl;

    public MainCat() {
        mFavoriteId = 0;
    }

    public String getCatId() {
        return mCatId;
    }

    public void setCatId(String catId) {
        mCatId = catId;
    }

    public int getFavoriteId() {
        return mFavoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        mFavoriteId = favoriteId;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }
}
