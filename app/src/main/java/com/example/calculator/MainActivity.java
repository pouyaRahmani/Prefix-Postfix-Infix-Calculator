package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import com.example.calculator.logic.ExpressionHandler;

public class MainActivity extends AppCompatActivity {

    private EditText inputExpression;
    private Spinner formatSpinner;
    private Button calculateButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputExpression = findViewById(R.id.inputExpression);
        formatSpinner = findViewById(R.id.formatSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        resultView = findViewById(R.id.resultView);

        String[] formats = {"Infix", "Postfix", "Prefix"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, formats);
        formatSpinner.setAdapter(adapter);

        calculateButton.setOnClickListener(view -> {
            String expression = inputExpression.getText().toString().trim();
            String selectedFormat = formatSpinner.getSelectedItem().toString();
            ExpressionHandler handler = new ExpressionHandler();

            try {
                String result;
                switch (selectedFormat) {
                    case "Infix":
                        result = handler.evaluateInfix(expression);
                        break;
                    case "Postfix":
                        result = handler.evaluatePostfix(expression);
                        break;
                    case "Prefix":
                        result = handler.evaluatePrefix(expression);
                        break;
                    default:
                        result = "Unknown format!";
                }
                resultView.setText(result);
            } catch (Exception e) {
                resultView.setText("Error: " + e.getMessage());
            }
        });
    }
}
