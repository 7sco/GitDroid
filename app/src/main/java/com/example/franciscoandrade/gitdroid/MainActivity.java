package com.example.franciscoandrade.gitdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.gitdroid.OpenHelper.SQLite_OpenHelper;
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

    TextInputEditText usernameTV;
    Button checkBtn;
    TextView tectChange;

    //Todo: Show user from database in the droplist(Spinner)
    //Todo: allow user to modify data
    //Todo: Only ask user to save data if data is not in DB
    //Todo: if user chooses yes save data, else hust how content


    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "DB1", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTV= (TextInputEditText) findViewById(R.id.usernameTV);
        checkBtn=(Button)findViewById(R.id.checkBtn);
        tectChange=(TextView) findViewById(R.id.tectChange);

        tectChange.setText("WELCOME");
//        usernameTV.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                tectChange.setText(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    public void onClick(View view) {

        if(!TextUtils.isEmpty(usernameTV.getText())) {
            if (helper.verifyUsrPas(usernameTV.getText().toString()).getCount() > 0) {
               sentToNextAcitivity(usernameTV.getText().toString());
            } else {
                //load from retrofit


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Save to DB");
                builder.setMessage("Do you want to save this usert to the DB");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sentToNextAcitivity(usernameTV.getText().toString());
                    }
                });
                builder.setNegativeButton("NO!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //

                        usernameTV.setText("");
                        Toast.makeText(getApplicationContext(), "Select new Username to search", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.create();
                builder.show();

            }
        }
        else {
            Toast.makeText(this, "Empty Field, Enter text! ",Toast.LENGTH_SHORT).show();
        }
    }

    void sentToNextAcitivity(String username){
        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        usernameTV.setText("");

    }
}
