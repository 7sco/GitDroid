package com.example.franciscoandrade.gitdroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.gitdroid.restApi.ConstantsRestApi;
import com.example.franciscoandrade.gitdroid.restApi.EndPointApi;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObject;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObjectRepos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends AppCompatActivity {
    Retrofit retrofit;

    FrameLayout fragmentContainer;
    RecyclerView recyclerContainer;
    RepoAdapter repoAdapter;
    CircleImageView profile_image;
    TextView profileName, followerTV, followingTV, publicTV;

    List<RootObject> userList = new ArrayList<>();
    List<RootObjectRepos> reposList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        showToolBar("", true);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        recyclerContainer = (RecyclerView) findViewById(R.id.recyclerContainer);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        profileName = (TextView) findViewById(R.id.profileName);
        followerTV = (TextView) findViewById(R.id.followerTV);
        followingTV = (TextView) findViewById(R.id.followingTV);
        publicTV = (TextView) findViewById(R.id.publicTV);
        repoAdapter = new RepoAdapter(this);
        recyclerContainer.setAdapter(repoAdapter);
        recyclerContainer.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerContainer.setLayoutManager(linearLayoutManager);

        Bundle extras = getIntent().getExtras();
        String userNane = extras.getString("username");

        retrofitUser();
        obtenerDatos(userNane);
    }

    private void retrofitUser() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private void obtenerDatos(String username) {

        EndPointApi service = retrofit.create(EndPointApi.class);
        Call<RootObject> response = service.getUser(username);
        response.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if (response.isSuccessful()) {
                    RootObject rootObject = response.body();
                    Picasso.with(getApplication()).load(rootObject.getAvatar_url())
                            .transform(new CropCircleTransformation()).into(profile_image);
                    profileName.setText(String.valueOf(rootObject.getLogin()));
                    followerTV.setText(String.valueOf(rootObject.getFollowers()));
                    followingTV.setText(String.valueOf(rootObject.getFollowing()));
                    publicTV.setText("Public Repos: " + String.valueOf(rootObject.getPublic_repos()));
                } else {
                    Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {

            }
        });


        Call<List<RootObjectRepos>> response2 = service.getRepos(username);
        response2.enqueue(new Callback<List<RootObjectRepos>>() {
            @Override
            public void onResponse(Call<List<RootObjectRepos>> call, Response<List<RootObjectRepos>> response) {
                if (response.isSuccessful()) {
                    reposList = response.body();
                    repoAdapter.addImages(reposList);
                } else {
                    Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<RootObjectRepos>> call, Throwable t) {

            }
        });


//
    }

    private void showToolBar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
