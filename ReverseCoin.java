package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class ReverseCoin implements GameObject {

    private RectF coin;
    private Paint paint;
    private boolean gotHit;
    private int signOfNumber;
    private long startTime = System.currentTimeMillis();

    public ReverseCoin(){
        int x = (int)(Math.random() * (2 * Constants.SCREEN_WIDTH / 3 ) + Constants.SCREEN_WIDTH / 10);
        int y = 0;
        int width = Constants.SCREEN_WIDTH / 25;
        int height = width;
        this.coin = new RectF(x, y, x + width, y + height);
        this.paint = new Paint();
    }

    public RectF getCoin(){
        return coin;
    }

    public void setGetHit(Rect player) {
        this.gotHit = Rect.intersects(hitBoxCoins(), player);
    }

    public boolean getHit(){
        return gotHit;
    }
    public Rect hitBoxCoins(){
        return new Rect((int)(coin.left + 1), (int)(coin.top + 1), (int)(coin.right - 1), (int)(coin.bottom - 1));
    }
    @Override
    public void draw(Canvas canvas) {
        int r = (int)(Math.random() * 255);
        int g = (int)(Math.random() * 255);
        int b = (int)(Math.random() * 255);
        paint.setColor(Color.rgb(r,g,b));
        canvas.drawOval(coin, paint);
    }

    @Override
    public void update() {
        if(!GamePlayScene.gameOver) {
            int elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            float speed = Constants.SCREEN_HEIGHT / 5000.0f;
//
            coin.top += (speed * elapsedTime);
            coin.bottom += (speed * elapsedTime);
//            coin.left += (signOfNumber * speed * elapsedTime);
//            coin.right += (signOfNumber * speed * elapsedTime);
            if (coin.bottom >= Constants.SCREEN_HEIGHT || getHit()) {
                int x = (int) (Math.random() * (2 * Constants.SCREEN_WIDTH / 3) + Constants.SCREEN_WIDTH / 10);
                int y = 0;
                int width = Constants.SCREEN_WIDTH / 25;
                int height = width;
                coin = new RectF(x, y, x + width, y + height);
            }
        }
    }
}
