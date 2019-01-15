package com.game.hogia.boringman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player implements GameObject {
    public Rect squarePlayer;
    private boolean jumping;
    private boolean falling;

    public int touchGround;

    public int score;

    private SoundEffects sound;
    private Bitmap playerImage;
    private Ground ground;
    private MinusCoin minusCoin;

    private int width, height;

    private float positionX, positionY, vX, vY;
    private final float speed2 = Constants.PLAYER_SPEED, speed1 = speed2 - 5;
    private final float MaxVY = 100;
    private float gravity;
    private float dt;


    private Wall wall;

    private boolean isCollideWall_top, isCollideWall_bottom, isOnWall, isCollideWall_side;

    private long startTime;


    public Player(int x, int y, int width, int height){
        this.width = width;
        this.sound = new SoundEffects(Constants.CURRENT_CONTEXT);
        this.height = height;
        this.squarePlayer = new Rect(x, y, x + width, y + height);
        this.score = 0;
        this.vX = -speed2;
        this.vY = 0;
        this.gravity = 4f;
        this.positionX = (squarePlayer.left + squarePlayer.right)/2;
        this.positionY = (squarePlayer.top + squarePlayer.bottom)/2;
        BitmapFactory bf = new BitmapFactory();
        this.minusCoin = new MinusCoin();
        playerImage = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.stand);

        touchGround = 0;
        jumping = false;
        falling = false;

        startTime = System.currentTimeMillis();
    }
    //Set the velocities
    private void setVX(float vx){this.vX = vx;}

    private void setVY(float vy){this.vY = vy;}


    public void setGround(Ground g){
        this.ground = g;
    }

    public Ground getTheGround(){
        return this.ground;
    }



    public Rect getBoundLeft(){
        return new Rect(squarePlayer.left,squarePlayer.top + 15, squarePlayer.left + 15, squarePlayer.bottom - 15);
    }

    public Rect getBoundRight(){
        return new Rect(squarePlayer.right - 15,squarePlayer.top + 15, squarePlayer.right, squarePlayer.bottom - 15);
    }

    public Rect getBoundTop(){
        return new Rect(squarePlayer.left,squarePlayer.top, squarePlayer.right, squarePlayer.top + height / 2 - 15);
    }

    public Rect getBoundBottom(){
        return new Rect(squarePlayer.left,squarePlayer.bottom - height/2 + 15, squarePlayer.left + 15, squarePlayer.bottom);
    }



    public void setJumping(boolean jump){
        this.jumping = jump;
    }

    public void setFalling(boolean fall){this.falling = fall;}



    public void reverseVX(){setVX(-vX);}

    public void getWall(Wall wall){
        this.wall = wall;
    }

    public int getWidth() {
        return this.width;
    }


    public void collideWall(Wall wall){
        getWall(wall);
        //Check if the player hits the bottom side of the wall
        if (Rect.intersects(getBoundTop(), wall.getBoundWall_DownSide())){
            collideWall_bottom(true);
        } else {
            collideWall_bottom(false);
        }
        //Check if the player hits the top side of the wall
        if (Rect.intersects(getBoundBottom(), wall.getBoundWall_UpSide())){
            collideWall_top(true);
        } else {
            collideWall_top(false);
        }
        //Check if the player hits one of the sides of the wall
        if (Rect.intersects(getBoundLeft(), wall.getBoundWall_RightSide()) || Rect.intersects(getBoundRight(), wall.getBoundWall_LeftSide())){
            collideWall_side(true);
        } else {
            collideWall_side(false);
        }

        checkCollision();
    }


    private void collideWall_side(boolean collide){
        isCollideWall_side = collide;
    }

    private void collideWall_top(boolean collide){
        isCollideWall_top = collide;
    }

    private void setOnWall(boolean onWall){
        isOnWall = onWall;
    }


    private void collideWall_bottom(boolean collide){
        isCollideWall_bottom = collide;
    }

    public void startJumping(){
        if(!jumping) {
            setVY(-speed2);
            sound.playJumpSound();
            setJumping(true);
        }
        setFalling(true);
    }


    private void checkCollision(){
        if (isCollideWall_bottom){
            setVY(speed2);
            setFalling(true);
        }

        if (isCollideWall_top){
            if (squarePlayer.bottom > wall.getRectangle().top){
                squarePlayer.bottom = wall.getRectangle().top;
            }
            setVY(1);
            setJumping(false);
            setOnWall(true);
            if(Wall.mode == 1){
                wall.setMovingHMode1(false);
                if(squarePlayer.left <= wall.getRectangle().left || squarePlayer.right >= wall.getRectangle().right)
                    reverseVX();
            }
            if(Wall.mode == 2 && wall.getBorder_Left() != null && wall.getBorder_Right() != null){
                if(Rect.intersects(squarePlayer, wall.getBorder_Left()) || Rect.intersects(squarePlayer, wall.getBorder_Right()))
                    reverseVX();
            }
        }
        else{
            setOnWall(false);
            wall.setMovingHMode1(true);
        }

        if (isCollideWall_side){
            reverseVX();
            setFalling(true);
        }
    }



    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerImage, null, squarePlayer, null);
        minusCoin.draw(canvas);
    }

    @Override
    public void update() {


        dt = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (dt > 0.5f) {
            dt = 0.5f;
        }

        minusCoin.update();
        minusCoin.setGetHit(squarePlayer);
        if(minusCoin.getHit()) {
            if(score >= 2)
                score -= 2;
            else if(score >= 1)
                score -= 1;
        }

        if (squarePlayer.bottom > ground.getGround().top - 50){
            setVY(30);
        }

        if (squarePlayer.bottom > ground.getGround().top ) {
            squarePlayer.bottom = Constants.SCREEN_HEIGHT - ground.getHeight();
            touchGround = 1;
            setFalling(false);
        }

        if (getBoundLeft().left <= 0) {
//            setVX(speed2);
            reverseVX();
            vY += 0.05f;
        }
        if (getBoundRight().right >= Constants.SCREEN_WIDTH) {
//            setVX(-speed2);
            reverseVX();
            vY += 0.05f;
        }


        if (squarePlayer.top <= 10){
            setVY(speed2/3 * 2);
        }


        if (vY > MaxVY){
            vY = MaxVY;
        }

        if(falling){
            if(isOnWall){
                if (vX > 0){
                    setVX(speed1);
                } else {
                    setVX(-speed1);
                }
                setVY(-10);
                squarePlayer.bottom = wall.getRectangle().top;
            } else {
                if (vX > 0){
                    setVX(speed2);
                } else {
                    setVX(-speed2);
                }
                vY += gravity * dt;
            }

        }


        positionX += vX * dt;
        positionY += vY * dt;
        squarePlayer.set((int) (positionX - width / 2), (int) (positionY - height / 2), (int) (positionX + width / 2), (int) (positionY + height / 2));
    }
}
