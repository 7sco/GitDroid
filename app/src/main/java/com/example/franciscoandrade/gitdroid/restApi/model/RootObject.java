package com.example.franciscoandrade.gitdroid.restApi.model;

import java.util.Date;

/**
 * Created by franciscoandrade on 1/16/18.
 */

public class RootObject{
    public String login;
    public int id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public Object name;
    public Object company;
    public String blog;
    public Object location;
    public Object email;
    public boolean hireable;
    public Object bio;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public Date created_at;
    public Date updated_at;
    public int private_gists;
    public int total_private_repos;
    public int owned_private_repos;
    public int disk_usage;
    public int collaborators;
    public boolean two_factor_authentication;


    public String getLogin() {

        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public Object getName() {
        return name;
    }

    public Object getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public Object getLocation() {
        return location;
    }

    public Object getEmail() {
        return email;
    }

    public boolean isHireable() {
        return hireable;
    }

    public Object getBio() {
        return bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public int getPrivate_gists() {
        return private_gists;
    }

    public int getTotal_private_repos() {
        return total_private_repos;
    }

    public int getOwned_private_repos() {
        return owned_private_repos;
    }

    public int getDisk_usage() {
        return disk_usage;
    }

    public int getCollaborators() {
        return collaborators;
    }

    public boolean isTwo_factor_authentication() {
        return two_factor_authentication;
    }
}
