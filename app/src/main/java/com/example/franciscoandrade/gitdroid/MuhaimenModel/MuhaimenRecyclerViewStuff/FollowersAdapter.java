package com.example.franciscoandrade.gitdroid.MuhaimenModel.MuhaimenRecyclerViewStuff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.franciscoandrade.gitdroid.MuhaimenModel.FollowersModel;
import com.example.franciscoandrade.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q on 2/2/18.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersViewHolder> {
    private List<FollowersModel> followersModelList = new ArrayList<>();

    public FollowersAdapter(List<FollowersModel> followersModelList) {
        this.followersModelList = followersModelList;
    }

    @Override
    public FollowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_itemview, parent, false);

        return new FollowersViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(FollowersViewHolder holder, int position) {
        holder.onBind(followersModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return followersModelList.size();
    }
}
