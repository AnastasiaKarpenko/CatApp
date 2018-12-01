package ws.tilda.anastasia.catapp.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cat {

    @SerializedName("breeds")
    @Expose
    private List<Object> breeds = null;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    public List<Object> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Object> breeds) {
        this.breeds = breeds;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}