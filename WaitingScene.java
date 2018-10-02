package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class WaitingScene implements Scene {

    Rect r = new Rect();

    public WaitingScene(){


    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 3;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(Constants.SCREEN_WIDTH / 20);
        drawCenterText(canvas, paint, "Loading ...");
    }

    @Override
    public void update() {
//        if (SceneManager.ACTIVE_SCENE == 3 && GamePlayScene.timesToShowAds == 0)
//            SceneManager.ACTIVE_SCENE = 2;
    }
}
