package com.example.music.Model;

import com.google.firebase.database.Exclude;

public class UploadSong
{
    public String songTitle,songArtist,songType,songDuration,songLink,mKey;

    public UploadSong(){}

    public UploadSong(String songTitle,String songArtist,String songType, String songDuration, String songLink)
    {
        if(songTitle.trim().equals(""))
        {
            songTitle = "No title";
        }
        if(songArtist.trim().equals(""))
        {
            songArtist = "No Artist";
        }
        if(songType.trim().equals(""))
        {
            songType = "No Type";
        }
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songType = songType;
        this.songDuration = songDuration;
        this.songLink = songLink;

    }




    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongType() {
        return songType;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }
    @Exclude
    public String getmKey() {
        return mKey;
    }
    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
