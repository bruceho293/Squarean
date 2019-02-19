package com.game.hogia.boringman;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    public SceneManager sceneManager;


    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        Constants.CURRENT_CONTEXT = context;


        sceneManager = new SceneManager();
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        sceneManager.receiveTouch(event);

//        if (sceneManager.getActiveScene() == 0){
//            sceneManager.scenes.add(new GamePlayScene());
//            sceneManager.ACTIVE_SCENE = 1;
//        }

//        if (GamePlayScene.gameOver){
//            sceneManager.increaseTime();
//        }
//
//        if (sceneManager.getActiveScene() == 2){
//            sceneManager.ACTIVE_SCENE = 1;
//        }
        return true;
    }

    public void update(){
        sceneManager.update();
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        sceneManager.draw(canvas);
    }

}
