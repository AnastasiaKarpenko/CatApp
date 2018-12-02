package ws.tilda.anastasia.catapp.ui.allcats;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

public class CatListItemViewModel {
    private String mCatPhotoUrl;
    private String mCatId;

    public CatListItemViewModel(Cat cat) {
        mCatPhotoUrl = cat.getUrl();
        mCatId = cat.getId();
    }

    public CatListItemViewModel(FavoriteCat cat) {
        mCatPhotoUrl = cat.getImageId();
        mCatId = cat.getImageId();
    }

    public String getCatPhotoUrl() {
        return mCatPhotoUrl;
    }

    public String getCatId() {
        return mCatId;
    }
}
