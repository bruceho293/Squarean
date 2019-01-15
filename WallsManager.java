package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class WallsManager{

    private ArrayList<Wall> walls;
    private Player player;
    private int gapL, gapR;
    private int obstacleHeight;
    private int distance;

    private long startTime, initTime;

    public WallsManager(Player player, int gapL, int gapR,  int obstacleHeight, int distance){
        this.obstacleHeight = obstacleHeight;
        this.distance = distance;

        this.gapL = gapL;
        this.gapR = gapR;

        this.player = player;

        startTime = initTime = System.currentTimeMillis();

        walls = new ArrayList<>();

        populateObstacle();
    }

    private void populateObstacle(){
        Wall.mode = 0;
        int currY =  -4 * Constants.SCREEN_HEIGHT/4;
        while (currY < 0){
            walls.add(new Wall(gapL, gapR, player.getWidth(), currY, obstacleHeight));
            currY += (distance + obstacleHeight);
        }
    }



    public void update(){
            for (Wall w : walls) {
                player.collideWall(w);
                w.update();
            }

            if (walls.get(walls.size() - 1).getRectangle().bottom >= player.getTheGround().getGround().top) {
                walls.add(0, new Wall(gapL, gapR, player.getWidth(), walls.get(0).getRectangle().top - obstacleHeight - distance, obstacleHeight));
                if (walls.get(walls.size() - 1).getRectangle().bottom >= player.getTheGround().getGround().top) {
                    walls.remove(walls.size() - 1);
                        player.score++;
                }
            }
            Wall.changeMode(player.score);
    }

    public void draw(Canvas canvas){
        for (Wall w : walls){
            w.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(Constants.SCORE_SIZE);
            if(player.score <= Constants.SCORE_STAGE_0)
                paint.setColor(Color.MAGENTA);
            else if(player.score <= Constants.SCORE_STAGE_1)
                paint.setColor(Color.rgb(32, 137, 77));
            else if(player.score <= Constants.SCORE_STAGE_2)
                paint.setColor(Color.rgb(255, 137, 77));
            else if(player.score <= Constants.SCORE_STAGE_3)
                paint.setColor(Color.rgb(32, 137, 255));
            else
                paint.setColor(Color.rgb(32, 255, 77));
            canvas.drawText("" + player.score, Constants.SCREEN_WIDTH / 15, Constants.SCREEN_HEIGHT / 15, paint);
        }

    }
}
