package com.justadeveloper96.helpers.network;

import com.justadeveloper96.helpers.arch.Resource;
import com.shaadi.android.data.retrofitwrapper.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class NetworkHandler<T> {
    Call<T> call;
    public Resource<T> response;
    public NetworkHandler(Call<T> call)
    {
        this.call=call;
    }
    public void onSuccess(Response<T> data){
        response= Resource.success(data.body());
    }
    public void onUnSuccessful(Response<T> data){
        response= Resource.unsuccessful(ApiUtils.parseError(data.errorBody()),null);
    }

    public void onFail(Exception e) {
        response= Resource.error(ApiUtils.getErrorMessage(e),null);
    }

    public void run(){
        try {
            Response<T> data=call.execute();
            if (data.isSuccessful())
            {
                onSuccess(data);
            }else {
                onUnSuccessful(data);
            }
            setResponseCode(data.code());
        } catch (Exception e) {
            e.printStackTrace();
            onFail(e);
        }
    }

    protected void setResponseCode(int code) {
        response.code=code;
    }

    public Resource<T> getResult() {
        run();
        return response;
    }
}