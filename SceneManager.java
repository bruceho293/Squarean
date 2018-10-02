package com.game.hogia.boringman;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {
    public ArrayList<Scene> scenes = new ArrayList<>();

    public static int ACTIVE_SCENE;
    public static Score scoreSystem = new Score();

    private GamePlayScene gp;
    private ScoreScene scoreScene;



    public SceneManager() {
        ACTIVE_SCENE = 0;
        scenes.add(new StartGameScene());
//        this.gp = new GamePlayScene();
//        this.scoreScene = new ScoreScene();
//        scenes.add(gp);
//        scenes.add(scoreScene);
//        this.scoreScene.getScoreSystem(gp.scoreSystem);
        scenes.add(new GamePlayScene());
        scenes.add(new ScoreScene());
//        scenes.add(new WaitingScene());
    }


//    public void increaseTime(){
//        this.timeToShowAds++;
//    }
//
//    public void setTimeToShowAds(){
//        this.timeToShowAds = 0;
//    }

//    public int getTimeToShowAds(){
//        return this.timeToShowAds;
//    }

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
