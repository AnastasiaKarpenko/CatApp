package ws.tilda.anastasia.catapp.network;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ws.tilda.anastasia.catapp.BuildConfig;
import ws.tilda.anastasia.catapp.model.Cat;
import ws.tilda.anastasia.catapp.model.CatResponse;

public interface CatApi {
    @Headers({
            "Content-Type: application/json",
            "x-api-key: " + BuildConfig.API_KEY
    })
    @GET("/v1/images/search")
    CatResponse getAllCats(@Query("size") String size,
                           @Query("order") String order,
                           @Query("page") int page,
                           @Query("limit") int limit);

    @GET("/v1/images/:{image_id}")
    Cat getSpecificCat(
            @Path("image_id") String imageId,
            @Query("size") String size,
            @Query("include_favourite") String includeFavorite);
}
