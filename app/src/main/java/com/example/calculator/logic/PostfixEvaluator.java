package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Evaluates postfix expressions
public class PostfixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>(); // Stack to store operands
        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(\\.\\d+)?|[-+*/])\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        // Process each token
        while (matcher.find()) {
            String token = matcher.group().trim();
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token)); // Push numeric values to the stack
            } else if (isOperator(token)) {
                double val1 = stack.pop(); // Pop two operands
                double val2 = stack.pop();
                stack.push(applyOp(token, val2, val1)); // Apply the operator and push the result
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        return stack.pop(); // Return the final result
    }
}