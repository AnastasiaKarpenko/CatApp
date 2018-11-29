package ws.tilda.anastasia.catapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CatResponse implements Serializable {

    @SerializedName("cats")
    private List<Cat> mCats;

    public List<Cat> getCats() {
        return mCats;
    }

    public void setProjects(List<Cat> cats) {
        mCats = cats;
    }
}
