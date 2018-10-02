package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


public class StartGameScene implements Scene{

    private Bitmap title, buttonStartGame;
    private Rect button, titleHolder;
    private Rect r = new Rect();

    public StartGameScene(){
        int left = Constants.SCREEN_WIDTH / 3;
        int right = 2 * Constants.SCREEN_WIDTH/3;
        int top = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/3;
        int bottom = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/3 + right - left;

        button =  new Rect(left, top, right, bottom);

        left = Constants.SCREEN_WIDTH / 9;
        right = Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 9;
        top = Constants.SCREEN_HEIGHT /4;
        bottom = Constants.SCREEN_HEIGHT / 2;

        titleHolder = new Rect(left, top, right, bottom);

        title = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlea1);
        buttonStartGame = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.attach_to_wall);
    }

    private void drawCenterText(Canvas canvas, float y, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        canvas.drawText(text, x, y, paint);
    }


    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && button.contains((int)event.getX(), (int)event.getY())) {
            SceneManager.ACTIVE_SCENE = 1;
        }
    }


    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH /10 );
        paint.setColor(Color.BLACK);
        String text = "Tap to start";


        canvas.drawBitmap(title, null, titleHolder, null);
        canvas.drawBitmap(buttonStartGame, null, button, null);
        drawCenterText(canvas, button.bottom + Constants.SCREEN_HEIGHT/10, paint, text);
    }

}
