package com.example.calculator.logic;

public interface Expression {
    double evaluate(String expression) throws IllegalArgumentException;
}
