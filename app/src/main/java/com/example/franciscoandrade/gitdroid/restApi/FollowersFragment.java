package com.example.franciscoandrade.gitdroid.restApi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ListAdapter;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff.FollowersAdapter;
import com.example.franciscoandrade.gitdroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {
    private View rootView;
    private List<FollowersModel> followersModelList;


    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_followers, container, false);
        retrofitStuff();
        // Inflate the layout for this fragment
        return rootView;
    }
    public void retrofitStuff(){
        Bundle bundle= getArguments();
        Retrofit retrofit= new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/").build();
        final EndPointApi endPointApi=retrofit.create(EndPointApi.class);
        Call<List<FollowersModel>>  followers= endPointApi.getFollowers(bundle.getString("followers"));
        followers.enqueue(new Callback<List<FollowersModel>>() {
            @Override
            public void onResponse(Call<List<FollowersModel>> call, Response<List<FollowersModel>> response) {
                followersModelList=response.body();
                Log.d(TAG, "onResponse: "+ followersModelList.get(1).getUrl());
                recyclerViewstuff();

            }

            @Override
            public void onFailure(Call<List<FollowersModel>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
    public void recyclerViewstuff(){
        RecyclerView recyclerView= rootView.findViewById(R.id.followersRecyclerView);
        FollowersAdapter adapter= new FollowersAdapter(followersModelList);
        GridLayoutManager layoutManager= new GridLayoutManager(rootView.getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
