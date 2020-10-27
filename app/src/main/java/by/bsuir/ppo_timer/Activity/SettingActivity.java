package by.bsuir.ppo_timer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

public class SettingActivity extends PreferenceActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("theme",true))
        {
            setTheme(R.style.Theme_AppCompat);
        }



        String font = sp.getString("fontSize", "");
        String listValue = sp.getString("test_lang", "не выбрано");
        Locale locale;
        if (listValue.equals("English") || listValue.equals("Английский")) {
            locale   = new Locale("en");
        }
        else {
            locale = new Locale("ru");
        }
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        if (font.equals("Малый") || font.equals("Small")) {
            configuration.fontScale = (float) 0.85;
        } else if (font.equals("Нормальный") || font.equals("Normal")) {
            configuration.fontScale = (float) 1;
        } else {
            configuration.fontScale = (float) 1.15;
        }
        getBaseContext().getResources().updateConfiguration(configuration, null);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
        super.onBackPressed();
    }


    public static class MyPreferenceFragment extends PreferenceFragment {

        private DBViewModel mViewModel;

        @Override
        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            Preference button = findPreference("DeleteAll");
            Preference deleteAll = findPreference("test_lang");
            Preference theme = findPreference("theme");
            Preference font = findPreference("fontSize");
            theme.setOnPreferenceChangeListener((preference, newValue) -> {
                if ((boolean) newValue) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                getActivity().recreate();
                return true;
            });
            deleteAll.setOnPreferenceChangeListener((preference, newValue) -> {
                if (newValue.toString().equals("English") || newValue.toString().equals("Английский")) {
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getActivity().getBaseContext().getResources().updateConfiguration(configuration, null);
                } else {
                    Locale locale = new Locale("ru");
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getActivity().getResources().updateConfiguration(configuration, null);
                }

                return true;

            });
            button.setOnPreferenceClickListener(preference -> {
                mViewModel = new DBViewModel(getActivity().getApplication());
                mViewModel.removeAll();
                return true;
            });

            font.setOnPreferenceChangeListener((preference, newValue) -> {
                Configuration configuration = getResources().getConfiguration();
                if (newValue.toString().equals("Малый") || newValue.toString().equals("Small")) {
                    configuration.fontScale = (float) 0.85;
                } else if (newValue.toString().equals("Нормальный") || newValue.toString().equals("Normal")) {
                    configuration.fontScale = (float) 1;
                } else {
                    configuration.fontScale = (float) 1.15;
                }
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                metrics.scaledDensity = configuration.fontScale * metrics.density;
                getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);

                return true;
            });
        }


    }
    public void restartFragment() {
        MyPreferenceFragment fragment = new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }
}