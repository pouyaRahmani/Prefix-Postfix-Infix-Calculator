package com.example.calculator.logic;

public abstract class BaseExpressionEvaluator implements Expression {
    protected boolean isNumeric(String token) {
        return token.matches("-?\\d+(?:\\.\\d+)?");
    }

    protected boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    protected double applyOp(String op, double a, double b) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
                return a / b;
            default: throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}
