package com.example.my_java_calc.engine;


import com.example.my_java_calc.engine.parser.*;
import com.example.my_java_calc.engine.evaluator.Evaluator;

import java.util.List;

public class CalculatorEngine {

    public static double calculate(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new RuntimeException("Empty input");
        }

        List<Token> tokens = Tokenizer.tokenize(input);
        List<Token> postfix = InfixToPostfixConverter.convert(tokens);
        return Evaluator.evaluate(postfix);
    }
}
