package com.example.calculator.logic;

public abstract class Expression {
    public abstract double evaluate(String expression) throws IllegalArgumentException;
}
