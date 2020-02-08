package com.example.mosalah95.focuspower;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class SettingsActivity extends AppCompatActivity {

    Typeface typeface,typeface1;
    BootstrapButton btn_restart, btn_report, btn_stars;
    SharedPrefer SaveSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SaveSystem = new SharedPrefer(this);


        typeface = Typeface.createFromAsset(getAssets(), "Fonts/fontawesome-webfont.ttf");
        typeface1 = Typeface.createFromAsset(getAssets(), "Fonts/font-en.otf");
        btn_restart = (BootstrapButton) findViewById(R.id.btn_restart);
        btn_report = (BootstrapButton) findViewById(R.id.btn_report);
        btn_stars = (BootstrapButton) findViewById(R.id.btn_stars);

        btn_restart.setTypeface(typeface);
        btn_report.setTypeface(typeface);
        btn_stars.setTypeface(typeface);

        btn_restart.setText("\uf021" + " " + "Reset All App Without Coins");
        btn_report.setText("\uf188" + " " + getResources().getString(R.string.report));
        btn_stars.setText("\uf005" + " " + getResources().getString(R.string.rate));

    }

    public void report(View view) {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.setContentView(R.layout.report_dialog);
        TextView txt_report = (TextView) dialog.findViewById(R.id.txt_report);
        TextView txt_typereport = (TextView) dialog.findViewById(R.id.txt_typereport);
        final TextView txt_description = (TextView) dialog.findViewById(R.id.txt_description);
        final RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.rb1);
        final RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.rb2);
        final RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.rb3);
        final EditText edit_description = (EditText) dialog.findViewById(R.id.edit_description);
        final TextView txt_editmax = (TextView) dialog.findViewById(R.id.txt_editmax);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        TextView txt_ok = (TextView) dialog.findViewById(R.id.txt_ok);
        txt_report.setTypeface(typeface1);
        txt_typereport.setTypeface(typeface1);
        txt_description.setTypeface(typeface1);
        rb1.setTypeface(typeface1);
        rb2.setTypeface(typeface1);
        rb3.setTypeface(typeface1);
        edit_description.setTypeface(typeface1);
        txt_editmax.setTypeface(typeface1);
        txt_cancel.setTypeface(typeface1);
        txt_ok.setTypeface(typeface1);
        dialog.show();

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxedit = edit_description.length();
                if (maxedit > 0) {
                    if (rb1.isChecked()) {


                        try {
                            String[] Sendto = {"mosalah955@gmail.com"};
                            Intent SendEmail = new Intent(Intent.ACTION_SEND);
                            SendEmail.putExtra(Intent.EXTRA_EMAIL, Sendto);
                            SendEmail.putExtra(Intent.EXTRA_SUBJECT, "[" + rb1.getText().toString() + "]" + "New Report");
                            SendEmail.putExtra(Intent.EXTRA_TEXT, "" + edit_description.getText().toString());
                            SendEmail.setType("message/rfc822");
                            Intent.createChooser(SendEmail, "Send Email");
                            startActivity(SendEmail);
                            edit_description.setText("");
                        } catch (Exception e) {
                            Toast.makeText(SettingsActivity.this, getResources().getString(R.string.no_app), Toast.LENGTH_SHORT).show();
                        }


                    } else if (rb2.isChecked()) {
                        try {
                            String[] Sendto = {"mosalah955@gmail.com"};
                            Intent SendEmail = new Intent(Intent.ACTION_SEND);
                            SendEmail.putExtra(Intent.EXTRA_EMAIL, Sendto);
                            SendEmail.putExtra(Intent.EXTRA_SUBJECT, "[" + rb2.getText().toString() + "]" + "New Report");
                            SendEmail.putExtra(Intent.EXTRA_TEXT, "" + edit_description.getText().toString());
                            SendEmail.setType("message/rfc822");
                            Intent.createChooser(SendEmail, "Send Email");
                            startActivity(SendEmail);
                            edit_description.setText("");
                        } catch (Exception e) {
                            Toast.makeText(SettingsActivity.this, getResources().getString(R.string.no_app), Toast.LENGTH_SHORT).show();
                        }
                    } else if (rb3.isChecked()) {
                        try {
                            String[] Sendto = {"mosalah955@gmail.com"};
                            Intent SendEmail = new Intent(Intent.ACTION_SEND);
                            SendEmail.putExtra(Intent.EXTRA_EMAIL, Sendto);
                            SendEmail.putExtra(Intent.EXTRA_SUBJECT, "[" + rb3.getText().toString() + "]" + "New Report");
                            SendEmail.putExtra(Intent.EXTRA_TEXT, "" + edit_description.getText().toString());
                            SendEmail.setType("message/rfc822");
                            Intent.createChooser(SendEmail, "Send Email");
                            startActivity(SendEmail);
                            edit_description.setText("");
                        } catch (Exception e) {
                            Toast.makeText(SettingsActivity.this, getResources().getString(R.string.no_app), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                }

            }
        });


        edit_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int max = edit_description.length();
                txt_editmax.setText(max + " / 500");
                if (max >= 0 && max < 50) {
                    txt_editmax.setTextColor(Color.parseColor("#FF000000"));
                } else if (max >= 50 && max < 500) {
                    txt_editmax.setTextColor(Color.parseColor("#FFE17A2C"));
                } else if (max == 500) {
                    txt_editmax.setTextColor(Color.parseColor("#FFDC141B"));
                }
            }

        });
    }

    public void stars(View view) {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void restart(View view) {
        //SaveSystem.SaveIntData("Coins",0);
        SaveSystem.SaveIntData("Level",0);
        SaveSystem.SaveIntData("MoreCoins",0);
        SaveSystem.SaveIntData("LessCoins",0);
        SaveSystem.SaveIntData("MoreTime",0);
        SaveSystem.SaveStringData("FirstTime", "1");
        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.reset), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        finish();
    }
}
