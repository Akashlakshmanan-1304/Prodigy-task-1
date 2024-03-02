package com.application.simplecalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private StringBuilder currentNumber;
    private double firstOperand;
    private String operator;
    private boolean isOperatorClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        // Initialize views
        textView = findViewById(R.id.textView);

        // Initialize variables
        currentNumber = new StringBuilder();
        firstOperand = 0;
        operator = "";
        isOperatorClicked = false;

        // Set click listeners for buttons
        findViewById(R.id.ac).setOnClickListener(this);
        findViewById(R.id.C).setOnClickListener(this);
        findViewById(R.id.mod).setOnClickListener(this);
        findViewById(R.id.divide).setOnClickListener(this);
        findViewById(R.id.mul).setOnClickListener(this);
        findViewById(R.id.sub).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.n0).setOnClickListener(this);
        findViewById(R.id.n1).setOnClickListener(this);
        findViewById(R.id.n2).setOnClickListener(this);
        findViewById(R.id.n3).setOnClickListener(this);
        findViewById(R.id.n4).setOnClickListener(this);
        findViewById(R.id.n5).setOnClickListener(this);
        findViewById(R.id.n6).setOnClickListener(this);
        findViewById(R.id.n7).setOnClickListener(this);
        findViewById(R.id.n8).setOnClickListener(this);
        findViewById(R.id.n9).setOnClickListener(this);
        findViewById(R.id.dot).setOnClickListener(this);
        findViewById(R.id.eq).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ac) {
            clearAll();
        } else if (id == R.id.C) {
            clear();
        } else if (id == R.id.mod || id == R.id.divide || id == R.id.mul || id == R.id.sub || id == R.id.add) {
            setOperator(((Button) v).getText().toString());
        } else if (id == R.id.n0 || id == R.id.n1 || id == R.id.n2 || id == R.id.n3 || id == R.id.n4 || id == R.id.n5 || id == R.id.n6 || id == R.id.n7 || id == R.id.n8 || id == R.id.n9 || id == R.id.dot) {
            appendNumber(((Button) v).getText().toString());
        } else if (id == R.id.eq) {
            calculate();
        }
    }


    private void clearAll() {
        currentNumber.setLength(0);
        firstOperand = 0;
        operator = "";
        isOperatorClicked = false;
        updateDisplay("");
    }

    private void clear() {
        currentNumber.setLength(0);
        updateDisplay("");
    }

    private void setOperator(String op) {
        if (!isOperatorClicked && currentNumber.length() > 0) {
            try {
                firstOperand = Double.parseDouble(currentNumber.toString());
                operator = op;
                isOperatorClicked = true;

                updateDisplay(op);

                // Update display to clear current number
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void appendNumber(String num) {
        if (isOperatorClicked) {
            currentNumber.setLength(0);
            isOperatorClicked = false;
        }
        currentNumber.append(num);
        updateDisplay(currentNumber.toString());
    }

    private void calculate() {
        if (currentNumber.length() > 0 && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentNumber.toString());
            double result = 0;
            Log.d("Calculator", "First operand: " + firstOperand);
            Log.d("Calculator", "Second operand: " + secondOperand);
            switch (operator) {
                case "%":
                    result = firstOperand % secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        updateDisplay("Error");
                        return;
                    }
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "+":
                    result = firstOperand + secondOperand;
                    break;
            }
            Log.d("Calculator", "Result: " + result);
            updateDisplay(String.valueOf(result));
            currentNumber.setLength(0);
            currentNumber.append(result);
            isOperatorClicked = true;
        }
    }



    private void updateDisplay(String text) {
        if (!text.isEmpty()) {
            double value = Double.parseDouble(text);
            if (Double.isInfinite(value)) {
                textView.setText("Error: Division by zero");
            } else if (value == (long) value) {
                textView.setText(String.format("%d", (long) value));
            } else {
                textView.setText(String.format("%.7f", value));
            }
        } else {
            textView.setText("");
        }
    }
}
