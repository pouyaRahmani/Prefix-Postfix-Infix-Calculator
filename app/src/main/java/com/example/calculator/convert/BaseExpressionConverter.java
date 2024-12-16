package com.example.calculator.convert;

// BaseExpressionConverter Abstract Class
public abstract class BaseExpressionConverter implements ExpressionConverter {

    protected boolean isNumeric(String token) {
        return token.matches("-?\\d+(?:\\.\\d+)?");
    }

    protected boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
}
