package com.codesui.allcalc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codesui.allcalc.ads.BannerManager;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

public class BasicCalculatorFragment extends Fragment {
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char PERCENT = '%';
    private char currentSymbol;
    private double firstValue = Double.NaN;
    private double secondValue;
    private TextView inputDisplay, outputDisplay;
    private DecimalFormat decimalFormat;
    private MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
            buttonDot, buttonAdd, buttonSub, buttonMultiply, buttonDivide, buttonPercent, buttonClear, buttonOFF, buttonEqual;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fixing DecimalFormat initialization
        decimalFormat = new DecimalFormat("#.####"); // Formats up to 4 decimal places
        inputDisplay = view.findViewById(R.id.input);
        outputDisplay = view.findViewById(R.id.output);


        button0 = view.findViewById(R.id.btn0);
        button1 = view.findViewById(R.id.btn1);
        button2 = view.findViewById(R.id.btn2);
        button3 = view.findViewById(R.id.btn3);
        button4 = view.findViewById(R.id.btn4);
        button5 = view.findViewById(R.id.btn5);
        button6 = view.findViewById(R.id.btn6);
        button7 = view.findViewById(R.id.btn7);
        button8 = view.findViewById(R.id.btn8);
        button9 = view.findViewById(R.id.btn9);
        buttonAdd = view.findViewById(R.id.add);
        buttonSub = view.findViewById(R.id.subtract);
        buttonDivide = view.findViewById(R.id.division);
        buttonDot = view.findViewById(R.id.btnPoint);
        buttonMultiply = view.findViewById(R.id.multiply);
        buttonClear = view.findViewById(R.id.clear);
        buttonOFF = view.findViewById(R.id.off);
        buttonEqual = view.findViewById(R.id.equal);
        buttonPercent = view.findViewById(R.id.percent);
        button0.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "0"));
        button1.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "1"));
        button2.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "2"));
        button3.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "3"));
        button4.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "4"));
        button5.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "5"));
        button6.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "6"));
        button7.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "7"));
        button8.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "8"));
        button9.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "9"));
        buttonAdd.setOnClickListener(v -> {
            allCalculations();
            currentSymbol = ADDITION;
            outputDisplay.setText(decimalFormat.format(firstValue) + "+");
            inputDisplay.setText(null);
        });
        buttonSub.setOnClickListener(v -> {
            allCalculations();
            currentSymbol = SUBTRACTION;
            outputDisplay.setText(decimalFormat.format(firstValue) + "-");
            inputDisplay.setText(null);
        });
        buttonMultiply.setOnClickListener(v -> {
            allCalculations();
            currentSymbol = MULTIPLICATION;
            outputDisplay.setText(decimalFormat.format(firstValue) + "x");
            inputDisplay.setText(null);
        });
        buttonDivide.setOnClickListener(v -> {
            allCalculations();
            currentSymbol = DIVISION;
            outputDisplay.setText(decimalFormat.format(firstValue) + "/");
            inputDisplay.setText(null);
        });
        buttonPercent.setOnClickListener(v -> {
            allCalculations();
            currentSymbol = PERCENT;
            outputDisplay.setText(decimalFormat.format(firstValue) + "%");
            inputDisplay.setText(null);
        });
        buttonDot.setOnClickListener(v -> inputDisplay.setText(inputDisplay.getText() + "."));
        buttonClear.setOnClickListener(v -> {
            if (inputDisplay.getText().length() > 0) {
                CharSequence currentText = inputDisplay.getText();
                inputDisplay.setText(currentText.subSequence(0, currentText.length() - 1));
            } else {
                firstValue = Double.NaN;
                secondValue = Double.NaN;
                inputDisplay.setText("");
                outputDisplay.setText("");
            }
        });
        buttonOFF.setOnClickListener(v -> {
            //finish();
        });
        buttonEqual.setOnClickListener(view1 -> {
            allCalculations();
            outputDisplay.setText(decimalFormat.format(firstValue));
            firstValue = Double.NaN;
            currentSymbol = '0';
        });
        FrameLayout adViewContainer = view.findViewById(R.id.adViewContainer);
        BannerManager bannerManager = new BannerManager(requireContext(), requireActivity(), adViewContainer);
        bannerManager.loadBanner();
    }
    private void allCalculations() {
        if (!Double.isNaN(firstValue)) {
            try {
                // Parse the second value
                secondValue = Double.parseDouble(inputDisplay.getText().toString());

                // Perform the calculation based on the current operation
                switch (currentSymbol) {
                    case ADDITION:
                        firstValue += secondValue;
                        break;
                    case SUBTRACTION:
                        firstValue -= secondValue;
                        break;
                    case MULTIPLICATION:
                        firstValue *= secondValue;
                        break;
                    case DIVISION:
                        if (secondValue != 0) {
                            firstValue /= secondValue;
                        } else {
                            outputDisplay.setText("Error: Division by zero");
                            firstValue = Double.NaN;
                            return;
                        }
                        break;
                    case PERCENT:
                        firstValue %= secondValue;
                        break;
                }
            } catch (NumberFormatException e) {
                outputDisplay.setText("Error: Invalid input");
            }
            inputDisplay.setText(null);
        } else {
            try {
                // Parse the first value
                firstValue = Double.parseDouble(inputDisplay.getText().toString());
            } catch (NumberFormatException e) {
                outputDisplay.setText("Error: Invalid input");
            }
        }
    }
}