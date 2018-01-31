package com.example.franciscoandrade.gitdroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.gitdroid.OpenHelper.SQLite_OpenHelper;
import com.example.franciscoandrade.gitdroid.restApi.EndPointApi;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObject;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObjectRepos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
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
    List<RootObjectRepos> reposList;
    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "DB1", null, 1);
    LinearLayout getInfo;
    String stringToBeInserted, datajson, reposJson, loginName;
    Gson gson = new Gson();
    RootObject rootObject;


    //Todo: add click listeners to following and followers, when click show its info in the info page
    //Todo: allow user to click on the repos to get more data about it

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
        getInfo = (LinearLayout) findViewById(R.id.getInfo);

        repoAdapter = new RepoAdapter(this);
        recyclerContainer.setAdapter(repoAdapter);
        recyclerContainer.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerContainer.setLayoutManager(linearLayoutManager);
        recyclerContainer.setNestedScrollingEnabled(false);
        Bundle extras = getIntent().getExtras();
        String userName = extras.getString("username");

        //ADD next if user already exec counter>0  load from db
        //else load from network (make retrofit call)

        if (helper.verifyUsrPas(userName).getCount() > 0) {
            //load from db
            Log.d("LOADDB", "onCreate: ");
            loadDataFromDB(userName);
        } else {
            //load from retrofit
            Log.d("LOADRETRO", "onCreate: ");
            retrofitUser();
            obtenerDatos(userName);
        }

        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("result", stringToBeInserted);
                //datajson=gson.toJson(datajson);
                bundle.putString("result", datajson);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void retrofitUser() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void loadDataFromDB(String username) {
        Log.d("INDB==", "loadDataFromDB: " + helper.verifyUsrPas(username).getString(3));

        Gson gson = new Gson();
        String result = helper.verifyUsrPas(username).getString(3);
        RootObject userObject = gson.fromJson(result, RootObject.class);
        Picasso.with(getApplication()).load(userObject.getAvatar_url())
                .transform(new CropCircleTransformation()).into(profile_image);
        profileName.setText(String.valueOf(userObject.getLogin()));
        followerTV.setText(String.valueOf(userObject.getFollowers()));
        followingTV.setText(String.valueOf(userObject.getFollowing()));
        publicTV.setText("Public Repos: " + String.valueOf(userObject.getPublic_repos()));

        String resultRepos = helper.verifyUsrPas(username).getString(2);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson1 = gsonBuilder.create();
        RootObjectRepos[] rootObjects = gson1.fromJson(resultRepos, RootObjectRepos[].class);
        reposList = Arrays.asList(rootObjects);
        repoAdapter.addImages(reposList);
        //missing load image with picaso
    }

    private void obtenerDatos(final String username) {

        EndPointApi service = retrofit.create(EndPointApi.class);
        Call<RootObject> response = service.getUser(username);
        response.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if (response.isSuccessful()) {
                    rootObject = null;
                    rootObject = response.body();
                    Picasso.with(getApplication()).load(rootObject.getAvatar_url())
                            .transform(new CropCircleTransformation()).into(profile_image);
                    profileName.setText(String.valueOf(rootObject.getLogin()));
                    followerTV.setText(String.valueOf(rootObject.getFollowers()));
                    followingTV.setText(String.valueOf(rootObject.getFollowing()));
                    publicTV.setText("Public Repos: " + String.valueOf(rootObject.getPublic_repos()));
                    // Write into DB
                    datajson = gson.toJson(response.body());
                    stringToBeInserted = rootObject.toString();
                    //result= rootObject;
                    loginName = rootObject.getLogin();
                    //helper.closeDB();
                    // insert or update the DB
                    helper.insertUserData(loginName.toLowerCase());

                    SQLiteDatabase db = helper.getWritableDatabase();
                    //Find person by ID
                    String[] parameters = {loginName.toLowerCase()};
                    ContentValues values = new ContentValues();
                    //add values with key value .put(name as key, data to be changed)
                    //values.put("Name", loginName);
                    values.put("Data", datajson);
                    //Samething for phone field
                    //update takes care of process
                    db.update("gituser", values, "Name" + "=?", parameters);
                    Toast.makeText(getApplicationContext(), "User Updated!", Toast.LENGTH_SHORT).show();
//                    db.close();
                } else {
                    Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Log.d("FAILDATA", "onFailure: " + t.getMessage());
            }
        });


        Call<List<RootObjectRepos>> response2 = service.getRepos(username);
        response2.enqueue(new Callback<List<RootObjectRepos>>() {
            @Override
            public void onResponse(Call<List<RootObjectRepos>> call, Response<List<RootObjectRepos>> response) {
                if (response.isSuccessful()) {
                    reposList = response.body();
                    repoAdapter.addImages(reposList);
                    reposJson = gson.toJson(response.body());
                    //helper.openDB();
                    //helper.insertUserRepos(String.valueOf(rootObject.getLogin()), reposJson);
                    //helper.closeDB();
                    //Open DB
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //Find person by ID
                    String[] parameters = {loginName.toLowerCase()};
                    ContentValues values = new ContentValues();
                    //add values with key value .put(name as key, data to be changed)
                    values.put("Repos", reposJson);
                    //Samething for phone field
                    //update takes care of process
                    db.update("gituser", values, "Name" + "=?", parameters);
                    Toast.makeText(getApplicationContext(), "User Updated!", Toast.LENGTH_SHORT).show();
                    db.close();

                } else {
                    Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<RootObjectRepos>> call, Throwable t) {
                Log.d("FAILREPOS", "onFailure: " + t.getMessage());
            }
        });
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
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
