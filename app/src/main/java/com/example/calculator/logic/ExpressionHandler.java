package com.example.calculator.logic;

import java.util.Stack;

public class ExpressionHandler implements ExpressionParser {

    @Override
    public String evaluateInfix(String expression) throws Exception {
        if (!isValidExpression(expression)) {
            throw new Exception("Invalid Infix Expression");
        }
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    @Override
    public String evaluatePostfix(String expression) throws Exception {
        if (!isValidExpression(expression)) {
            throw new Exception("Invalid Postfix Expression");
        }

        Stack<Integer> stack = new Stack<>();
        for (String token : expression.split("\\s")) {
            if (isNumeric(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(applyOperation(a, b, token));
            }
        }
        return stack.pop().toString();
    }

    @Override
    public String evaluatePrefix(String expression) throws Exception {
        if (!isValidExpression(expression)) {
            throw new Exception("Invalid Prefix Expression");
        }

        Stack<Integer> stack = new Stack<>();
        String[] tokens = expression.split("\\s");
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (isNumeric(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(applyOperation(a, b, token));
            }
        }
        return stack.pop().toString();
    }

    private String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(c).append(' ');
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                stack.pop(); // Remove '('
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }
        return result.toString().trim();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private int applyOperation(int a, int b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private boolean isValidExpression(String expression) {
        // Placeholder for robust validation logic
        return true;
    }
}
