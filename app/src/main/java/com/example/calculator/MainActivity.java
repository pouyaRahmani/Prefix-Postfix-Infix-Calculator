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

                String infixExpression = convertToInfix(expression, selectedFormat);


                conversionResultView.setText("Infix: " + infixExpression);


                double result = ExpressionHandler.evaluate(infixExpression, "Infix");


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
