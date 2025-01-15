package com.example.calculator.convert;

import java.util.Stack;

// Infix with Parentheses Converter
public class InfixWithParenthesesConverter extends BaseExpressionConverter {

    @Override
    public String convert(String expression) throws IllegalArgumentException {
        expression = expression.replaceAll("\\s+", " ").trim(); // Clean up spaces
        // Check if the expression is a valid infix
        if (!isValidInfix(expression)) {
            throw new IllegalArgumentException("Invalid Infix Expression");
        }

        // Format the expression with parentheses
        return formatWithParentheses(expression);
    }

    // Validates if the given infix expression has correct syntax (operands, operators, parentheses)
    private boolean isValidInfix(String expression) {
        Stack<Character> stack = new Stack<>();
        boolean expectOperand = true;
        boolean lastWasOperator = false;

        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (isNumeric(token)) {
                if (!expectOperand) return false;
                expectOperand = false;
            } else if (isOperator(token)) {
                if (expectOperand || lastWasOperator) return false;
                expectOperand = true;
            } else if (token.equals("(")) {
                stack.push('(');
                expectOperand = true;
            } else if (token.equals(")")) {
                if (expectOperand || stack.isEmpty() || stack.pop() != '(') return false;
            } else {
                return false;
            }
            lastWasOperator = isOperator(token);
        }
        // Expression is valid if no operands are expected and parentheses are balanced
        return !expectOperand && stack.isEmpty();
    }

    // Converts the infix expression to a fully parenthesized format
    private String formatWithParentheses(String expression) {
        Stack<String> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                operands.push(token);
            } else if (isOperator(token)) {
                // Pop operators with higher or equal precedence and combine operands
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    String op = operators.pop();
                    String b = operands.pop();
                    String a = operands.pop();
                    operands.push("(" + a + " " + op + " " + b + ")");
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                // Resolve the parentheses and pop operators inside parentheses
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    String op = operators.pop();
                    String b = operands.pop();
                    String a = operands.pop();
                    operands.push("(" + a + " " + op + " " + b + ")");
                }
                operators.pop(); // Remove the "("
            }
        }

        // Process any remaining operators
        while (!operators.isEmpty()) {
            String op = operators.pop();
            String b = operands.pop();
            String a = operands.pop();
            operands.push("(" + a + " " + op + " " + b + ")");
        }

        return operands.pop(); // Final result
    }

    // Returns the precedence of operators (higher number means higher precedence)
    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}
