package ws.tilda.anastasia.catapp.ui.viewmodels;

import ws.tilda.anastasia.catapp.data.model.MainCat;

public class CatListItemViewModel {
    private String mCatPhotoUrl;
    private String mCatId;

    public CatListItemViewModel(MainCat cat) {
        mCatPhotoUrl = cat.getPhotoUrl();
        mCatId = cat.getCatId();
    }

    public String getCatPhotoUrl() {
        return mCatPhotoUrl;
    }

    public String getCatId() {
        return mCatId;
    }
}
