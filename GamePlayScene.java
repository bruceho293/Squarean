package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GamePlayScene implements Scene {

    public Player player;
    public Score scoreSystem;

    private Ground ground;
    private Rect r = new Rect();
    private WallsManager wallsManager;
    private boolean firstGameStart = true;

    public static boolean ResetGameAfterScoreScene = false;
    public static boolean gameOver = true;

    public GamePlayScene(){
        scoreSystem = new Score();
    }

    public void reset(){
        ground = new Ground(Constants.GROUND_HEIGHT);
        int y = Constants.SCREEN_HEIGHT / 2;
        player = new Player(2 * Constants.SCREEN_WIDTH / 3, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        player.setGround(ground);
        int gap = Constants.SCREEN_WIDTH / 5;
        wallsManager = new WallsManager(player, gap, gap, Constants.WALL_HEIGHT, Constants.WALL_DISTANCE);
    }


    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver) {
                    player.startJumping();
                }
        }

    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 1;
    }



    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if (!gameOver) {
            player.draw(canvas);
            ground.draw(canvas);
            wallsManager.draw(canvas);
        }
    }

    @Override
    public void update() {
        if(!gameOver) {
            wallsManager.update();
            player.update();
            if (player.touchGround == 1) {
                gameOver = true;
                SceneManager.scoreSystem.getScore(player.score);
                SceneManager.scoreSystem.updateScore();
                player.touchGround = 0;
            }
            ground.update();
        } else {
                if (firstGameStart) {
                    gameOver = false;
                    reset();
                    firstGameStart = false;
                } else if(!ResetGameAfterScoreScene){
                    GameActivity.adListener.onAdLoaded();
                    SceneManager.ACTIVE_SCENE = 2;
                } else {
                    gameOver = false;
                    reset();
                    ResetGameAfterScoreScene = false;
                }

        }
    }
}
