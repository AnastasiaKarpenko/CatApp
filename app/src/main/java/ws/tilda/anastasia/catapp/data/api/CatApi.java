package ws.tilda.anastasia.catapp.data.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ws.tilda.anastasia.catapp.BuildConfig;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

public interface CatApi {
    @Headers({
            "Content-Type: application/json",
            "x-api-key: " + BuildConfig.API_KEY
    })
    @GET("/v1/images/search")
    Call<List<Cat>> getAllCats(@Query("size") String size,
                               @Query("order") String order,
                               @Query("page") int page,
                               @Query("limit") int limit);

    @GET("/v1/images/{image_id}")
    Call<Cat> getSpecificCat(
            @Path("image_id") String imageId,
            @Query("size") String size,
            @Query("include_favourite") boolean includeFavorite);

    @GET("/v1/favourites")
    Call<List<FavoriteCat>> getFavoriteCats();

    @GET("/v1/favourites/{favourite_id}")
    Call<FavoriteCat> getFavoriteCat(@Path("favourite_id") String favoriteId);

//    @POST("/v1/favourites")
//    void setAsFavorite(@Body String catId);


}
