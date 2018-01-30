package com.example.franciscoandrade.gitdroid;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.franciscoandrade.gitdroid.restApi.model.RootObject;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class DataActivity extends AppCompatActivity {
    TextView textInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        textInfo=(TextView)findViewById(R.id.textInfo);

        Bundle sendObject = getIntent().getExtras();
        //RootObject result = null;

        Gson gson = new Gson();

        if (sendObject != null) {
//            result = (RootObject) sendObject.getSerializable("result");
            String result= sendObject.getString("result");
            RootObject userObject = gson.fromJson(result, RootObject.class);
            Log.d("JSON SERIALIZED", "onCreate: "+userObject.getAvatar_url());
            Log.d("JSON SERIALIZED", "onCreate: "+userObject.getPublic_repos());
        }

        //byte[] blob = cursor.getBlob(cursor.getColumnIndex(MyProvider.KEY_DATA));




    }
}
