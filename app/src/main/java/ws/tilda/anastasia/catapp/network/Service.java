package ws.tilda.anastasia.catapp.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ws.tilda.anastasia.catapp.BuildConfig;

public class Service {
//    public static String apiBaseUrl = "http://biotope.serval.uni.lu:8383/";
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(apiBaseUrl);
//
//    private static Retrofit retrofit = builder.build();
//
//    private static HttpLoggingInterceptor logging =
//            new HttpLoggingInterceptor()
//                    .setLevel(HttpLoggingInterceptor.Level.BODY);
//
//    private static OkHttpClient.Builder httpClient =
//            new OkHttpClient.Builder();
//
//    public static <S> S createService(
//            Class<S> serviceClass) {
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging);
//        }
//
//        OkHttpClient okHttpClient = httpClient.connectTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .build();
//
//        builder.client(okHttpClient);
//
//        retrofit = builder.build();
//
//        return retrofit.create(serviceClass);
//    }

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
