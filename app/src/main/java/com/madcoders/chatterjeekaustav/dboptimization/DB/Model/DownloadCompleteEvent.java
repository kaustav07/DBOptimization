package com.madcoders.chatterjeekaustav.dboptimization.DB.Model;

/**
 * Created by Kaustav on 11-03-2018.
 */

public class DownloadCompleteEvent {

    private String message;

    public DownloadCompleteEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
