package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PostfixEvaluator extends Expression {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>();
        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(?:\\.\\d+)?|[-+*/])\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim();

            if (token.matches("-?\\d+(?:\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double val1 = stack.pop();
                double val2 = stack.pop();
                stack.push(applyOp(token, val2, val1));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        return stack.pop();
    }

    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    private double applyOp(String op, double a, double b) {
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
