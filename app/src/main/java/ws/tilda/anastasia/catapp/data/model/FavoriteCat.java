package ws.tilda.anastasia.catapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteCat {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("sub_id")
    @Expose
    private Object subId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("image")
    @Expose
    private Image image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Object getSubId() {
        return subId;
    }

    public void setSubId(Object subId) {
        this.subId = subId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}