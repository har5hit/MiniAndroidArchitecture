package com.justadeveloper96.helpers.network;

import android.support.annotation.Nullable;

import com.justadeveloper96.helpers.arch.Resource;
import com.shaadi.android.data.retrofitwrapper.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public abstract class NetworkHandlerCustom<T,E>{
    public Resource<E> transformedResponse;

    public NetworkHandler<T> handler;


    public NetworkHandlerCustom(Call<T> call) {
        handler=new NetworkHandler<T>(call);
    }

    public abstract E map(@Nullable T data);

    public Resource<E> getResult() {
        Resource<T> actualResponse = handler.getResult();
        transformedResponse=new Resource<E>(actualResponse.status,map(actualResponse.data),actualResponse.message);
        transformedResponse.code=actualResponse.code;
        return transformedResponse;
    }
}