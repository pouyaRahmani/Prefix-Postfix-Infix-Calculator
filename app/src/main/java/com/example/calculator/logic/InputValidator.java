package com.example.calculator.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class InputValidator {

    public static boolean isValidInfix(String expression) {
        if (!areParenthesesBalanced(expression)) return false;

        String infixPattern = "\\s*(-?\\d+(\\.\\d+)?|[-+*/()]|\\s)+";
        if (!expression.matches(infixPattern)) return false;

        String[] tokens = tokenize(expression);
        return isInfixTokenOrderValid(tokens);
    }

    public static boolean isValidPrefix(String expression) {
        String[] tokens = tokenize(expression);
        return isPrefixTokenOrderValid(tokens);
    }

    public static boolean isValidPostfix(String expression) {
        String[] tokens = tokenize(expression);
        return isPostfixTokenOrderValid(tokens);
    }

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

    private static String[] tokenize(String expression) {
        Pattern tokenPattern = Pattern.compile("-?\\d+(\\.\\d+)?|[-+*/()]");
        Matcher matcher = tokenPattern.matcher(expression);

        // Use a list to collect matches
        java.util.List<String> tokens = new java.util.ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        // Convert the list to an array and return
        return tokens.toArray(new String[0]);
    }


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

    private static boolean isNumeric(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
}
