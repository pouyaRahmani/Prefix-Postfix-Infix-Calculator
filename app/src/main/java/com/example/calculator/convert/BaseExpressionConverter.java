package com.example.calculator.convert;

// BaseExpressionConverter Abstract Class
public abstract class BaseExpressionConverter implements ExpressionConverter {

    // Checks if the token is a valid number (integer or floating-point)
    protected boolean isNumeric(String token) {
        return token.matches("-?\\d+(?:\\.\\d+)?");
    }

    // Checks if the token is a valid operator (+, -, *, /)
    protected boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
}
