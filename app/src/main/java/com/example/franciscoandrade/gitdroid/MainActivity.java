package com.example.franciscoandrade.gitdroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.franciscoandrade.gitdroid.OpenHelper.SQLite_OpenHelper;
import com.example.franciscoandrade.gitdroid.restApi.model.RootObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextInputEditText usernameTV;
    Button checkBtn;
    TextView tectChange;
    Spinner spinnerUsers;
    ArrayList<String> listUsers;
    //2-List of type User with info we map
    ArrayList<String> usersList;
    ArrayAdapter<CharSequence> adapter;

    //Todo: allow user to modify data
    //Todo: if user chooses yes save data, else hust how content
    //Todo: Fix bug on no network dont load new users


    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "DB1", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTV= (TextInputEditText) findViewById(R.id.usernameTV);
        checkBtn=(Button)findViewById(R.id.checkBtn);
        tectChange=(TextView) findViewById(R.id.tectChange);
        spinnerUsers=(Spinner) findViewById(R.id.spinnerUsers);

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

        consultListPeople();
        adapter.notifyDataSetChanged();
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
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //

                        usernameTV.setText(" ");
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


    private void consultListPeople() {
        //Define SQLiteDB
        SQLiteDatabase db= helper.getReadableDatabase();
        //INstance of clase user to create object
        RootObject rootObject =null;
        usersList= new ArrayList<String>();
        //select * from users
        Cursor cursor= db.rawQuery("SELECT * FROM gituser", null);
        //go through data from db returns
        while (cursor.moveToNext()){
            usersList.add(cursor.getString(1));
        }
        //call method o construct list that will be shown in the spinner, using data we build in peopleList
        getList();
        //Load into spinner
        adapter=new ArrayAdapter(this, R.layout.spinner_item, listUsers);
        adapter.notifyDataSetChanged();
        spinnerUsers.setAdapter(adapter);

        //================== 9 ==========
        //add event to spinnerPeople
        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //position -1 because of the select word at position 0
                if(position!=0){

                    usernameTV.setText(usersList.get(position-1));
                }
                else {
                    usernameTV.setText("");

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //============= -> Next show data a ListView=======

    }

    private void getList() {
        listUsers= new ArrayList<String>();
        listUsers.add("Select");
        for(int i=0; i<usersList.size();i++){
            listUsers.add(usersList.get(i));
        }
    }


}
