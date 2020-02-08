package com.example.mosalah95.focuspower;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefer {
    SharedPreferences ShredRef;

    public SharedPrefer(Context context) {
        ShredRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
    }

    public void SaveStringData(String Data, String Value) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString(Data, Value);
        editor.apply();
    }

    public String LoadStringData(String Data) {
        return ShredRef.getString(Data, "");
    }

    public void SaveIntData(String Data, int Value) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putInt(Data, Value);
        editor.apply();
    }

    public int LoadIntData(String Data) {
        return ShredRef.getInt(Data, 0);
    }

}

/*
package com.haleyjoel.queastion;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    BootstrapButton btn_play, btn_settings, btn_points, btn_shop, btn_quit;
    Typeface typeface_1, typeface_2;
    TextView text_Level, text_Coins;
    private RewardedVideoAd mAd;
    SharedPrefer SaveSystem;
    Dialog dialog;
    Animation animation_alpha,animation_alpha2,animation_scale,animation_rotate;
    ImageView Image_Logo;
    int value2;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SaveSystem = new SharedPrefer(this);


        CheckInternet checkInternet = new CheckInternet(this);
        boolean check = checkInternet.isConnectingToInternet();
        adView = (AdView) findViewById(R.id.adView);
        if(check){
            adView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }else {
            adView.setVisibility(View.GONE);
        }




        lastActive();



        MobileAds.initialize(this, getResources().getString(R.string.group_ads));
        mAd = MobileAds.getRewardedVideoAdInstance(this);

        if ( SaveSystem.LoadStringData("FirstTime")  == "") {
            SaveSystem.SaveStringData("FirstTime", "True");
            SaveSystem.SaveIntData("Coins", 500);
        }



        typeface_1 = Typeface.createFromAsset(getAssets(), "Fonts/fontawesome-webfont.ttf");
        typeface_2 = Typeface.createFromAsset(getAssets(), "Fonts/font-en.otf");
        btn_settings = (BootstrapButton) findViewById(R.id.btn_settings);
        btn_points = (BootstrapButton) findViewById(R.id.btn_points);
        btn_shop = (BootstrapButton) findViewById(R.id.btn_shop);
        btn_play = (BootstrapButton) findViewById(R.id.btn_play);
        btn_quit = (BootstrapButton) findViewById(R.id.btn_quit);
        btn_play.setTypeface(typeface_1);
        btn_settings.setTypeface(typeface_1);
        btn_points.setTypeface(typeface_1);
        btn_shop.setTypeface(typeface_1);
        btn_quit.setTypeface(typeface_1);


        Image_Logo = (ImageView)findViewById(R.id.Image_Logo);


        animation_alpha = AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha_home);
        animation_alpha2 = AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha2_home);
        animation_alpha2.setStartTime(1000);
        animation_rotate = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_home);
        btn_play.setAnimation(animation_alpha);
        btn_shop.setAnimation(animation_alpha2);
        Image_Logo.setAnimation(animation_rotate);

        text_Level = (TextView) findViewById(R.id.text_Level);
        text_Coins = (TextView) findViewById(R.id.text_Coins);
        text_Coins.setTypeface(typeface_2);
        text_Level.setTypeface(typeface_2);

        btn_play.setText("\uf11b" + " " + getResources().getString(R.string.start_game));
        btn_settings.setText("\uf0ad" + " " + getResources().getString(R.string.Settings));
        btn_points.setText("\uf03d" + " " + getResources().getString(R.string.watch_ads));
        btn_shop.setText("\uF07A" + " " + getResources().getString(R.string.Shop));
        btn_quit.setText("\uf08b" + " " + getResources().getString(R.string.Exit));
        text_Level.setText(getResources().getString(R.string.level) + " " + SaveSystem.LoadIntData("Level"));
        text_Coins.setText(String.valueOf(SaveSystem.LoadIntData("Coins")));


        loadRewardedVideoAd();
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override public void onRewardedVideoAdLoaded() {}
            @Override public void onRewardedVideoAdOpened() {}
            @Override public void onRewardedVideoStarted() {}
            @Override public void onRewardedVideoAdClosed() {}
            @Override public void onRewardedVideoAdLeftApplication() {}
            @Override public void onRewardedVideoAdFailedToLoad(int i) {}

            @Override
            public void onRewarded(RewardItem rewardItem) {
                SaveSystem.SaveIntData("Coins", SaveSystem.LoadIntData("Coins") + 100);
                text_Coins.setText(String.valueOf(SaveSystem.LoadIntData("Coins")));
                Toast.makeText(MainActivity.this, getResources().getString(R.string.earned), Toast.LENGTH_SHORT).show();
            }


        });


    }

    public void PlayGame(View view) {
        if (SaveSystem.LoadIntData("Coins") >= 20) {
            startActivity(new Intent(MainActivity.this, QueastionActivity.class));
        }else{
            Toast.makeText(this, "" + getResources().getString(R.string.enough_money), Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenSettings(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void QuitGame(View view) {
        finish();
        System.exit(0);
    }

    public void StartAds(View view) {
        CheckInternet State = new CheckInternet(this);
        if ( State.isConnectingToInternet() ) {
            if (mAd.isLoaded()) {
                mAd.show();
                loadRewardedVideoAd();
            }
        }else{
            Toast.makeText(MainActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRewardedVideoAd() {
        if (!mAd.isLoaded()) {
            mAd.loadAd(getResources().getString(R.string.video), new AdRequest.Builder().build());
        }
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        loadRewardedVideoAd();
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    public void openShop(View view) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.shop_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        TextView Tit_1 = (TextView)dialog.findViewById(R.id.txt_title1);
        TextView Tit_2 = (TextView)dialog.findViewById(R.id.txt_title2);
        TextView Tit_3 = (TextView)dialog.findViewById(R.id.txt_title3);
        TextView Des_1 = (TextView)dialog.findViewById(R.id.txt_description1);
        TextView Des_2 = (TextView)dialog.findViewById(R.id.txt_description2);
        TextView Des_3 = (TextView)dialog.findViewById(R.id.txt_description3);
        TextView Des_4 = (TextView)dialog.findViewById(R.id.txt_dialog);
        Tit_1.setTypeface(typeface_2);
        Tit_2.setTypeface(typeface_2);
        Tit_3.setTypeface(typeface_2);
        Des_1.setTypeface(typeface_2);
        Des_2.setTypeface(typeface_2);
        Des_3.setTypeface(typeface_2);
        Des_4.setTypeface(typeface_2);

        BootstrapButton Buy_1 = (BootstrapButton) dialog.findViewById(R.id.btn_buy1);
        BootstrapButton Buy_2 = (BootstrapButton) dialog.findViewById(R.id.btn_buy2);
        BootstrapButton Buy_3 = (BootstrapButton) dialog.findViewById(R.id.btn_buy3);
        Buy_1.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
        Buy_2.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
        Buy_3.setBootstrapBrand(DefaultBootstrapBrand.DANGER);

        if (SaveSystem.LoadIntData("Coins") >= 5000) {
            Buy_1.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
            Buy_2.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
            Buy_3.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }else if (SaveSystem.LoadIntData("Coins") >= 3000) {
            Buy_1.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
            Buy_2.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }else if (SaveSystem.LoadIntData("Coins") >= 1500) {
            Buy_1.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
        }

        if (SaveSystem.LoadIntData("MoreTime") >= 5) {
            Buy_1.setEnabled(false);
        }
        if (SaveSystem.LoadIntData("MoreCoins") >= 5) {
            Buy_2.setEnabled(false);
        }
        if (SaveSystem.LoadIntData("LessCoins") >= 5) {
            Buy_3.setEnabled(false);
        }

        Buy_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSystem.LoadIntData("Coins") >= 1000) {
                    if (SaveSystem.LoadIntData("MoreTime") >= 5) {
                        return;
                    }
                    SaveSystem.SaveIntData("MoreTime", 5);
                    onBuyItem(1500);
                }else{
                    Toast.makeText(MainActivity.this, "" + getResources().getString(R.string.enough_money), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Buy_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSystem.LoadIntData("Coins") >= 3000) {
                    if (SaveSystem.LoadIntData("MoreCoins") >= 20) {
                        return;
                    }
                    SaveSystem.SaveIntData("MoreCoins",20);
                    onBuyItem(c);
                }else{
                    Toast.makeText(MainActivity.this, "" + getResources().getString(R.string.enough_money), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Buy_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSystem.LoadIntData("Coins") >= 5000) {
                    if (SaveSystem.LoadIntData("LessCoins") >= 10) {
                        return;
                    }
                    SaveSystem.SaveIntData("LessCoins",10);
                    onBuyItem(5000);
                }else{
                    Toast.makeText(MainActivity.this, "" + getResources().getString(R.string.enough_money), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    void onBuyItem(int Coins){
        SaveSystem.SaveIntData("Coins", SaveSystem.LoadIntData("Coins") - Coins);
        text_Coins.setText(String.valueOf(SaveSystem.LoadIntData("Coins")));
        Toast.makeText(MainActivity.this, getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
    }

    public void lastActive(){
        CheckInternet checkInternet = new CheckInternet(MainActivity.this);
        boolean val = checkInternet.isConnectingToInternet();
        if(val){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("control panel");
            myRef.child("last active").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String value = snapshot.getValue(String.class);
                    value2 = Integer.parseInt(value) + 1;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    myRef.child("last active").setValue(String.valueOf(value2));
                }
            }.start();
        }
    }

}

 */