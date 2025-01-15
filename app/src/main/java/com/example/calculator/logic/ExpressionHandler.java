package com.example.calculator.logic;

// Handles the evaluation of expressions in different formats
public class ExpressionHandler {

    // Evaluates the given expression in the specified format (infix, prefix, or postfix)
    public static double evaluate(String expression, String format) throws IllegalArgumentException {
        boolean isValid;

        // Validate the expression format
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

        // Create the appropriate evaluator based on the format
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

        // Evaluate the expression
        return evaluator.evaluate(expression);
    }
}