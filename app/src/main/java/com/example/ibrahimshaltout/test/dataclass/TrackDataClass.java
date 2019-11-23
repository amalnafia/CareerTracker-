package com.example.ibrahimshaltout.test.dataclass;

import java.util.ArrayList;

public class TrackDataClass {

    private String track_Name;
    private String track_Desc;
    private String track_Bio;
    private String track_Rate;
    private String TrackID;
    private ArrayList<String> track_Hashtag_List;

    public String getTrackID() {
        return TrackID;
    }

    public void setTrackID(String trackID) {
        TrackID = trackID;
    }

    public String getTrack_Name() {
        return track_Name;
    }

    public void setTrack_Name(String track_Name) {
        this.track_Name = track_Name;
    }

    public String getTrack_Desc() {
        return track_Desc;
    }

    public void setTrack_Desc(String track_Desc) {
        this.track_Desc = track_Desc;
    }

    public String getTrack_Bio() {
        return track_Bio;
    }

    public void setTrack_Bio(String track_Bio) {
        this.track_Bio = track_Bio;
    }

    public String getTrack_Rate() {
        return track_Rate;
    }

    public void setTrack_Rate(String track_Rate) {
        this.track_Rate = track_Rate;
    }

    public ArrayList<String> getTrack_Hashtag_List() {
        return track_Hashtag_List;
    }

    public void setTrack_Hashtag_List(ArrayList<String> track_Hashtag_List) {
        this.track_Hashtag_List = track_Hashtag_List;
    }
}
