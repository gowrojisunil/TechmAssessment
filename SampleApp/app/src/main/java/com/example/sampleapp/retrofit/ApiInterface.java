package com.example.sampleapp.retrofit;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import com.example.sampleapp.model.FactModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Call<FactModel> getFacts(@Url String url);

}
