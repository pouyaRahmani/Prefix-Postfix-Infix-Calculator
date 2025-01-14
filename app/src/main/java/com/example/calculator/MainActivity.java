package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.example.calculator.convert.InfixWithParenthesesConverter;
import com.example.calculator.convert.PostfixToInfixConverter;
import com.example.calculator.convert.PrefixToInfixConverter;
import com.example.calculator.logic.ExpressionHandler;
import com.example.calculator.logic.InputValidator;

public class MainActivity extends AppCompatActivity {

    private EditText inputExpression;
    private Spinner formatSpinner;
    private Button calculateButton;
    private TextView resultView;
    private TextView conversionResultView; // For displaying infix conversion results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputExpression = findViewById(R.id.inputExpression);
        formatSpinner = findViewById(R.id.formatSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        resultView = findViewById(R.id.resultView);
        conversionResultView = findViewById(R.id.conversionResultView);

        String[] formats = {"Infix", "Postfix", "Prefix"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, formats);
        formatSpinner.setAdapter(adapter);

        calculateButton.setOnClickListener(view -> {
            String expression = inputExpression.getText().toString().trim();
            String selectedFormat = formatSpinner.getSelectedItem().toString();

            if (expression.isEmpty()) {
                showErrorToast("Please enter a valid expression.");
                return;
            }

            try {
                // Validate the expression
                if (!validateExpression(expression, selectedFormat)) {
                    throw new IllegalArgumentException("Expression format is invalid for the selected type: " + selectedFormat);
                }

                // Convert to Infix
                String infixExpression = convertToInfix(expression, selectedFormat);

                // Display converted infix expression
                conversionResultView.setText("Infix: " + infixExpression);

                // Evaluate the infix expression
                double result = ExpressionHandler.evaluate(infixExpression, "Infix");

                // Display result
                resultView.setText("Result: " + result);
                Toast.makeText(this, "Calculation successful!", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                resultView.setText("Error");
                conversionResultView.setText("Error");
                showErrorToast("Invalid expression: " + e.getMessage());
            } catch (Exception e) {
                resultView.setText("Error");
                conversionResultView.setText("Error");
                showErrorToast("Unknown error: " + e.getMessage());
            }
        });
    }

    /**
     * Validates the user input expression based on the selected format.
     */
    private boolean validateExpression(String expression, String format) {
        switch (format) {
            case "Infix":
                return InputValidator.isValidInfix(expression);
            case "Postfix":
                return InputValidator.isValidPostfix(expression);
            case "Prefix":
                return InputValidator.isValidPrefix(expression);
            default:
                return false;
        }
    }

    /**
     * Converts the given expression to infix format based on the selected format.
     */
    private String convertToInfix(String expression, String format) throws IllegalArgumentException {
        switch (format) {
            case "Prefix":
                return new PrefixToInfixConverter().convert(expression);
            case "Postfix":
                return new PostfixToInfixConverter().convert(expression);
            case "Infix":
                return new InfixWithParenthesesConverter().convert(expression);
            default:
                throw new IllegalArgumentException("Invalid format selected.");
        }
    }

    /**
     * Displays a custom error toast with the given message.
     */
    private void showErrorToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_error, null);

        TextView textView = layout.findViewById(R.id.errorMessage);
        textView.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
