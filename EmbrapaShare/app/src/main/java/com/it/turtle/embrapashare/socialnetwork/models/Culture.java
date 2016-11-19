package com.it.turtle.embrapashare.socialnetwork.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Culture extends FirebaseObject {
    private String name;
    private String description;
    private String image;

    public Culture(String id, String name, String description, String image) {
        super(id);
        this.name = name;
        this.description = description;
        this.image = image != null ? image : "";
    }

    public Culture(String name, String description, String image) {
        this(("Culture_" + (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()))), name,description,image);
    }

    public Culture(String name) {
        this(name, "", "");
    }

    public Culture () {
        this("", "", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
