package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrefixEvaluator extends BaseExpressionEvaluator {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> stack = new Stack<>();
        Pattern tokenPattern = Pattern.compile("-?\\d+(?:\\.\\d+)?|[-+*/]");
        Matcher matcher = tokenPattern.matcher(expression);

        Stack<String> tokens = new Stack<>();
        while (matcher.find()) {
            tokens.push(matcher.group());
        }

        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double val1 = stack.pop();
                double val2 = stack.pop();
                stack.push(applyOp(token, val1, val2));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        return stack.pop();
    }
}
