package com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.R;
import com.example.franciscoandrade.gitdroid.restApi.EndPointApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private List<FollowersModel> modelList = new ArrayList<>();
    private View rootView;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_info, container, false);
        // Inflate the layout for this fragment
        setRetrofit();
        return rootView;
    }

    public void setRetrofit() {
        Bundle bundle = getArguments();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointApi endPointApi = retrofit.create(EndPointApi.class);
        Call<List<FollowersModel>> following = endPointApi.getFollowings(bundle.getString("following"));
        following.enqueue(new Callback<List<FollowersModel>>() {
            @Override
            public void onResponse(Call<List<FollowersModel>> call, Response<List<FollowersModel>> response) {
                modelList = response.body();
                setUpRecyclerView();

            }

            @Override
            public void onFailure(Call<List<FollowersModel>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    public void setUpRecyclerView() {
        RecyclerView recyclerView = rootView.findViewById(R.id.followingRecyclerView);
        FollowersAdapter followersAdapter = new FollowersAdapter(modelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(followersAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
