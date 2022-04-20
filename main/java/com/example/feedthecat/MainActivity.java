package com.example.feedthecat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button feedButton;
    private TextView clickField;
    private CharSequence temp;
    private ImageView catImg;
    private Button shareButton;
    private Button aboutButton;
    private Button tableButton;
    private Button resetButton;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickField = findViewById(R.id.clickField);
        feedButton = findViewById(R.id.feedButton);
        catImg = findViewById(R.id.catImg);
        shareButton = findViewById(R.id.shareButton);
        aboutButton = findViewById(R.id.aboutButton);
        tableButton = findViewById(R.id.tableButton);
        resetButton = findViewById(R.id.resetButton);

        sPref = getSharedPreferences("myPref", MODE_PRIVATE);
        try {
            clickField.setText(sPref.getString("satietyCount", "0"));
        } catch (Exception e){
            clickField.setText("0");
        }

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = clickField.getText();
                int temp1 = Integer.valueOf(temp.toString());
                temp1++;
                if (temp1 % 15 == 0) {
                    RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(1 / 2);
                    anim.setDuration(700);
                    catImg.startAnimation(anim);
                }
                clickField.setText(Integer.toString(temp1));
                System.out.println(clickField.getText());
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("text/plain");
                String shareBody = "Я накормил усатого " + clickField.getText() + " раз в FeedTheCat!";
                String shareSub = "Пруфы";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                intent.setAction(Intent.ACTION_SEND);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ABOUT")
                        .setMessage("Сухарко Доместосы4, гр. 951006, лаба 1")
                        .setPositiveButton("Интересно", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("ABOUT");
                alert.show();
            }
        });



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentDate = new Date();
                currentDate.setTime(currentDate.getTime());

                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String dateText = dateFormat.format(currentDate);
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);

                try
                {
                    File internalStorageDir = getFilesDir();
                    File table = new File(internalStorageDir, "results.txt");
                    FileWriter writer = new FileWriter(table, true);
                    writer.write(clickField.getText() + "_" + dateText + "_" + timeText);
                    writer.append('\n');
                    writer.flush();
                }
                catch(Exception ex){
                    Log.d("oshibka", "ERROR");
                }
                clickField.setText("0");
            }
        });


    }

    public void onTableButton(View view) {
        SharedPreferences.Editor editorShare = sPref.edit();
        editorShare.putString("satietyCount", (String) clickField.getText());
        editorShare.commit();

        Intent intentResults = new Intent(this, Table.class);
        startActivity(intentResults);
    }
}