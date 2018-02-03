package com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.R;
import com.example.franciscoandrade.gitdroid.UserInfoActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by c4q on 2/2/18.
 */


public class FollowersViewHolder extends RecyclerView.ViewHolder {
    private TextView followerLogin;


    private ImageView urlPic;
    public FollowersViewHolder(View itemView) {
        super(itemView);
        followerLogin=itemView.findViewById(R.id.followersLogin);
        urlPic=itemView.findViewById(R.id.followerspic);


    }
    public void onBind(final FollowersModel followersModel){
        followerLogin.setText(followersModel.getLogin());
        Picasso.with(itemView.getContext()).load(followersModel.getAvatar_url()).into(urlPic);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), followersModel.getLogin().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), UserInfoActivity.class);
                intent.putExtra("username", followersModel.getLogin().toString());
                itemView.getContext().startActivity(intent);
            }
        });



    }
}
