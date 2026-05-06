package com.example.my_java_calc.engine.evaluator;

import com.example.my_java_calc.engine.parser.Token;
import com.example.my_java_calc.engine.parser.TokenType;

import java.util.List;
import java.util.Stack;

public class Evaluator {

    public static double evaluate(List<Token> postfixTokens) {
        Stack<Double> stack = new Stack<>();

        for (Token token : postfixTokens) {

            if (token.type == TokenType.NUMBER) {
                stack.push(Double.parseDouble(token.value));
            }

            else if (token.type == TokenType.OPERATOR) {

                if (stack.size() < 2) {
                    throw new RuntimeException("Invalid expression");
                }

                double b = stack.pop();
                double a = stack.pop();

                double result = applyOperator(a, b, token.value);
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new RuntimeException("Invalid expression");
        }

        return stack.pop();
    }

    private static double applyOperator(double a, double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            default:
                throw new RuntimeException("Unknown operator: " + op);
        }
    }
}
