package com.game.hogia.boringman;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class GameActivity extends Activity {

    private static InterstitialAd mInterstitialAd;
    public static AdListener adListener;
    private static int timeToShowAds = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;

        Constants.PLAYER_WIDTH = Math.round(Constants.SCREEN_WIDTH / 15f);
        Constants.PLAYER_HEIGHT = Constants.PLAYER_WIDTH;
        Constants.PLAYER_SPEED = Math.round(Constants.SCREEN_HEIGHT / 25f);
        Constants.WALL_HEIGHT = Math.round(Constants.SCREEN_HEIGHT / 20f);
        Constants.SCORE_SIZE = Math.round(Constants.SCREEN_WIDTH / 10f);
        Constants.WALL_DISTANCE = Math.round(Constants.PLAYER_HEIGHT * 3.5f);
        Constants.GROUND_HEIGHT = Math.round(Constants.SCREEN_HEIGHT / 20f);

        Constants.SCORE_STAGE_0 = 25;//color: MAGENTA
        Constants.SCORE_STAGE_1 = 75;//color: rgb(32, 137, 77);
        Constants.SCORE_STAGE_2 = 150;//color: rgb(255, 137, 77);
        Constants.SCORE_STAGE_3 = 300;//color: rgb(32, 137, 255);
        Constants.WIDTH_RECT_MODE_2 = 25;


        final GamePanel gp = new GamePanel(this);
        setContentView(gp);
//         Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, getString(R.string.appId));
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adId));
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        adListener = new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
//                if(GamePlayScene.timesToShowAds >= 5)
//                    if (mInterstitialAd.isLoaded())
//                        mInterstitialAd.show();
                countGameOver();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                GamePlayScene.gameOver = true;
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                GamePlayScene.gameOver = true;
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                SceneManager.ACTIVE_SCENE = 2;
                setContentView(gp);
            }
            public void countGameOver() {
                // If Ads are loaded, show Interstitial else show nothing.
                timeToShowAds++;
                if (timeToShowAds >= 7){
                    timeToShowAds = 0;
                    displayInterstitial();
                }
            }
        };
        mInterstitialAd.setAdListener(adListener);

    }


    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.

        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }
}
