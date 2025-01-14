package com.example.calculator.logic;

public class ExpressionHandler {

    public static double evaluate(String expression, String format) throws IllegalArgumentException {
        boolean isValid;

        switch (format.toLowerCase()) {
            case "infix":
                isValid = InputValidator.isValidInfix(expression);
                break;
            case "prefix":
                isValid = InputValidator.isValidPrefix(expression);
                break;
            case "postfix":
                isValid = InputValidator.isValidPostfix(expression);
                break;
            default:
                throw new IllegalArgumentException("Invalid format: " + format);
        }

        if (!isValid) {
            throw new IllegalArgumentException("Invalid expression format for " + format);
        }

        Expression evaluator;

        switch (format.toLowerCase()) {
            case "infix":
                evaluator = new InfixEvaluator();
                break;
            case "prefix":
                evaluator = new PrefixEvaluator();
                break;
            case "postfix":
                evaluator = new PostfixEvaluator();
                break;
            default:
                throw new IllegalArgumentException("Invalid format: " + format);
        }

        return evaluator.evaluate(expression);
    }
}
