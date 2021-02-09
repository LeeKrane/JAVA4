package com.example.labor08_lotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = findViewById(R.id.numberSigned_input);
        editText.setFilters(new InputFilter[]{new MinMaxFilter(1, 10)});
    }

    public void showTips(View view) {
        try {
            String tips = generateTips();
            TextView textView = findViewById(R.id.textView_output);
            textView.setText(tips);
        } catch (IllegalArgumentException e) {
            tooltip(view, e.getMessage());
        }
    }

    private void tooltip(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }

    private String generateTips() {
        String value = ((EditText) findViewById(R.id.numberSigned_input)).getText().toString();
        if (value.isEmpty())
            throw new IllegalArgumentException("Please enter a tip count between 1 and 10!");

        int n = Integer.parseInt(value);
        int max = ((RadioButton) findViewById(R.id.rb_45)).isChecked() ? 45 : 49;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 6; j++) {
                builder.append(String.format(Locale.ENGLISH, "%2d", ThreadLocalRandom.current().nextInt(max) + 1));
                if (j < 5)
                    builder.append("  ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}