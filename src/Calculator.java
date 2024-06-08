
import java.util.Stack;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Moein Zanjirian Zadeh
 * #Email moeinz9322@gmail.com
 * @moeinz9322
 */
public class Calculator {
    private static Stack<String> stack;
    /**
     * Constructor
     */
    public Calculator(){
        this.stack = new Stack<String>();
    }
    
    /**
     * A mathematical expression takes input and returns the final answer
     * @param input
     * @return The answer to the mathematical expression
     */
    public String answerEvalution(String input){
        String[] postfix = infixToPostfix(input);
        stack = new Stack();
        for (int i = 0; i < postfix.length; i++) {
            if(postfix[i] == null)
                break;
            
            if(isDigit(postfix[i])){
                stack.push(postfix[i]);
            }
            else{
                double dig2 = Double.valueOf(stack.pop());
                double dig1 = Double.valueOf(stack.pop());
                stack.push(calculate(dig1, dig2, postfix[i]));
            }
            
        }
        return stack.pop();
    }
    
    /**
     * It takes two input numbers and an operator and The answer of the mathematical operation between two numbers is returned
     * @param dig1
     * @param dig2
     * @param operator
     * @return answer
     */
    private String calculate(double dig1, double dig2, String operator){
        switch (operator) {
            case "^":
                return String.valueOf(Math.pow(dig1, dig2));
            case "*":
                return String.valueOf(dig1*dig2);
            case "/":
                return String.valueOf(dig1/dig2);    
            case "+":
                return String.valueOf(dig1+dig2);
            case "-":
                return String.valueOf(dig1-dig2);    
            default:
                return null;
        }
    }
    
    /**
     * To convert an infix string to a postfix array
     * @param input
     * @return a postfix array
     */
    public String[] infixToPostfix(String input){
        stack = new Stack();
        String[] postfix = new String[input.length()];
        int postfixIndex = 0;
        for(int i=0;i<input.length();i++){
            postfix[postfixIndex] = "";
            String character = String.valueOf(input.charAt(i));
            if ((i==0 && (character.equals("+")||character.equals("-"))) || isDigit(character)){
                String number = "";
                if(character.equals("+")||character.equals("-")){
                    number+=character;
                    i++;
                    if(input.length()==i)
                        break;
                    character = String.valueOf(input.charAt(i));
                }
                
                while(character.equals(".") || isDigit(character)){
                    number+=character;
                    i++;
                    if(input.length()==i)
                        break;
                    character = String.valueOf(input.charAt(i));
                }
                postfix[postfixIndex] = number;
                
                i--;
                postfixIndex++;
            }
            else if (character.equals(")")){
                while(!stack.empty() && !stack.peek().equals("(")){
                    postfix[postfixIndex] = stack.pop();
                    postfixIndex++;
                }
                if(stack.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error => (");
                    System.out.println("Error => (");
                    return null;
                }
                stack.pop();
            }else{
                while(!stack.empty() &&
                        !isDigit(stack.peek()) &&
                        !stack.peek().equals("(") &&
                        priority(stack.peek())>priority(character)){
                    postfix[postfixIndex] = stack.pop();
                    postfixIndex++;
                }
                stack.push(character);
            }
            
        }
        while(!stack.empty()){
            postfix[postfixIndex] = stack.pop();
            postfixIndex++;
        }
        return postfix;
    }
    
    /**
     * Specifies the priority of operators, operators with higher priority returns a larger number
     * @param operator
     * @return 
     */
    private int priority(String operator){
        switch (operator) {
            case "^":
                return 4;
            case "*":
                return 3;
            case "/":
                return 3;
            case "+":
                return 2;
            case "-":
                return 2; 
            case "(":
                return 5;     
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * If the input is a number, it returns true, otherwise it returns false
     * @param str
     * @return 
     */
    private boolean isDigit(String str){
        try{
            Double.valueOf(str);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
