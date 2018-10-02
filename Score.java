package com.game.hogia.boringman;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {
    public int score;
    public int HighScore;

    private SharedPreferences sp;

    public Score(){
        sp = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        this.HighScore = sp.getInt("High_Score", 0);
        this.score = 0;
    }

    public void getScore(int score){
        this.score = score;
    }

    public void updateScore(){
        HighScore = sp.getInt("High_Score", 0);
        if (score > HighScore){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("High_Score", score);
            editor.commit();
            HighScore = sp.getInt("High_Score",0);
        }
    }
}
