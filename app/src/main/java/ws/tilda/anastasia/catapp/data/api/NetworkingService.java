package ws.tilda.anastasia.catapp.data.api;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.tilda.anastasia.catapp.BuildConfig;

public class NetworkingService {

    private static OkHttpClient sClient;
    private static Retrofit sRetrofit;
    private static Gson sGson;
    private static CatApi sApi;


    private static OkHttpClient getClient() {
        if (sClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            sClient = builder.build();
        }
        return sClient;
    }

    private static Retrofit getRetrofit() {
        if (sGson == null) {
            sGson = new Gson();
        }
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .build();
        }
        return sRetrofit;
    }

    public static CatApi getApiService() {
        if (sApi == null) {
            sApi = getRetrofit().create(CatApi.class);
        }
        return sApi;
    }

}
