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
public class PostfixString {
    
    public static String postfix(String expression) {
        String result = "";
        
        // Tokens can have whitespace
        char[] tokens = expression.toCharArray();
        
        // Stores true if previous char is an operator
        boolean isPrevOp = false;
        
        // Sets isPrevOP to true if expression begins with a negative sign
        if (tokens[0] == '-') {
            isPrevOp = true;
        }
        
        Stack<Character> operators = new Stack<Character>();
        
        // Returns Invalid Character! if unrecognized char was used  
        for (int i = 0; i < tokens.length; i++) {
            // Skips whitespace tokens
            if (tokens[i] == ' ') {
                continue;
            }
            
            // Adds numbers and a decimal point to result string
            else if (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.') {
                // Adds space before the numbers if previous char is an operator 
                if (isPrevOp == true) {
                    result += " " + tokens[i];
                }
                
                else {
                    result += tokens[i];
                }
                
                isPrevOp = false;
            }
            
            // Adds a negative sign to result string
            else if (tokens[i] == '-' && isPrevOp == true) {
                result += " " + tokens[i];
                isPrevOp = false;
            }
            
            // Pushes opening parenthesis to operators stack
            else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            }
            
            // Adds top of operators stack to result up to opening parenthesis  
            else if (tokens[i] == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    result += " " + operators.pop();
                }
                
                // Removes the remaining opening parenthesis
                operators.pop();
            }
            
            // Pushes EMDAS to operators stack
            else if (tokens[i] == '^' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '+' || tokens[i] == '-') {
                isPrevOp = true;
                
                // Compares the top of operators stack's precedence level to current token
                while (!operators.isEmpty() && precLevel(tokens[i]) <= precLevel(operators.peek())) {
                    // Adds top operator if it has higher or same precedence level  
                    result += " " + operators.pop();
                }
                
                operators.push(tokens[i]);
            }
            
            else {
                return "Invalid Character!";
            }
        }
        
        // Adds remaining operators
        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                return "Invalid Expression!";
            }
            
            result += " " + operators.pop(); 
        }
        
        // Returns final result
        return result;
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
}
