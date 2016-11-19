package com.it.turtle.embrapashare.socialnetwork.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirebaseObject {

    private String id;

    public FirebaseObject (String id) {
        this.id = id;
    }

    public FirebaseObject () {
        this(new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()));
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof FirebaseObject) && (this.toString().equals(obj.toString()));
    }
}
