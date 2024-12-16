package com.example.calculator.convert;

import java.util.Stack;

// Postfix to Infix Converter
public class PostfixToInfixConverter extends BaseExpressionConverter {

    @Override
    public String convert(String expression) throws IllegalArgumentException {
        Stack<String> stack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid Postfix Expression");
                }
                String b = stack.pop();
                String a = stack.pop();
                stack.push("(" + a + " " + token + " " + b + ")");
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid Postfix Expression");
        }

        return stack.pop();
    }
}
