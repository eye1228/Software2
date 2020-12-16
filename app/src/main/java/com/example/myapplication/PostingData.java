package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PostingData {
    private String posting_id;
    private String posting_weather;
    private String posting_image;
    private String posting_memo;
    private String posting_location;
    private String posting_title;
    private String posting_score;
    private String posting_date; //set은 필요 없음. 저장은 데이터베이스에서 할 꺼니까.


    public String getPosting_id() {
        return posting_id;
    }

    public String getPosting_weather() {
        return posting_weather;
    }

    public String getPosting_image() {
        return posting_image;
    }

    public String getPosting_memo() {
        return posting_memo;
    }

    public String getPember_location() {
        return posting_location;
    }

    public String getPosting_title() {
        return posting_title;
    }

    public String getPosting_score() {
        return posting_score;
    }

    public String getPember_date() {
        return posting_date;
    }

    public void setMember_id(String member_id) {
        this.posting_id = member_id;
    }
    public void setPosting_weather(String posting_weather) {
        this.posting_weather = posting_weather;
    }

    public void setPosting_image(String posting_image) {
        this.posting_image = posting_image;
    }

    public void setPosting_memo(String posting_memo) {
        this.posting_memo = posting_memo;
    }

    public void setPember_location(String posting_location) {
        this.posting_location = posting_location;
    }

    public void setosting_title(String posting_title) {
        this.posting_title = posting_title;
    }

    public void setPosting_score(String posting_score) {
        this.posting_score = posting_score;
    }
}