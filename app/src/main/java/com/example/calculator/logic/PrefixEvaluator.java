package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PrefixEvaluator extends Expression {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>();
        Pattern tokenPattern = Pattern.compile("-?\\d+(?:\\.\\d+)?|[-+*/]");
        Matcher matcher = tokenPattern.matcher(expression);

        Stack<String> tokens = new Stack<>();
        while (matcher.find()) {
            tokens.push(matcher.group());
        }

        while (!tokens.isEmpty()) {
            String token = tokens.pop();

            if (token.matches("-?\\d+(?:\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double val1 = stack.pop();
                double val2 = stack.pop();
                stack.push(applyOp(token, val1, val2));
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
