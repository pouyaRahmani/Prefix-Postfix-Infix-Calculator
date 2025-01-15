package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Evaluates prefix expressions
public class PrefixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>(); // Stack to store operands
        Pattern tokenPattern = Pattern.compile("-?\\d+(\\.\\d+)?|[-+*/]");
        Matcher matcher = tokenPattern.matcher(expression);

        Stack<String> tokens = new Stack<>(); // Stack to store tokens in reverse order
        while (matcher.find()) {
            tokens.push(matcher.group());
        }

        // Process tokens in reverse order
        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token)); // Push numeric values to the stack
            } else if (isOperator(token)) {
                double val1 = stack.pop(); // Pop two operands
                double val2 = stack.pop();
                stack.push(applyOp(token, val1, val2)); // Apply the operator and push the result
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        return stack.pop(); // Return the final result
    }
}