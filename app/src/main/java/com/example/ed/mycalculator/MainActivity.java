package com.example.ed.mycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView equationDisplay;
    private TextView result;
    private Double operand1 = null;
    private String pendingOperation = "";
    private static final String SAVED_EQUATION_DISPLAY = "Saved Display";
    private static final String SAVED_PENDING_OPERATION = "Saved Pending Operation";
    private static final String SAVED_RESULT = "Saved Result";
    private static final String SAVED_OPERAND = "Saved Operand";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        equationDisplay = (TextView) findViewById(R.id.equationDisplay);
        result = (TextView) findViewById(R.id.result);
        // Clear all default values from text displays.
        input.setText("");
        equationDisplay.setText("");
        result.setText("");

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        View.OnClickListener numListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (pendingOperation.equals("=")) {
                    /* If user enters "=" to total the equation. The next number button or "." hit
                       will clear fields and start a new calculation. */
                    operand1 = null;
                    input.append(b.getText().toString());
                    equationDisplay.setText("");
                    result.setText("");
                    pendingOperation = "";
                } else {
                    input.append(b.getText().toString());
                }
            }
        };

        button0.setOnClickListener(numListener);
        button1.setOnClickListener(numListener);
        button2.setOnClickListener(numListener);
        button3.setOnClickListener(numListener);
        button4.setOnClickListener(numListener);
        button5.setOnClickListener(numListener);
        button6.setOnClickListener(numListener);
        button7.setOnClickListener(numListener);
        button8.setOnClickListener(numListener);
        button9.setOnClickListener(numListener);
        buttonDot.setOnClickListener(numListener);

        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        Button buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonModulus = (Button) findViewById(R.id.buttonModulus);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = input.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    input.setText("");
                    // Limit equationDisplay to two rows of 36 characters. Clear and restart at 72.
                    if (equationDisplay.getText().length() >= 72) {
                        equationDisplay.setText(String.format(" %.2f", operand1));
                        equationDisplay.append(" " + op);
                    } else {
                        equationDisplay.append(" " + op);
                    }
                }
                pendingOperation = op;
            }
        };

        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonAdd.setOnClickListener(opListener);
        buttonSubtract.setOnClickListener(opListener);
        buttonEquals.setOnClickListener(opListener);
        buttonModulus.setOnClickListener(opListener);

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonBackspace = (Button) findViewById(R.id.buttonBackspace);
        Button buttonPosNeg = (Button) findViewById(R.id.buttonPosNeg);

        View.OnClickListener modifierListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();

                switch (op) {
                    case "C":
                        // Clear button. Reset all fields.
                        operand1 = null;
                        input.setText("");
                        equationDisplay.setText("");
                        result.setText("");
                        pendingOperation = "=";
                        break;
                    case "BS":
                        // Backspace button.
                        if (input.getText().length() > 0) {
                            input.getText().delete(input.getText().length() - 1, input.getText().length());
                        }
                        break;
                    case "+-":
                        // Positive / Negative button.
                        if (input.getText().toString().equals("")) {
                            input.append("-");
                        } else if (input.getText().toString().equals("-")) {
                            input.setText("");
                        } else {
                            Double num = Double.valueOf(input.getText().toString()) * -1;
                            String value = num.toString();
                            input.setText(value);
                        }
                        break;
                }
            }
        };

        buttonClear.setOnClickListener(modifierListener);
        buttonBackspace.setOnClickListener(modifierListener);
        buttonPosNeg.setOnClickListener(modifierListener);
    }

    private void performOperation(Double value, String op) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
        }

        switch (pendingOperation) {
            case "=":
                operand1 = value;
                break;
            case "/":
                // If user tries to divide by 0 give 0 as the result to catch exception.
                if (value == 0.0) {
                    operand1 = 0.0;
                } else {
                    operand1 /= value;
                }
                break;
            case "*":
                operand1 *= value;
                break;
            case "+":
                operand1 += value;
                break;
            case "-":
                operand1 -= value;
                break;
            case "%":
                operand1 %= value;
                break;
            }
        result.setText(String.format("%.2f", operand1));
        // Limit equationDisplay to two rows of 36 characters. Clear and restart at 72.
        if (equationDisplay.getText().length() >= 72) {
            equationDisplay.setText(String.format(" %.2f", operand1));
            equationDisplay.append(" " + op);
        } else {
            equationDisplay.append(" " + value.toString());
            equationDisplay.append(" " + op);
        }
        // When "=" is entered display the total after the = sign in equationDisplay.
        if (op.equals("=")) {
            equationDisplay.append(String.format(" %.2f", operand1));
        }
        input.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVED_EQUATION_DISPLAY, equationDisplay.getText().toString());
        outState.putString(SAVED_PENDING_OPERATION, pendingOperation);
        outState.putString(SAVED_RESULT, result.getText().toString());
        try {
            outState.putDouble(SAVED_OPERAND, operand1);
        } catch (NullPointerException e) {
            // If operand1 is null, set SAVED_OPERAND to input as long as a number has been entered.
            if (!input.getText().toString().equals("")) {
                outState.putDouble(SAVED_OPERAND, Double.valueOf(input.getText().toString()));
            } else {
                // Do nothing.
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        equationDisplay.setText(savedInstanceState.getString(SAVED_EQUATION_DISPLAY));
        pendingOperation = savedInstanceState.getString(SAVED_PENDING_OPERATION);
        result.setText(savedInstanceState.getString(SAVED_RESULT));
        operand1 = savedInstanceState.getDouble(SAVED_OPERAND);
    }
}
