package com.example.my_java_calc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_java_calc.engine.CalculatorEngine;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String currentInput = "";

    TextView expressionView;
    TextView resultView;

    boolean justEvaluated = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expressionView = findViewById(R.id.expressionView);
        resultView = findViewById(R.id.resultView);
        GridLayout grid = (GridLayout) ((GridLayout) ((android.view.ViewGroup) display.getParent()).getChildAt(1));

        for (int i = 0; i < grid.getChildCount(); i++) {
            if (grid.getChildAt(i) instanceof Button) {
                Button btn = (Button) grid.getChildAt(i);

                btn.setOnClickListener(v -> {
                    String text = btn.getText().toString();
                    handleInput(text);
                });
            }
        }
    }

    private boolean isOperator(String val) {
        return "+-*/".contains(val);
    }

    private void handleInput(String value) {
        switch (value) {
            case "=":
                try {
                    double result = CalculatorEngine.calculate(currentInput);
                    resultView.setText(String.valueOf(result));
                    expressionView.setText(currentInput);

                    currentInput = String.valueOf(result);
                    justEvaluated = true;

                } catch (Exception e) {
                    resultView.setText("Error");
                    expressionView.setText("");
                    currentInput = "";
                }
                break;
            case "C":
                currentInput = "";
                expressionView.setText("");
                resultView.setText("0");
                justEvaluated = false;
                break;
            case "⌫":
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    updatePreview();
                }
                break;
            default:
                // Reset after result if user starts typing number
                if (justEvaluated && !isOperator(value)) {
                    currentInput = "";
                    expressionView.setText("");
                    justEvaluated = false;
                }
                // Prevent double operators
                if (isOperator(value)) {
                    if (currentInput.isEmpty()) return;

                    char last = currentInput.charAt(currentInput.length() - 1);
                    if (isOperator(String.valueOf(last))) return;
                }
                currentInput += value;
                updatePreview();
                break;
        }
    }

    private void updatePreview() {
        expressionView.setText(currentInput);
        try {
            double result = CalculatorEngine.calculate(currentInput);
            resultView.setText(String.valueOf(result));
        } catch (Exception e) {
            // Don't show error while typing
            resultView.setText("...");
        }
    }
}