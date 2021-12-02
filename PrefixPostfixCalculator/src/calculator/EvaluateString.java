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
    
    public static double evaluate(String postfix) {
        // Tokens can have whitespace
        char[] tokens = postfix.toCharArray();
        
        // Stores next char
        char next = ' ';
        
        Stack<Double> operands = new Stack<Double>();
        
        for (int i = 0; i < tokens.length; i++) {
            if (i < tokens.length - 1) {
                next = tokens[i + 1];
            }
            
            // Skips whitespace tokens
            if (tokens[i] == ' ') {
                continue;
            }
            
            // Pushes numbers and a decimal point to operands stack 
            else if (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.') {
                StringBuffer strBuff = new StringBuffer();
                
                while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) {
                    strBuff.append(tokens[i++]);
                }
                
                operands.push(Double.parseDouble(strBuff.toString()));
                
                // Corrects i offset caused by for loop
                i--;
            }
            
            // Pushes numbers, a decimal point, and a negative sign to operands stack 
            else if (tokens[i] == '-' && next != ' ' && i != tokens.length - 1) {
                StringBuffer strBuff = new StringBuffer();
                
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] == '.' || tokens[i] == '-'))) {
                    strBuff.append(tokens[i++]);
                }
                
                operands.push(Double.parseDouble(strBuff.toString()));
                
                // Corrects i offset caused by for loop
                i--;
            }
            
            // Applies the operator to top 2 of operands stack
            else {
                double n1 = operands.pop();
                double n2 = operands.pop();
                
                switch (tokens[i]) {
                    case '^':
                        operands.push(Math.pow(n2, n1));
                        break;
                
                    case '*':
                        operands.push(n2 * n1);
                        break;
                
                    case '/':
                        operands.push(n2 / n1);
                        break;
            
                    case '+':
                        operands.push(n2 + n1);
                        break;
                
                    case '-':
                        operands.push(n2 - n1);
                        break;
                }
            }
        }
        
        // Returns final result
        return operands.pop();
    }
    
}
