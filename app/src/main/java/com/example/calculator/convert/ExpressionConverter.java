package com.example.calculator.convert;

// ExpressionConverter Interface
public interface ExpressionConverter {
    // Converts the input expression into a specific format (e.g., Infix to Postfix)
    String convert(String expression) throws IllegalArgumentException;
}
