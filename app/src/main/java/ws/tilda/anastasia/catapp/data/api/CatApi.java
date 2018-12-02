package ws.tilda.anastasia.catapp.data.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ws.tilda.anastasia.catapp.BuildConfig;
import ws.tilda.anastasia.catapp.data.model.Cat;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

public interface CatApi {
    @Headers({"Content-Type: application/json", "x-api-key: " + BuildConfig.API_KEY})
    @GET("/v1/images/search")
    Single<List<Cat>> getAllCats(@Query("size") String size,
                                 @Query("order") String order,
                                 @Query("page") int page,
                                 @Query("limit") int limit);

    @Headers({"Content-Type: application/json", "x-api-key: " + BuildConfig.API_KEY})
    @GET("/v1/images/{image_id}")
    Call<Cat> getCat(
            @Path("image_id") String imageId,
            @Query("size") String size,
            @Query("include_favourite") boolean includeFavorite);


    @Headers({"Content-Type: application/json", "x-api-key: " + BuildConfig.API_KEY})
    @GET("/v1/favourites")
    Single<List<FavoriteCat>> getFavoriteCats();

//    @POST("/v1/favourites")
//    void setAsFavorite(@Body String catId);


}
