package com.example;

import android.graphics.RectF;

public class Key
{
    public int sound;
    public RectF rectF;
    public boolean isDown;

    public Key(int sound, RectF rectF) {
        this.sound = sound;
        this.rectF = rectF;
        isDown = false;
    }
}
