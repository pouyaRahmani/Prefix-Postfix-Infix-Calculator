package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostfixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>();
        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(\\.\\d+)?|[-+*/])\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim();
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double val1 = stack.pop();
                double val2 = stack.pop();
                stack.push(applyOp(token, val2, val1));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        return stack.pop();
    }
}
