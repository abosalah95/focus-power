package com.example.mosalah95.focuspower;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class EndgameActivity extends AppCompatActivity {
    TextView text_te1, text_te2;
    Typeface typeface;
    BootstrapButton btn_restart;
    SharedPrefer SaveSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        SaveSystem = new SharedPrefer(this);


        typeface = Typeface.createFromAsset(getAssets(), "Fonts/font-en.otf");

        text_te1 = (TextView) findViewById(R.id.text_te1);
        text_te2 = (TextView) findViewById(R.id.text_te2);

        text_te1.setTypeface(typeface);
        text_te2.setTypeface(typeface);
        btn_restart = (BootstrapButton) findViewById(R.id.btn_restart);

        btn_restart.setTypeface(typeface);

        btn_restart.setText("\uf021" + " " + "Reset All App Without Coins");
    }

    public void backToHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void restart(View view) {
        //SaveSystem.SaveIntData("Coins",0);
        SaveSystem.SaveIntData("Level",0);
        SaveSystem.SaveIntData("MoreCoins",0);
        SaveSystem.SaveIntData("LessCoins",0);
        SaveSystem.SaveIntData("MoreTime",0);
        SaveSystem.SaveStringData("FirstTime", "1");
        Toast.makeText(EndgameActivity.this, getResources().getString(R.string.reset), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EndgameActivity.this,MainActivity.class));
        finish();
    }
}
