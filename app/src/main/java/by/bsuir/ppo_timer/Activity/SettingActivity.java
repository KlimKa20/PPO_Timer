package by.bsuir.ppo_timer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
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
    int language_def;
    int font_def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("theme", true)) {
            setTheme(R.style.Theme_AppCompat);
        }
        String font = sp.getString("fontSize", "Малый");
        String listValue = sp.getString("test_lang", "Английский");
        Configuration configuration = new Configuration();

        Locale locale;
        if (listValue.equals("English") || listValue.equals("Английский")) {
            font_def = 1;
            locale = new Locale("en");
        } else {
            font_def = 0;
            locale = new Locale("ru");
        }
        Locale.setDefault(locale);
        configuration.locale = locale;

        if (font.equals("Малый") || font.equals("Small")) {
            language_def = 0;
            configuration.fontScale = (float) 0.85;
        } else if (font.equals("Нормальный") || font.equals("Normal")) {
            language_def = 1;
            configuration.fontScale = (float) 1;
        } else {
            language_def = 2;
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

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            Preference button = findPreference("DeleteAll");
            ListPreference language = (ListPreference) findPreference("test_lang");
            Preference theme = findPreference("theme");
            ListPreference font = (ListPreference) findPreference("fontSize");
            font.setValueIndex(((SettingActivity) getActivity()).language_def);
            language.setValueIndex(((SettingActivity) getActivity()).font_def);

            theme.setOnPreferenceChangeListener(this::onThemeChange);
            language.setOnPreferenceChangeListener(this::onLanguageChange);
            button.setOnPreferenceClickListener(this::onDeleteClick);
            font.setOnPreferenceChangeListener(this::onFontChange);
        }

        private boolean onThemeChange(Preference preference, Object newValue) {
            if ((boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            getActivity().recreate();
            return true;
        }

        private boolean onLanguageChange(Preference preference, Object newValue) {
            Locale locale;
            if (newValue.toString().equals("English") || newValue.toString().equals("Английский")) {
                locale = new Locale("en");
            } else {
                locale = new Locale("ru");
            }
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getActivity().getResources().updateConfiguration(configuration, null);
            getActivity().recreate();
            return true;
        }

        private boolean onDeleteClick(Preference preference) {
            DBViewModel mViewModel = new DBViewModel(getActivity().getApplication());
            mViewModel.removeAll();
            return true;
        }

        private boolean onFontChange(Preference preference, Object newValue) {
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
            getActivity().recreate();
            return true;
        }
    }
}