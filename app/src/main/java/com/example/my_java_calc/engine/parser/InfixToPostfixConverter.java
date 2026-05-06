package com.example.my_java_calc.engine.parser;

import java.util.*;

public class InfixToPostfixConverter {

    public static List<Token> convert(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Stack<Token> stack = new Stack<>();

        for (Token token : tokens) {

            switch (token.type) {

                case NUMBER:
                    output.add(token);
                    break;

                case OPERATOR:
                    while (!stack.isEmpty() &&
                            stack.peek().type == TokenType.OPERATOR &&
                            precedence(stack.peek()) >= precedence(token)) {

                        output.add(stack.pop());
                    }
                    stack.push(token);
                    break;

                case LEFT_PAREN:
                    stack.push(token);
                    break;

                case RIGHT_PAREN:
                    while (!stack.isEmpty() &&
                            stack.peek().type != TokenType.LEFT_PAREN) {
                        output.add(stack.pop());
                    }

                    if (!stack.isEmpty() && stack.peek().type == TokenType.LEFT_PAREN) {
                        stack.pop(); // remove '('
                    } else {
                        throw new RuntimeException("Mismatched parentheses");
                    }
                    break;
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            if (stack.peek().type == TokenType.LEFT_PAREN) {
                throw new RuntimeException("Mismatched parentheses");
            }
            output.add(stack.pop());
        }

        return output;
    }

    private static int precedence(Token token) {
        switch (token.value) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}
