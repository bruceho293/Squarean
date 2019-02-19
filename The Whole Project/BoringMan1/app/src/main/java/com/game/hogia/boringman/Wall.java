package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Wall implements  GameObject{
    private Rect rectangle, rectangleBorderL, rectangleBorderR;
    private long startTime;
    private Bitmap imageRect, imageBorder;

    private boolean movingHMode1;
    private int direction;

    public static int mode = 2;



    public Wall(int gapL, int gapR, int playerWidth, int y, int height){
        int gap1 = (int)(gapL * Math.random()) + 2 * playerWidth/3;
        int gap2 = (int)(gapR * Math.random()) + 2 * playerWidth/3;


        this.imageRect = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wall12);

        if (gap1 < playerWidth && gap2 < playerWidth){
            double random = Math.random();
            if (random >= 0.5){
                gap2 =  2 * playerWidth;
            } else {
                gap1 = 2 * playerWidth;
            }
        }
        int gap = (gap1 + gap2) / 2;
        int widthMode1 = playerWidth * 6;
        if(mode == 0)
            this.rectangle = new Rect(gap1, y, Constants.SCREEN_WIDTH - gap2, y + height);
        if(mode == 1) {
            int left = gap;
            int right = left + widthMode1;
            this.rectangle = new Rect(left, y, right, y + height);
            this.movingHMode1 = true;
            this.direction = (Math.random() >= 0.5) ? 1 : -1;
        }
        if(mode == 2){
            this.rectangle = new Rect(gap1, y, Constants.SCREEN_WIDTH - gap2, y + height);
            int widthRec = rectangle.right - rectangle.left;
            int left = rectangle.left + (int)(Math.random() * widthRec / 2);
            int right = left + Constants.WIDTH_RECT_MODE_2;
            this.rectangleBorderL = new Rect(left, y - height - 5, right, y);
            left = right + (int)(Math.random() * widthRec / 2);
            right = left + Constants.WIDTH_RECT_MODE_2;
            this.rectangleBorderR = new Rect(left, y - height - 5, right, y);
            this.imageBorder = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.border1);
        }
        startTime = System.currentTimeMillis();

    }


    //Walls movement and it's properties
    // Get the directions of walls and change the

    public Rect getRectangle(){
        return rectangle;
    }

    public static void changeMode(int score){
        if (score <= Constants.SCORE_STAGE_0)
            mode = 0;
        else if (score <= Constants.SCORE_STAGE_1)
            mode = 1;
        else if (score <= Constants.SCORE_STAGE_2)
            mode = 2;
        else if (score <= Constants.SCORE_STAGE_3)
            mode = 3;
        else
            mode = 4;
    }

    private void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        if (mode == 2 && rectangleBorderL != null && rectangleBorderR != null){
            rectangleBorderL.top += y;
            rectangleBorderL.bottom += y;
            rectangleBorderR.top += y;
            rectangleBorderR.bottom += y;
        }
    }

    private void reverseDirection(){
        if(rectangle.left < 0){
            direction = 1;
        }
        if(rectangle.right > Constants.SCREEN_WIDTH){
            direction = -1;
        }
    }

    private void moveHorizontally(float x){
        if(mode == 1 && movingHMode1) {
            reverseDirection();
            rectangle.left += x * direction;
            rectangle.right += x * direction;
        }
    }

    public void setMovingHMode1(boolean movingHMode1){
        this.movingHMode1 = movingHMode1;
    }


    //End of the wall movement

    //Walls Collision Detection
    public Rect getBoundWall_RightSide(){
        return new Rect(rectangle.right - 20, rectangle.top + 20, rectangle.right, rectangle.bottom - 20 );
    }

    public Rect getBoundWall_LeftSide(){
        return new Rect(rectangle.left, rectangle.top + 20, rectangle.left + 20, rectangle.bottom - 20 );
    }

    public Rect getBoundWall_UpSide(){
        return new Rect(rectangle.left, rectangle.top, rectangle.right, rectangle.top + 25);
    }


    public Rect getBoundWall_DownSide(){
        return new Rect(rectangle.left , rectangle.bottom - 25, rectangle.right, rectangle.bottom);
    }

    public Rect getBorder_Left(){
        return rectangleBorderL;
    }

    public Rect getBorder_Right(){
        return rectangleBorderR;
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawBitmap(imageRect, null, rectangle, paint);
        if (mode == 2 & rectangleBorderL != null && rectangleBorderR != null){
            canvas.drawBitmap(imageBorder, null, rectangleBorderL, null);
            canvas.drawBitmap(imageBorder, null, rectangleBorderR, null);
        }
    }

    @Override
    public void update() {
        if(!GamePlayScene.gameOver) {
            int elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            float speed = Constants.SCREEN_HEIGHT / 15000.0f;
            incrementY(speed * elapsedTime);
            if (mode == 1 && movingHMode1){
                float speedH = (Constants.PLAYER_SPEED  - 10) * 0.5f;
                moveHorizontally(speedH);
            }
        }

    }

}
