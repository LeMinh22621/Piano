package com.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PianoView extends View {
    private final static int NUMBER_KEYS = 14;
    private final Paint black, white, yellow, line;
    private final ArrayList<Key> whites;
    private final ArrayList<Key> blacks;

    private int keyWidth, keyHeight;
    private final SoundManager soundManager;

    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        line = new Paint();

        whites = new ArrayList<>();
        blacks = new ArrayList<>();

        soundManager = SoundManager.getInstance();
        soundManager.init(context);
        soundManager.playSound(R.raw.db3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w/NUMBER_KEYS;
        keyHeight = h;

        int blackCount = 15;
        for (int i = 0; i < NUMBER_KEYS; i++)
        {
            int left = i * keyWidth;
            int right = left + keyWidth;

            RectF rectF = new RectF(left, 0, right, keyHeight);

            whites.add(new Key(i+1, rectF));

            if (i != 0 && i != 3 && i != 7 && i != 10)
            {
                rectF = new RectF((float)(i-1) * keyWidth + 0.75f * keyWidth,
                                0,
                                (float)i * keyWidth + 0.25f * keyWidth,
                                keyHeight * 0.67f);

                blacks.add(new Key(blackCount++, rectF));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Key k : whites)
        {
            canvas.drawRect(k.rectF, k.isDown?yellow:white);
        }

        for (Key k : blacks)
        {
            canvas.drawRect(k.rectF, k.isDown?yellow:black);
        }

        for(int i = 1; i < 14; i++)
        {
            line.setAntiAlias(true);
            line.setStrokeWidth(6f);
            line.setColor(Color.BLACK);
            line.setStyle(Paint.Style.STROKE);
            line.setStrokeJoin(Paint.Join.ROUND);
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, keyHeight, line);
        }

    }

    public void onOneButton(Key key)
    {
        for(Key k : blacks)
        {
            if(key.sound != k.sound)
                k.isDown = false;
        }
        for(Key k : whites)
        {
            if(key.sound != k.sound)
                k.isDown = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        out:for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++)
        {
            int action =  event.getAction();
            boolean isDown = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            for(Key k : blacks)
            {
                if(k.rectF.contains(x, y))
                {
                    onOneButton(k);
                    k.isDown = isDown;
                    switch (k.sound) {
                        case 15:
                            soundManager.playSound(R.raw.db3);
                            break;
                        case 16:
                            soundManager.playSound(R.raw.eb3);
                            break;
                        case 17:
                            soundManager.playSound(R.raw.gb3);
                            break;
                        case 18:
                            soundManager.playSound(R.raw.ab3);
                            break;
                        case 19:
                            soundManager.playSound(R.raw.bb3);
                            break;
                        case 20:
                            soundManager.playSound(R.raw.db4);
                            break;
                        case 21:
                            soundManager.playSound(R.raw.eb4);
                            break;
                        case 22:
                            soundManager.playSound(R.raw.gb4);
                            break;
                        case 23:
                            soundManager.playSound(R.raw.ab4);
                            break;
                        case 24:
                            soundManager.playSound(R.raw.bb4);
                            break;
                    }
                    break out;
                }
            }
            for(Key k : whites)
            {
                if(k.rectF.contains(x, y))
                {
                    onOneButton(k);
                    k.isDown = isDown;
                    switch (k.sound)
                    {
                        case 1:
                            soundManager.playSound(R.raw.c3);
                            break;
                        case 2:
                            soundManager.playSound(R.raw.d3);
                            break;
                        case 3:
                            soundManager.playSound(R.raw.e3);
                            break;
                        case 4:
                            soundManager.playSound(R.raw.f3);
                            break;
                        case 5:
                            soundManager.playSound(R.raw.g3);
                            break;
                        case 6:
                            soundManager.playSound(R.raw.a3);
                            break;
                        case 7:
                            soundManager.playSound(R.raw.b3);
                            break;
                        case 8:
                            soundManager.playSound(R.raw.c4);
                            break;
                        case 9:
                            soundManager.playSound(R.raw.d4);
                            break;
                        case 10:
                            soundManager.playSound(R.raw.e4);
                            break;
                        case 11:
                            soundManager.playSound(R.raw.f4);
                            break;
                        case 12:
                            soundManager.playSound(R.raw.g4);
                            break;
                        case 13:
                            soundManager.playSound(R.raw.a4);
                            break;
                        case 14:
                            soundManager.playSound(R.raw.b4);
                            break;
                    }
                    break;
                }
            }
        }
        invalidate();
        return true;
    }
}
