package com.example.calculator.logic;

// Abstract base class for common utility methods in expression evaluators
public abstract class BaseExpressionEvaluator implements Expression {

    // Checks if the given token is a numeric value
    protected boolean isNumeric(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    // Checks if the given token is an operator
    protected boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    // Applies the given operator to two numbers and returns the result
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