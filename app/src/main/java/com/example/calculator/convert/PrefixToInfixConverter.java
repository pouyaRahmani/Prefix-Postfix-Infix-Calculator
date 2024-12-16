package com.example.calculator.convert;

import java.util.Stack;

// Prefix to Infix Converter
public class PrefixToInfixConverter extends BaseExpressionConverter {

    @Override
    public String convert(String expression) throws IllegalArgumentException {
        Stack<String> stack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");

        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isNumeric(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid Prefix Expression");
                }
                String a = stack.pop();
                String b = stack.pop();
                stack.push("(" + a + " " + token + " " + b + ")");
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid Prefix Expression");
        }

        return stack.pop();
    }
}
