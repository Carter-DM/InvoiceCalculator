package com.example.invoicecalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class SettingsActivity extends AppCompatActivity {

    private EditText settingsDefaultTipEditText;
    private EditText settingsMaximumTipEditText;
    private Button settingsSaveButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences("TipCalculatorSharedPreferences", MODE_PRIVATE);

        settingsDefaultTipEditText = findViewById(R.id.settingsDefaultTipEditText);
        settingsMaximumTipEditText = findViewById(R.id.settingsMaximumTipEditText);
        settingsSaveButton = findViewById(R.id.settingsSaveButton);

        settingsDefaultTipEditText.setText(sharedPreferences.getString("defaultTip", "15"));
        settingsMaximumTipEditText.setText(sharedPreferences.getString("maxTip", "100"));

        /*
        ============================================================================================
        ======================================== LISTENERS =========================================
        ============================================================================================
         */

        settingsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedPreferences();
            }
        });
    }

    private void setSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get Default Tip Value
        String defaultTip;
        if (String.valueOf(settingsDefaultTipEditText.getText()).equals("")) {
            defaultTip = "0";
        }
        else {
            defaultTip = settingsDefaultTipEditText.getText().toString();
        }

        // Get Max Tip Value
        String maxTip;
        if (String.valueOf(settingsMaximumTipEditText.getText()).equals("")) {
            maxTip = "0";
        }
        else {
            maxTip = settingsMaximumTipEditText.getText().toString();
        }

        editor.putString("defaultTip", defaultTip);
        editor.putString("maxTip", maxTip);

        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
