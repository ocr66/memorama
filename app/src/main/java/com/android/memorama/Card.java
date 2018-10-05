package com.android.memorama;

/**
 * Created by o.lopez.cienfuegos on 10/4/2018.
 */

public class Card {

    int imageSource;

    public Card(){}

    public Card(int imageSource) {
        this.imageSource = imageSource;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }
}
