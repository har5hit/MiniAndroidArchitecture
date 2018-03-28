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

   /* @Provides
    @Singleton
    public <T> T provideService(Class<T> service) {
        return provideRetrofit().create(service);
    }*/

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

        /*  final String token=SharedPrefs.getPrefs().getString("token");
        if(!token.isEmpty())
        {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer "+token)
                            .addHeader("Accept", ""); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }else {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", ""); // <-- this is the important line

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }*/

        return httpClient.build();
    }
}
