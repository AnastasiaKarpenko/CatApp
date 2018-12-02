package ws.tilda.anastasia.catapp.ui.allcats;

import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

public class CatListItemViewModel {
    private String mCatPhotoUrl;

    public CatListItemViewModel(Cat cat) {
        mCatPhotoUrl = cat.getUrl();
    }

    public CatListItemViewModel(FavoriteCat cat) {
        mCatPhotoUrl = cat.getImageId();
    }

    public String getCatPhotoUrl() {
        return mCatPhotoUrl;
    }
}
