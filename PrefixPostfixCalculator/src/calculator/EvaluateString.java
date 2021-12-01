/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator;

import java.util.Stack;

/**
 *
 * @author User
 */
public class EvaluateString {
    
    public static double evaluate(String expression) {
        // Tokens can have whitespace
        char[] tokens = expression.toCharArray();
        
        // Stores true if previous char is an operator
        boolean isPrevOp = false;
        
        // Sets isPrevOP to true if expression begins with a negative sign
        if (tokens[0] == '-') {
            isPrevOp = true;
        }
        
        Stack<Double> operands = new Stack<Double>();
        Stack<Character> operators = new Stack<Character>();
        
        for (int i = 0; i < tokens.length; i++) {
            // Skips whitespace tokens
            if (tokens[i] == ' ') {
                continue;
            }
            
            // Pushes numbers and a decimal point to operands stack 
            else if (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.') {
                StringBuffer strBuff = new StringBuffer();
                isPrevOp = false;
                
                while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) {
                    strBuff.append(tokens[i++]);
                }
                
                operands.push(Double.parseDouble(strBuff.toString()));
                
                // Corrects i offset caused by for loop
                i--;
            }
            
            // Pushes numbers, a decimal point, and a negative sign to operands stack 
            else if (tokens[i] == '-' && isPrevOp == true) {
                StringBuffer strBuff = new StringBuffer();
                isPrevOp = false;
                
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] == '.' || tokens[i] == '-'))) {
                    strBuff.append(tokens[i++]);
                }
                
                operands.push(Double.parseDouble(strBuff.toString()));
                
                // Corrects i offset caused by for loop
                i--;
            }
            
            // Pushes opening parenthesis to operators stack
            else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            }
            
            // Evaluates current expression inside parentheses 
            else if (tokens[i] == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    operands.push(evaluateExp(operators.pop(), operands.pop(), operands.pop()));
                }
                
                // Removes the remaining opening parenthesis
                operators.pop();
            }
            
            // Pushes EMDAS to operators stack
            else if (tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '+' || tokens[i] == '-') {
                isPrevOp = true;
                
                // Checks the top of operators stack's precedence to current token
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) {
                    // Evaluates top operator if it has higher or same precedence  
                    operands.push(evaluateExp(operators.pop(), operands.pop(), operands.pop()));
                }
                
                operators.push(tokens[i]);
            }
        }
        
        // Evaluates remaining expressions
        while (!operators.isEmpty()) {
            operands.push(evaluateExp(operators.pop(), operands.pop(), operands.pop()));
        }
        
        // Returns final result
        return operands.pop();
    }
    
    // Returns true if op2 has higher or same precedence as op1
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        
        else if (op1 == '^' && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-')) {
            return false;
        }
        
        else if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        
        else {
            return true;
        }
    }
    
    // Applies top of operators stack to top 2 of operands stack
    private static double evaluateExp(char op, double n1, double n2) {
        switch (op) {
            case '^':
                return Math.pow(n2, n1);
                
            case '*':
                return n2 * n1;
                
            case '/':
                return n2 / n1;
            
            case '+':
                return n2 + n1;
                
            case '-':
                return n2 - n1;
        }
        
        return 0;
    }
    
}
