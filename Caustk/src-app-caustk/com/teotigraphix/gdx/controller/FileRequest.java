
package com.teotigraphix.gdx.controller;

import java.io.File;

import com.badlogic.gdx.math.Rectangle;

public class FileRequest {

    private Object id;

    private File root;

    private File location;

    private String[] filter;

    private Rectangle rectangle;

    private File file;

    private String title;

    private Object data;

    public Object getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public File getRoot() {
        return root;
    }

    public File getLocation() {
        return location;
    }

    public String[] getFilter() {
        return filter;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Extra data needed for the operation (per call unique).
     */
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public FileRequest(Object id, String title, File root, File location, String[] filter,
            Rectangle rectangle) {
        this.id = id;
        this.title = title;
        this.root = root;
        this.location = location;
        this.filter = filter;
        this.rectangle = rectangle;
    }
}
