package io.github.fatimazza.fbmatchschedule.network;

import io.github.fatimazza.fbmatchschedule.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;

    private TheSportDBApi sportDBApi;

    private RetrofitHelper() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(setupLoggingInterceptor().build())
                .build();
        sportDBApi = mRetrofit.create(TheSportDBApi.class);
    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    public TheSportDBApi getMoviesServices() {
        return sportDBApi;
    }

    private OkHttpClient.Builder setupLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return httpClient;
    }

}