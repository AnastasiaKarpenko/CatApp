package ws.tilda.anastasia.catapp.network;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ws.tilda.anastasia.catapp.BuildConfig;
import ws.tilda.anastasia.catapp.model.CatResponse;

public interface CatApi {
    @Headers({
            "Content-Type: application/json",
            "x-api-key: " + BuildConfig.API_KEY
    })
    @GET("/images/search")
    CatResponse getAllCats(@Query("size") String size,
                           @Query("mime_types") String mimeTypes,
                           @Query("format") String format,
                           @Query("order") String order,
                           @Query("page") int page,
                           @Query("limit") int limit);
}
