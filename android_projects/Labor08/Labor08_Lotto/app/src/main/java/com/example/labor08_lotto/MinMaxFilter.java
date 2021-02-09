package com.example.labor08_lotto;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxFilter implements InputFilter {
    private final int minValue;
    private final int maxValue;

    public MinMaxFilter (int minValue , int maxValue) {
        this.minValue = Math.min(minValue, maxValue);
        this.maxValue = Math.max(minValue, maxValue);
    }

    public MinMaxFilter (String minValue, String maxValue) {
        this(Integer.parseInt(minValue), Integer.parseInt(maxValue));
    }

    @Override
    public CharSequence filter (CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(minValue, maxValue, input))
                return null; // input will be written
        } catch (NumberFormatException ignored) {}
        return ""; // input will be replaced by an empty string
    }

    private boolean isInRange (int a, int b, int input) {
        return b > a
                ? input >= a && input <= b
                : input >= b && input <= a;
    }
}