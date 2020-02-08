package com.example.mosalah95.focuspower;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;

public class QueastionActivity extends AppCompatActivity {

    // متغيرات
    int TotalAnswer, TotalTrueAnswer; // الاجابات الصحيحة و رقم السؤال و مجموع الاجابات
    TextView text_Question, text_Time, text_watch;
    MediaPlayer Sound_Correct, Sound_Incorrect;
    LinearLayout panel_Image, panel_Question;
    CountDownTimer image_Timer, select_Timer;
    ArrayList<LevelClass> LevelList;
    ArrayList<String> answers_List;
    BootstrapButton[] Buttons;
    BootstrapButton btn_skip;
    LevelClass CurrentImage;
    SharedPrefer SaveSystem;
    Typeface typeface;
    AdView adView1,adView2;
    AdRequest adRequest;
    String TrueAnswer;
    boolean canClick;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queastion);

        SaveSystem = new SharedPrefer(this);
        if ( SaveSystem.LoadStringData("FirstTimeZoom")  == "") {
            SaveSystem.SaveStringData("FirstTimeZoom", "True");
            Toast.makeText(this, "" + getResources().getString(R.string.zoom), Toast.LENGTH_LONG).show();
        }

///////////////////////////////////////////////////////////////////////////
        CheckInternet checkInternet = new CheckInternet(this);
        boolean check = checkInternet.isConnectingToInternet();
        adView1 = (AdView) findViewById(R.id.adView1);
        if(check){
            adView1.setVisibility(View.VISIBLE);
            adRequest = new AdRequest.Builder().build();
            adView1.loadAd(adRequest);
            adView1.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    adView1.loadAd(adRequest);
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

        }else {
            adView1.setVisibility(View.GONE);
        }
////////////////////////////////////////////////////////////////////////////
        CheckInternet checkInternet2 = new CheckInternet(this);
        boolean check2 = checkInternet2.isConnectingToInternet();
        adView2 = (AdView) findViewById(R.id.adView2);
        if(check2){
            adView2.setVisibility(View.VISIBLE);
            adRequest = new AdRequest.Builder().build();
            adView2.loadAd(adRequest);
            adView2.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    adView2.loadAd(adRequest);
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

        }else {
            adView2.setVisibility(View.GONE);
        }
//////////////////////////////////////////////////////////////

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Sound_Correct = MediaPlayer.create(this, R.raw.answer_correct);
        Sound_Incorrect = MediaPlayer.create(this, R.raw.answer_incorrect);

        SaveSystem = new SharedPrefer(this);
        LevelList = new ArrayList<>();
        answers_List = new ArrayList<>();

        image = (ImageView) findViewById(R.id.image);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
        photoViewAttacher.update();

        text_Question = (TextView) findViewById(R.id.text_Question);
        text_Time = (TextView) findViewById(R.id.text_Time);
        text_watch = (TextView) findViewById(R.id.text_watch);

        panel_Image = (LinearLayout) findViewById(R.id.panel_Image);
        panel_Question = (LinearLayout) findViewById(R.id.panel_Question);
        Buttons = new BootstrapButton[4];
        Buttons[0] = (BootstrapButton) findViewById(R.id.button_Answer_1);
        Buttons[1] = (BootstrapButton) findViewById(R.id.button_Answer_2);
        Buttons[2] = (BootstrapButton) findViewById(R.id.button_Answer_3);
        Buttons[3] = (BootstrapButton) findViewById(R.id.button_Answer_4);

        typeface = Typeface.createFromAsset(getAssets(), "Fonts/font-en.otf");
        text_watch.setTypeface(typeface);
        text_Time.setTypeface(typeface);

        for (BootstrapButton i : Buttons) {
            i.setTypeface(typeface);

        }
        text_Question.setTypeface(typeface);

        //جلب الاسئلة و الاجابات من الجسون
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray AllImages = json.getJSONArray("Levels");
            for (int i = 0; i < AllImages.length(); i++) {
                JSONObject Level = AllImages.getJSONObject(i);
                String Image = Level.getString("Image");
                JSONArray JSONQuestions = Level.getJSONArray("Questions");
                ArrayList<QuestionsClass> Questions = new ArrayList<>();
                for (int n = 0; n < JSONQuestions.length(); n++) {
                    String Answer_1 = "";
                    String Answer_2 = "";
                    String Answer_3 = "";
                    String Answer_True = "";
                    String Question = "";
                    JSONObject QQuestion = JSONQuestions.getJSONObject(n);
                    try {
                        Question = QQuestion.getString("Question");
                        Answer_True = QQuestion.getString("Answer_True");
                        Answer_1 = QQuestion.getString("Answer_1");
                        Answer_2 = QQuestion.getString("Answer_2");
                        Answer_3 = QQuestion.getString("Answer_3");
                    } catch (Exception ignored) {
                    }

                    Questions.add(new QuestionsClass(Question, Answer_1, Answer_2, Answer_3, Answer_True));

                }
                LevelList.add(new LevelClass(Image, Questions));
            }
        } catch (Exception ignored) {
        }

        if (isEndGame()) {
            startActivity(new Intent(this, EndgameActivity.class));
            finish();
        } else {
            StartGame();
        }
    }

    // عندما تبدا العبة
    void StartGame() {
        TrueAnswer = "";
        TotalAnswer = 0;
        TotalTrueAnswer = 0;
        canClick = true;
        panel_Image.setVisibility(View.VISIBLE);
        panel_Question.setVisibility(View.GONE);

        btn_skip = (BootstrapButton) findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread.sleep(100);
                    image_Timer.onFinish();
                    image_Timer.cancel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        int Time = 15000 + (SaveSystem.LoadIntData("MoreTime") * 1000);
        image_Timer = new CountDownTimer(Time, 1000) {

            public void onTick(long millis) {
                text_Time.setText(String.valueOf(millis / 1000));
            }

            public void onFinish() {
                panel_Image.setVisibility(View.GONE);
                panel_Question.setVisibility(View.VISIBLE);
                getNextQuestion(0);
            }
        }.start();
        int ImageIndex = SaveSystem.LoadIntData("Level");
        CurrentImage = LevelList.get(ImageIndex);
        image.setImageBitmap(getBitmapFromAssets(CurrentImage.Image));

    }

    // اذا انتهت المراحل
    Boolean isEndGame() {
        if (SaveSystem.LoadIntData("Level") >= LevelList.size()) {
            return true;
        }
        return false;
    }

    // عندما تنتهى الاسئلة
    void FinishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("TotalTrueAnswer", TotalTrueAnswer);
        intent.putExtra("TotalAnswer", TotalAnswer);
        startActivity(intent);
        finish();
    }

    // احضار السؤال التالي
    void getNextQuestion(int time) {
        canClick = false;
        select_Timer = new CountDownTimer(time, 1000) {
            public void onTick(long millis) {
            }

            public void onFinish() {
                canClick = true;
                if (CurrentImage.Questions.size() < 1) {
                    FinishGame();
                    return;
                }
                int CurrentQuestion = new Random().nextInt(CurrentImage.Questions.size());
                String Text = CurrentImage.Questions.get(CurrentQuestion).Question;
                text_Question.setText(Text);


                String Answer_True = CurrentImage.Questions.get(CurrentQuestion).Answer_True;
                TrueAnswer = Answer_True;

                answers_List.clear();
                answers_List.add(CurrentImage.Questions.get(CurrentQuestion).Answer_1);
                answers_List.add(CurrentImage.Questions.get(CurrentQuestion).Answer_2);
                answers_List.add(CurrentImage.Questions.get(CurrentQuestion).Answer_3);
                answers_List.add(Answer_True);
                for (BootstrapButton i : Buttons) {
                    i.setVisibility(View.GONE);
                }
                // تغير موققع الجواب الصحيح
                for (int i = 0; i < 4; i++) {
                    int randomIndex = new Random().nextInt(answers_List.size());
                    String randomElement = answers_List.get(randomIndex);
                    answers_List.remove(randomIndex);
                    if (!randomElement.equals("")) {
                        Buttons[i].setVisibility(View.VISIBLE);
                        Buttons[i].setText(randomElement);
                    }

                }

                for (BootstrapButton i : Buttons) {
                    i.setBootstrapBrand(DefaultBootstrapBrand.INFO);
                }
                CurrentImage.Questions.remove(CurrentQuestion);
            }
        }.start();
    }

    // عندما يتم اختيار اجابة من الاجابات
    public void onSelectedAnswer(View view) {
        if (canClick) {
            BootstrapButton btn = ((BootstrapButton) view);
            String Text = (String) ((BootstrapButton) view).getText();
            if (Text.equals(TrueAnswer)) {
                TotalTrueAnswer++;
                btn.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                Sound_Correct.start();
            } else {
                btn.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                Sound_Incorrect.start();
            }
            TotalAnswer++;
            getNextQuestion(1000);
        }
    }

    // Assets يجلب الصورة من ملف
    public Bitmap getBitmapFromAssets(String fileName) {
        InputStream istr = null;
        try {
            istr = getAssets().open("images/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    // يجلب ملف الجسون من ملف Assets
    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
