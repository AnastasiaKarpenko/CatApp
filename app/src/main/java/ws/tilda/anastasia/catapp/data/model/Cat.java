package ws.tilda.anastasia.catapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Cat {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String id;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    private String url;

    @ColumnInfo(name = "breeds")
    @SerializedName("breeds")
    @Expose
    private List<Object> breeds = null;

    @ColumnInfo(name = "categories")
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;

    public List<Object> getBreeds() {
        return breeds;
    }

    public void setBreeds(@NonNull List<Object> breeds) {
        this.breeds = breeds;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(@NonNull List<Object> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

}