package com.game.hogia.boringman;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundEffects {

    private static SoundPool soundPool;
    private static int jumpSound;


    public SoundEffects(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        jumpSound = soundPool.load(context, R.raw.jump_01,1);
    }

    public void playJumpSound(){
        soundPool.play(jumpSound, 1.0f, 1.0f ,1, 0, 1.2f);
    }

}
