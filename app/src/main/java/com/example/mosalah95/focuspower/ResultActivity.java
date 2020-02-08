package com.example.mosalah95.focuspower;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    TextView earned_points, wrong_answers, correct_answers, points_w, points_c;
    BootstrapButton btn_next, btn_home;
    int TotalTrueAnswer, TrueAnswerAmount, FalseAnswerAmount, TotalAnswer;
    SharedPrefer SaveSystem;
    Typeface typeface;
    Random RandomAds;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //أعلان كامل الشاشة
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.full_window));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        RandomAds = new Random();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(RandomAds.nextInt(7) != 5) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {

            }

            @Override
            public void onAdClosed() {

            }
        });


        SaveSystem = new SharedPrefer(this);

        typeface = Typeface.createFromAsset(getAssets(), "Fonts/font-en.otf");
        earned_points = (TextView) findViewById(R.id.earned_points);
        wrong_answers = (TextView) findViewById(R.id.wrong_answers);
        correct_answers = (TextView) findViewById(R.id.correct_answers);
        points_w = (TextView) findViewById(R.id.points_w);
        points_c = (TextView) findViewById(R.id.points_c);
        btn_next = (BootstrapButton) findViewById(R.id.btn_next);
        btn_home = (BootstrapButton) findViewById(R.id.btn_home);

        earned_points.setTypeface(typeface);
        wrong_answers.setTypeface(typeface);
        correct_answers.setTypeface(typeface);
        points_w.setTypeface(typeface);
        points_c.setTypeface(typeface);
        btn_next.setTypeface(typeface);
        btn_home.setTypeface(typeface);

        Bundle b = getIntent().getExtras();
        if (!b.isEmpty()) {
            TotalTrueAnswer = b.getInt("TotalTrueAnswer");
            TotalAnswer = b.getInt("TotalAnswer");
            FalseAnswerAmount = (TotalAnswer - TotalTrueAnswer) * ( -40 +  SaveSystem.LoadIntData("LessCoins") );
            TrueAnswerAmount = ( b.getInt("TotalTrueAnswer")* (20 + SaveSystem.LoadIntData("MoreCoins")));

            points_c.setText(String.valueOf(TotalTrueAnswer));
            points_w.setText(String.valueOf(TotalAnswer -TotalTrueAnswer));
            earned_points.setText(getResources().getString(R.string.earned_point) + " " + String.valueOf(TrueAnswerAmount + FalseAnswerAmount));
            SaveSystem.SaveIntData("Level", SaveSystem.LoadIntData("Level") + 1);
            SaveSystem.SaveIntData("Coins", SaveSystem.LoadIntData("Coins") + (TrueAnswerAmount + FalseAnswerAmount));
        }
    }

    public void NextLevel(View view) {
        SaveSystem = new SharedPrefer(this);
        if (SaveSystem.LoadIntData("Coins") >= 20) {
            startActivity(new Intent(this, QueastionActivity.class));
            finish();
        }else{
            Toast.makeText(this, "" + getResources().getString(R.string.enough_money), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
        }
    }

    public void backToHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
