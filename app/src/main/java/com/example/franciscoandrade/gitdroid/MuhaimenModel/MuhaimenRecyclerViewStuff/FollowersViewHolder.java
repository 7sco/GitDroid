package com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.R;

/**
 * Created by c4q on 2/2/18.
 */


public class FollowersViewHolder extends RecyclerView.ViewHolder {
    private TextView followerLogin;
    private TextView followersId;
    private TextView followers_avatar_url;
    private TextView followers_gravatar_url;
    private TextView followers_url;
    private TextView followers_html_url;
    private TextView repos_url;
    public FollowersViewHolder(View itemView) {
        super(itemView);
        followerLogin=itemView.findViewById(R.id.followersLogin);
        followersId=itemView.findViewById(R.id.followersidforfrag);
        followers_avatar_url=itemView.findViewById(R.id.follower_avatar_url);
        followers_gravatar_url=itemView.findViewById(R.id.follower_gravatar_id);
        followers_url=itemView.findViewById(R.id.follower_url);
        followers_html_url=itemView.findViewById(R.id.follower_html_url);
        repos_url=itemView.findViewById(R.id.repos_url);

    }
    public void onBind(FollowersModel followersModel){
        followerLogin.setText(followersModel.getLogin());
        followersId.setText(String.valueOf(followersModel.getId()));
        followers_avatar_url.setText(followersModel.getAvatar_url());
        followers_url.setText(followersModel.getFollowers_url());
        followers_html_url.setText(followersModel.getHtml_url());
        repos_url.setText(followersModel.getRepos_url());

    }
}
