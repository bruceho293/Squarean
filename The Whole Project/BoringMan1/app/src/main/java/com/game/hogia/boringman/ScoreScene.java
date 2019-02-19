package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ScoreScene implements Scene {

    private Score scoreSystem;
    private Rect r = new Rect();

    public ScoreScene(){

    }

//    public void getScoreSystem(Score scoreSystem){
//        this.scoreSystem = scoreSystem;
//    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 2;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GamePlayScene.ResetGameAfterScoreScene = true;
            GamePlayScene.gameOver = false;
            SceneManager.ACTIVE_SCENE = 1;
        }
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
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH / 8);
        paint.setColor(Color.BLACK);

        Rect gameOverHolder = new Rect(Constants.SCREEN_WIDTH / 10, Constants.SCREEN_HEIGHT / 8, 9 * Constants.SCREEN_WIDTH / 10, 1 * Constants.SCREEN_HEIGHT / 2);

        Bitmap gameOverPicture = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.gameover1);
        String text1 = "Your Score is " + SceneManager.scoreSystem.score;
        String text2 = "Highest Score : " + SceneManager.scoreSystem.HighScore;

        canvas.drawBitmap(gameOverPicture, null, gameOverHolder, null);
        drawCenterText(canvas, gameOverHolder.bottom + 250f, paint, text1);
        drawCenterText(canvas, gameOverHolder.bottom + 400f, paint, text2);

        Paint paint1 = new Paint();
        paint1.setTextSize(Constants.SCREEN_WIDTH / 10);
        paint1.setColor(Color.BLACK);
        String text3 = "Tap to play again";
        drawCenterText(canvas, gameOverHolder.bottom + 550f, paint1, text3);
    }

    @Override
    public void update() {

    }


}
