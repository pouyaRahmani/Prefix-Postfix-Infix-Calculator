package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

            if (expression.isEmpty()) {
                showErrorToast("Please enter a valid expression.");
                return;
            }

            try {
                double result = ExpressionHandler.evaluate(expression, selectedFormat);
                resultView.setText(String.valueOf(result));
                Toast.makeText(this, "Calculation successful!", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                resultView.setText("Error");
                showErrorToast("Invalid expression: " + e.getMessage());
            } catch (ArithmeticException e) {
                resultView.setText("Error");
                showErrorToast("Math error: " + e.getMessage());
            } catch (Exception e) {
                resultView.setText("Error");
                showErrorToast("Unknown error: " + e.getMessage());
            }
        });
    }

    private void showErrorToast(String message) {
        // Inflate the custom Toast layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_error, null);  // Use 'null' for the parent view

        // Set the message text
        TextView textView = layout.findViewById(R.id.errorMessage);
        textView.setText(message);

        // Create and configure the Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0); // Center the Toast on the screen
        toast.show();
    }
}
