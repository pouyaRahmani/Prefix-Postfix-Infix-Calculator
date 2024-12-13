package com.example.calculator.logic;

public interface ExpressionParser {
    String evaluateInfix(String expression) throws Exception;
    String evaluatePostfix(String expression) throws Exception;
    String evaluatePrefix(String expression) throws Exception;
}
