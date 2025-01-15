package com.example.calculator.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

// Validates input expressions for different formats
public class InputValidator {

    // Checks if an infix expression is valid
    public static boolean isValidInfix(String expression) {
        if (!areParenthesesBalanced(expression)) return false; // Check parentheses

        String infixPattern = "\\s*(-?\\d+(\\.\\d+)?|[-+*/()]|\\s)+";
        if (!expression.matches(infixPattern)) return false;

        String[] tokens = tokenize(expression);
        return isInfixTokenOrderValid(tokens); // Check token order
    }

    // Checks if a prefix expression is valid
    public static boolean isValidPrefix(String expression) {
        String[] tokens = tokenize(expression);
        return isPrefixTokenOrderValid(tokens);
    }

    // Checks if a postfix expression is valid
    public static boolean isValidPostfix(String expression) {
        String[] tokens = tokenize(expression);
        return isPostfixTokenOrderValid(tokens);
    }

    // Checks if parentheses are balanced
    private static boolean areParenthesesBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            if (ch == '(') stack.push(ch);
            else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') return false;
            }
        }
        return stack.isEmpty();
    }

    // Splits the expression into tokens
    private static String[] tokenize(String expression) {
        Pattern tokenPattern = Pattern.compile("-?\\d+(\\.\\d+)?|[-+*/()]");
        Matcher matcher = tokenPattern.matcher(expression);

        // Collect tokens into a list
        java.util.List<String> tokens = new java.util.ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        // Convert the list to an array and return
        return tokens.toArray(new String[0]);
    }

    // Checks if the order of tokens in an infix expression is valid
    private static boolean isInfixTokenOrderValid(String[] tokens) {
        boolean expectOperand = true;

        for (String token : tokens) {
            if (isNumeric(token)) {
                if (!expectOperand) return false;
                expectOperand = false;
            } else if (isOperator(token)) {
                if (expectOperand) return false;
                expectOperand = true;
            } else if (token.equals("(")) {
                expectOperand = true;
            } else if (token.equals(")")) {
                if (expectOperand) return false;
            }
        }
        return !expectOperand;
    }

    // Checks if the order of tokens in a prefix expression is valid
    private static boolean isPrefixTokenOrderValid(String[] tokens) {
        int operatorCount = 0;
        int operandCount = 0;

        for (String token : tokens) {
            if (isOperator(token)) operatorCount++;
            else if (isNumeric(token)) {
                operandCount++;
                if (operandCount > operatorCount + 1) return false;
            } else return false;
        }
        return operandCount == operatorCount + 1;
    }

    // Checks if the order of tokens in a postfix expression is valid
    private static boolean isPostfixTokenOrderValid(String[] tokens) {
        int operandCount = 0;

        for (String token : tokens) {
            if (isNumeric(token)) operandCount++;
            else if (isOperator(token)) {
                if (operandCount < 2) return false;
                operandCount--;
            } else return false;
        }
        return operandCount == 1;
    }

    // Checks if a token is numeric
    private static boolean isNumeric(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    // Checks if a token is an operator
    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
}