package by.bsuir.ppo_timer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import by.bsuir.ppo_timer.R;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sp;
    private final int SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String listValue = sp.getString("test_lang", "не выбрано");
        boolean theme = sp.getBoolean("theme", false);
        String font = sp.getString("fontSize", "");

        Configuration configuration = new Configuration();
        Locale locale;
        if (listValue.equals("English") || listValue.equals("Английский")) {
            locale = new Locale("en");
        } else {
            locale = new Locale("ru");
        }
        Locale.setDefault(locale);
        configuration.locale = locale;


        if (font.equals("Малый") || font.equals("Small")) {
            configuration.fontScale = (float) 0.85;
        } else if (font.equals("Нормальный") || font.equals("Normal")) {
            configuration.fontScale = (float) 1;
        } else {
            configuration.fontScale = (float) 1.15;
        }

        getBaseContext().getResources().updateConfiguration(configuration, null);

        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(mainIntent);
            SplashScreenActivity.this.finish();
        }, SPLASH_DISPLAY_LENGHT);
    }
}