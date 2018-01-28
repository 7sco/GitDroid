package com.example.franciscoandrade.gitdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    TextView usernameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTV= (TextView)findViewById(R.id.usernameTV);

    }

    public void onClick(View view) {
        if(!TextUtils.isEmpty(usernameTV.getText())) {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Save to DB");
            builder.setMessage("Do you want to save this usert to the DB");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    intent.putExtra("username", usernameTV.getText().toString());
                    startActivity(intent);
                    usernameTV.setText("");
                }
            });
            builder.setNegativeButton("NO!!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    usernameTV.setText("");
                    Toast.makeText(getApplicationContext(), "Select new Username to search", Toast.LENGTH_SHORT).show();

                }
            });
            builder.create();
            builder.show();
        }
        else {
            Toast.makeText(this, "Empty Field, Enter text! ",Toast.LENGTH_SHORT).show();
        }
    }
}
