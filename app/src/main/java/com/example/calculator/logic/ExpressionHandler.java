package com.example.calculator.logic;

public class ExpressionHandler {

    public static double evaluate(String expression, String format) throws IllegalArgumentException {
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
