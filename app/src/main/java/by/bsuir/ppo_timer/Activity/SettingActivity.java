package by.bsuir.ppo_timer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import by.bsuir.ppo_timer.R;
import by.bsuir.ppo_timer.ViewModel.DBViewModel;

public class SettingActivity extends PreferenceActivity {
    DBViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
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
                if ((boolean) newValue == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
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
                onCreate(null);
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
                    configuration.fontScale=(float) 0.85;
                } else if (newValue.toString().equals("Нормальный") || newValue.toString().equals("Normal")) {
                    configuration.fontScale=(float) 1;
                } else {
                    configuration.fontScale=(float) 1.15;
                }
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                metrics.scaledDensity = configuration.fontScale * metrics.density;
                getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
//                setPreferenceScreen(null);

                onCreate(null);
                return true;
            });
        }
    }
}