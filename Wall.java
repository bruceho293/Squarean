package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Wall implements  GameObject{
    private Rect rectangle1, rectangle2;
    private Rect rectangle;

    private int color;
    private long startTime;
    private Bitmap image;


    public float incrementSpeed;



    public Wall(int gapL, int gapR, int playerWidth, int y, int distance, int height, int color){
        this.color = color;
        int gap1 = (int)(gapL * Math.random()) + 2 * playerWidth/3;
        int gap2 = (int)(gapR * Math.random()) + 2 * playerWidth/3;

        this.image = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wall12);

        if (gap1 < playerWidth && gap2 < playerWidth){
            double random = Math.random();
            if (random >= 0.5){
                gap2 =  2 * playerWidth;
            } else {
                gap1 = 2 * playerWidth;
            }
        }

        rectangle = new Rect(gap1, y, Constants.SCREEN_WIDTH - gap2, y + height);

        startTime = System.currentTimeMillis();

}



    //Walls movement and it's properties
    // Get the directions of walls and change the

    public Rect getRectangle(){
        return rectangle;
    }



    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
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


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        canvas.drawBitmap(image, null, rectangle, paint);
    }

    @Override
    public void update() {
        if(!GamePlayScene.gameOver) {
            int elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            float speed = Constants.SCREEN_HEIGHT / 15000.0f;
            incrementSpeed = speed;
            incrementY(speed * elapsedTime);
        }

    }

}
