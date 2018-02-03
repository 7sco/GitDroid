package com.example.franciscoandrade.gitdroid.restApi;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObject;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObjectRepos;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by franciscoandrade on 12/26/17.
 */

public interface EndPointApi {

    @GET("users/{user}")
    retrofit2.Call<RootObject> getUser(@Path("user") String user);
    @GET("users/{user}/repos?sort=updated")
    retrofit2.Call<List<RootObjectRepos>> getRepos(@Path("user") String user);

    @GET("users/{user}/followers")
    Call<List<FollowersModel>> getFollowers(@Path("user") String user);



}
