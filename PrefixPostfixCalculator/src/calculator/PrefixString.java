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
public class PrefixString {
    
    public static String prefix(String expression) {
        // Tokens can have whitespace
        char[] tokens = expression.toCharArray();
        
        // Stores true if previous char is an operator
        boolean isPrevOp = false;
        
        // Sets isPrevOP to true if expression begins with a negative sign
        if (tokens[0] == '-') {
            isPrevOp = true;
        }
        
        Stack<String> operands = new Stack<String>();
        Stack<Character> operators = new Stack<Character>();
        
        // Returns Invalid Character! if unrecognized char was used  
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
                
                operands.push(strBuff.toString());
                
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
                
                operands.push(strBuff.toString());
                
                // Corrects i offset caused by for loop
                i--;
            }
            
            // Pushes opening parenthesis to operators stack
            else if (tokens[i] == '(') {
                operators.push(tokens[i]);
                isPrevOp = true;
            }
            
            // Concatenates current expression inside parentheses
            else if (tokens[i] == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    operands.push(concatenateExp(operators.pop(), operands.pop(), operands.pop()));
                }
                
                // Removes the remaining opening parenthesis
                operators.pop();
            }
            
            // Pushes EMDAS to operators stack
            else if (tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '+' || tokens[i] == '-') {
                isPrevOp = true;
                
                // Compares the top of operators stack's precedence level to current token
                while (!operators.isEmpty() && precLevel(tokens[i]) <= precLevel(operators.peek())) {
                    // Concatenates top operator if it has higher or same precedence level  
                    operands.push(concatenateExp(operators.pop(), operands.pop(), operands.pop()));
                }
                
                operators.push(tokens[i]);
            }
            
            else {
                return "Invalid Character!";
            }
        }
        
        // Concatenates remaining expressions
        while (!operators.isEmpty()) {
            operands.push(concatenateExp(operators.pop(), operands.pop(), operands.pop())); 
        }
        
        // Returns final result
        return operands.pop();
    }
    
    // Returns operator's precedence level
    private static int precLevel(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
                
            case '*':
            case '/':
                return 2;
                
            case '^':
                return 3;
        }
        
        return -1;
    }
    
    // Concatenates top of operators stack to top 2 of operands stack
    private static String concatenateExp(char op, String s1, String s2) {
        return op + " " + s2 + " " + s1 + " ";
    }
    
}
