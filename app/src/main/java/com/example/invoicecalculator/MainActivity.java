package com.example.invoicecalculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText subtotalEditText;
    private TextView tipPercentageTextView;
    private SeekBar tipPercentageSeekBar;
    private Button tipPercentageApplyButton;
    private TextView tipTotalTextView;
    private TextView totalTextView;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        subtotalEditText = findViewById(R.id.subtotalEditText);
        tipPercentageTextView = findViewById(R.id.tipPercentTextView);
        tipPercentageSeekBar = findViewById(R.id.tipPercentageSeekBar);
        tipPercentageApplyButton = findViewById(R.id.tipPercentageApplyButton);
        tipTotalTextView = findViewById(R.id.tipTotalTextView);
        totalTextView = findViewById(R.id.totalTextView);

        /*
        ============================================================================================
        ======================================== LISTENERS =========================================
        ============================================================================================
         */

        subtotalEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    calculateAndDisplay();
                }
                return false;
            }
        });

        tipPercentageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                displayTipPercentage(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        tipPercentageApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplay();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Display percentage value based on seekbar progress.
     */
    public void displayTipPercentage(int progress) {
        tipPercentageTextView.setText((progress * 5) + "%");
    }

    /**
     * Calculate and Display the tip and total values.
     */
    public void calculateAndDisplay(){
        // Getting subtotal amount field value
        String subtotalAmountString = subtotalEditText.getText().toString();
        float subtotalAmount;
        if (subtotalAmountString.equals("")) {
            subtotalAmount = 0;
        }
        else {
            subtotalAmount = Float.parseFloat(subtotalAmountString);
        }

        // Getting tip percentage field total
        String tipPercentageString = tipPercentageTextView.getText().toString().replace("%", "");
        float tipPercentage = Float.parseFloat(tipPercentageString);

        // Calculate tip and total
        float tip = subtotalAmount * (tipPercentage / 100);
        float total = subtotalAmount + tip;

        // Display values
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTotalTextView.setText(currency.format(tip));
        totalTextView.setText(currency.format(total));
    }
}
