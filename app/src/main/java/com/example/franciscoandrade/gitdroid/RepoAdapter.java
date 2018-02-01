package com.example.franciscoandrade.gitdroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.franciscoandrade.gitdroid.restApi.model.RootObjectRepos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by franciscoandrade on 1/16/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    List<RootObjectRepos> repoList;
    Context context;

    public RepoAdapter(Context context) {
        repoList = new ArrayList<>();
        this.context=context;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {

        holder.titleTV.setText(repoList.get(position).getName());
        if (repoList.get(position).getDescription() != null) {
            holder.descriptionTV.setText(repoList.get(position).getDescription().toString());
        } else {
            holder.descriptionTV.setText("N/A");
        }
        holder.branchTV.setText(repoList.get(position).getDefault_branch().toString());
        //When click takes u to branch

        //holder.collaboratorsTV.setText(repoList.get(position).getCollaborators_url().toString());
        //When Click shows colaborators
        if (repoList.get(position).getLanguage() != null) {
            holder.languageTV.setText(repoList.get(position).getLanguage().toString());
        } else {
            holder.languageTV.setText("N/A");
        }

        holder.dateTV.setText(repoList.get(position).getCreated_at().toString());

    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public void addImages(List<RootObjectRepos> rootObject) {
        repoList.addAll(rootObject);
        notifyDataSetChanged();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, descriptionTV, branchTV, collaboratorsTV, languageTV, dateTV;

        public RepoViewHolder(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
            descriptionTV = (TextView) itemView.findViewById(R.id.descriptionTV);
            branchTV = (TextView) itemView.findViewById(R.id.branchTV);
            collaboratorsTV = (TextView) itemView.findViewById(R.id.collaboratorsTV);
            languageTV = (TextView) itemView.findViewById(R.id.languageTV);
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
        }
    }
}
