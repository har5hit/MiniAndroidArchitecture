package com.justadeveloper96.helpers.di;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harshith on 20/1/17.
 */
@Module
public class RetrofitModule{
    String baseUrl;
    List<Interceptor> interceptors;
    List<Interceptor> networkInterceptors;

    public RetrofitModule(String baseUrl,List<Interceptor> interceptors,List<Interceptor> networkInterceptors) {
        this.baseUrl = baseUrl;
        this.interceptors=interceptors;
        this.networkInterceptors=networkInterceptors;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit.Builder builer= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
        return builer.build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOKHTTP(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (interceptors!=null)
        {
            for (Interceptor interceptor : interceptors) {
                httpClient.addInterceptor(interceptor);
            }
        }

        if (networkInterceptors!=null)
        {
            for (Interceptor networkInterceptor : networkInterceptors) {
                httpClient.addNetworkInterceptor(networkInterceptor);
            }
        }
        return httpClient.build();
    }
}
