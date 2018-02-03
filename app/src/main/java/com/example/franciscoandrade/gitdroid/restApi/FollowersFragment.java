package com.example.franciscoandrade.gitdroid.restApi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff.FollowersAdapter;
import com.example.franciscoandrade.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private List<FollowersModel> followersModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FollowersAdapter adapter;


    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_followers, container, false);
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.followersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewstuff();
        retrofitStuff();
        Log.d(TAG, "onViewCreated: ");
    }

    public void retrofitStuff() {
        Bundle bundle = getArguments();
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                Log.d("message",message);
                            }
                        })).build();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .build();

        EndPointApi endPointApi = retrofit.create(EndPointApi.class);
        Call<List<FollowersModel>> followers = endPointApi.getFollowers(bundle.getString("followers"));
        followers.enqueue(new Callback<List<FollowersModel>>() {
            @Override
            public void onResponse(Call<List<FollowersModel>> call, Response<List<FollowersModel>> response) {

                Log.d(TAG, "onResponse: "+response.body().get(0).toString());

                adapter.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<FollowersModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                t.printStackTrace();

            }
        });
        Log.d(TAG, "retrofitStuff: ");
    }

    public void recyclerViewstuff() {
        adapter = new FollowersAdapter(followersModelList);
        recyclerView.setAdapter(adapter);
    }

}
