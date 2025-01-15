package com.example.calculator.logic;

// Interface for evaluating expressions
public interface Expression {
    double evaluate(String expression) throws IllegalArgumentException;
}
