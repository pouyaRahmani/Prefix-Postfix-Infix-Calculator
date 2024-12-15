package com.example.calculator.logic;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfixEvaluator extends Expression {

    @Override
    public double evaluate(String expression) throws IllegalArgumentException {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        Pattern tokenPattern = Pattern.compile("\\s*(-?\\d+(?:\\.\\d+)?|[-+*/()]|\\S)\\s*");
        Matcher matcher = tokenPattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim();

            if (token.matches("-?\\d+(?:\\.\\d+)?")) {
                values.push(Double.parseDouble(token));
            } else if (token.equals("(")) {
                ops.push('(');
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    double secondOperand = values.pop();
                    double firstOperand = values.pop();
                    values.push(applyOp(ops.pop(), secondOperand, firstOperand));
                }
                ops.pop();
            } else if (token.matches("[-+*/]")) {
                char op = token.charAt(0);
                while (!ops.isEmpty() && precedence(op) <= precedence(ops.peek())) {
                    double secondOperand = values.pop();
                    double firstOperand = values.pop();
                    values.push(applyOp(ops.pop(), secondOperand, firstOperand));
                }
                ops.push(op);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!ops.isEmpty()) {
            double secondOperand = values.pop();
            double firstOperand = values.pop();
            values.push(applyOp(ops.pop(), secondOperand, firstOperand));
        }

        return values.pop();
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
                return a / b;
            default: throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}
