package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(?:\\.\\d+)?|[-+*/()]|\\S)\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim();
            if (isNumeric(token)) {
                values.push(Double.parseDouble(token));
            } else if (token.equals("(")) {
                operators.push('(');
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double secondOperand = values.pop();
                    double firstOperand = values.pop();
                    values.push(applyOp(String.valueOf(operators.pop()), firstOperand, secondOperand));
                }
                operators.pop();
            } else if (isOperator(token)) {
                char op = token.charAt(0);
                while (!operators.isEmpty() && precedence(op) <= precedence(operators.peek())) {
                    double secondOperand = values.pop();
                    double firstOperand = values.pop();
                    values.push(applyOp(String.valueOf(operators.pop()), firstOperand, secondOperand));
                }
                operators.push(op);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            double secondOperand = values.pop();
            double firstOperand = values.pop();
            values.push(applyOp(String.valueOf(operators.pop()), firstOperand, secondOperand));
        }

        return values.pop();
    }

    private int precedence(char operator) {
        return (operator == '+' || operator == '-') ? 1 : (operator == '*' || operator == '/') ? 2 : 0;
    }
}
