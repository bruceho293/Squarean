package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ground implements GameObject{
    private Rect ground;
    private int height;
    private Bitmap image;

    public Ground(int height){
        this.height = height;
        this.ground = new Rect(0, Constants.SCREEN_HEIGHT - height, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.image = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ground31);
    }


    public int getHeight(){
        return this.height;
    }

    public Rect getGround(){
        return ground;
    }
    @Override
    public void draw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        canvas.drawRect(ground, paint);
        canvas.drawBitmap(image, null, ground, null);
    }

    @Override
    public void update() {

    }
}
