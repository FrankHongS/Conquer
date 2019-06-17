package com.hon.conquer.vo.news;

/**
 * Created by Frank_Hon on 6/17/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsProfile {

    private String avatar;

    private String bio;

    private String author;

    public NewsProfile(){

    }

    public NewsProfile(String avatar, String bio, String author) {
        this.avatar = avatar;
        this.bio = bio;
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "avatar='" + avatar + '\'' +
                ", bio='" + bio + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

}
