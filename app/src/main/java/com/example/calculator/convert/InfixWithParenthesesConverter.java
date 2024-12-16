package com.example.calculator.convert;

// Infix with Parentheses Converter
public class InfixWithParenthesesConverter extends BaseExpressionConverter {

    @Override
    public String convert(String expression) {
        return expression.replaceAll("\\s+", " "); // Clean up spaces
    }
}
