package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {
    public ArrayList<Scene> scenes = new ArrayList<>();

    public static int ACTIVE_SCENE;
    public static Score scoreSystem = new Score();

    public SceneManager() {
        ACTIVE_SCENE = 0;
        scenes.add(new StartGameScene());
        scenes.add(new GamePlayScene());
        scenes.add(new ScoreScene());
    }




    public void receiveTouch(MotionEvent event){
            scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    public void update(){
            scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
            scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
