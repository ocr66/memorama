package com.android.memorama;

/**
 * Created by o.lopez.cienfuegos on 10/4/2018.
 */

public class MemoryMenu {

    String name;
    int imgSource;

    public MemoryMenu(){}

    public MemoryMenu(String name, int imgSource) {
        this.name = name;
        this.imgSource = imgSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgSource() {
        return imgSource;
    }

    public void setImgSource(int imgSource) {
        this.imgSource = imgSource;
    }
}
