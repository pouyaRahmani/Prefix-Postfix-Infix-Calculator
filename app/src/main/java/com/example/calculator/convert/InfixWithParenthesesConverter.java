package com.example.calculator.convert;

import java.util.Stack;

// Infix with Parentheses Converter
public class InfixWithParenthesesConverter extends BaseExpressionConverter {

    @Override
    public String convert(String expression) throws IllegalArgumentException {
        expression = expression.replaceAll("\\s+", " ").trim(); // Clean up spaces
        if (!isValidInfix(expression)) {
            throw new IllegalArgumentException("Invalid Infix Expression");
        }

        return formatWithParentheses(expression);
    }

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
        return !expectOperand && stack.isEmpty();
    }

    private String formatWithParentheses(String expression) {
        Stack<String> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isNumeric(token)) {
                operands.push(token);
            } else if (isOperator(token)) {
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
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    String op = operators.pop();
                    String b = operands.pop();
                    String a = operands.pop();
                    operands.push("(" + a + " " + op + " " + b + ")");
                }
                operators.pop(); // Remove the "("
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            String b = operands.pop();
            String a = operands.pop();
            operands.push("(" + a + " " + op + " " + b + ")");
        }

        return operands.pop();
    }

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
