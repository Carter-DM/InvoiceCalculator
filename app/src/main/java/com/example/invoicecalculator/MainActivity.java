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
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private EditText subtotalEditText;
    private EditText discountPercentageEditText;
    private TextView discountTotalViewField;
    private TextView totalViewField;

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

        subtotalEditText = (EditText) findViewById(R.id.subtotalEditText);
        discountPercentageEditText = (EditText) findViewById(R.id.discountPercentEditText);
        discountTotalViewField = (TextView) findViewById(R.id.discountTotalTextView);
        totalViewField = (TextView) findViewById(R.id.totalTextView);

        subtotalEditText.setOnEditorActionListener(this);
        discountPercentageEditText.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        calculateAndDisplay();
        super.onResume();
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
     * Calculate and Display the discount and total values.
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

        // Getting discount percentage field total
        String discountPercentageString = discountPercentageEditText.getText().toString();
        float discountPercentage;
        if (discountPercentageString.equals("")) {
            discountPercentage = 0;
        }
        else {
            discountPercentage = Float.parseFloat(discountPercentageString);
        }

        // Calculate tip and total
        float discount = subtotalAmount * (discountPercentage / 100);
        float total = subtotalAmount - discount;

        // Display values
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        discountTotalViewField.setText(currency.format(discount));
        totalViewField.setText(currency.format(total));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            calculateAndDisplay();
        }
        return false;
    }
}
