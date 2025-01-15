package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Evaluates infix expressions
public class InfixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> values = new Stack<>(); // Stack to store numeric values
        Stack<Character> operators = new Stack<>(); // Stack to store operators
        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(\\.\\d+)?|[-+*/()]|\\S)\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        // Tokenize the expression and process each token
        while (matcher.find()) {
            String token = matcher.group().trim();
            if (isNumeric(token)) {
                values.push(Double.parseDouble(token)); // Push numeric values to the stack
            } else if (token.equals("(")) {
                operators.push('('); // Push opening parentheses to the stack
            } else if (token.equals(")")) {
                // Process until a matching opening parenthesis is found
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double b = values.pop();
                    double a = values.pop();
                    values.push(applyOp(String.valueOf(operators.pop()), a, b));
                }
                operators.pop(); // Remove the opening parenthesis
            } else if (isOperator(token)) {
                char op = token.charAt(0);
                // Process operators with higher or equal precedence
                while (!operators.isEmpty() && precedence(op) <= precedence(operators.peek())) {
                    double b = values.pop();
                    double a = values.pop();
                    values.push(applyOp(String.valueOf(operators.pop()), a, b));
                }
                operators.push(op); // Push the current operator
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            values.push(applyOp(String.valueOf(operators.pop()), a, b));
        }

        return values.pop(); // Return the final result
    }

    // Returns the precedence of operators
    private int precedence(char operator) {
        return (operator == '+' || operator == '-') ? 1 : (operator == '*' || operator == '/') ? 2 : 0;
    }
}