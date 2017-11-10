package com.example.sl.yigongbang.util;

import android.media.AudioManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sl.yigongbang.R;

public class VoiceSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_setting);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        CheckBox shock=(CheckBox) findViewById(R.id.shock);
        shock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
            }
        });
        ImageView voiceplus=(ImageView) findViewById(R.id.voice_plus);
        voiceplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
            }
        });
        ImageView voicemini=(ImageView) findViewById(R.id.voice_mini);
        voicemini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
            }
        });
    }
}
