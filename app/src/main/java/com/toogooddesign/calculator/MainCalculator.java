package com.toogooddesign.calculator;

//App created by Jordan Maurice
//Copyright 10/16/2016 Too Good Design
//This is a calculator application, simply type in your expression and click the equal button
//It will revise your input into reverse polish notation and then calculate the answer.

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Stack;

public class MainCalculator extends Activity {
    Button one, two, three, four, five, six, seven, eight, nine, zero, dec, add, mul, sub, div, cancel, equal, back,mod,answerButton;
    Boolean decAdded;
    EditText display,historyDisplay;
    Double savedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calculator);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zeroe);
        cancel = (Button) findViewById(R.id.cancel);
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        mul = (Button) findViewById(R.id.mul);
        div = (Button) findViewById(R.id.div);
        dec = (Button) findViewById(R.id.dec);
        back = (Button) findViewById(R.id.back);
        equal = (Button) findViewById(R.id.equal);
        mod = (Button) findViewById(R.id.mod);
        display = (EditText) findViewById(R.id.display);
        historyDisplay = (EditText) findViewById(R.id.history);
        answerButton = (Button) findViewById(R.id.answerButton);
        savedAnswer = 0.0;
        decAdded=false;
    }
    static String infixToPostfix(String infix) {
        final String ops = "-+/X^%";
        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);

                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            }
            else if (c == '(') {
                s.push(-2); // -2 stands for '('
            }
            else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        return sb.toString();
    }

    public void press(View arg0){
        Editable userInput = display.getText();
        String input = userInput.toString();
        boolean isDigit;
        switch (arg0.getId()) {
            case R.id.one:
                userInput = userInput.append(one.getText());
                display.setText(userInput);
                break;
            case R.id.two:
                userInput = userInput.append(two.getText());
                display.setText(userInput);
                break;
            case R.id.three:
                userInput = userInput.append(three.getText());
                display.setText(userInput);
                break;
            case R.id.four:
                userInput = userInput.append(four.getText());
                display.setText(userInput);
                break;
            case R.id.five:
                userInput = userInput.append(five.getText());
                display.setText(userInput);
                break;
            case R.id.six:
                userInput = userInput.append(six.getText());
                display.setText(userInput);
                break;
            case R.id.seven:
                userInput = userInput.append(seven.getText());
                display.setText(userInput);
                break;
            case R.id.eight:
                userInput = userInput.append(eight.getText());
                display.setText(userInput);
                break;
            case R.id.nine:
                userInput = userInput.append(nine.getText());
                display.setText(userInput);
                break;
            case R.id.zeroe:
                userInput = userInput.append(zero.getText());
                display.setText(userInput);
                break;
            case R.id.answerButton:
                if(savedAnswer!= 0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(Integer.toString(ans2));
                        display.setText(userInput);
                    } else {
                        userInput = userInput.append(Double.toString(savedAnswer));
                        display.setText(userInput);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "No answer is saved", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                display.setText("");
                historyDisplay.setText("");
                savedAnswer=0.0;
                break;
            case R.id.back:
                String text = display.getText().toString();
                if (text.length() > 0) {
                    String newText = text.substring(0, text.length() - 1); //Creates a substring from 0 to end of text -1
                    display.setText(newText);
                }
                break;
            case R.id.dec:
                if (input.length()>0) {
                    char last = input.charAt(input.length()-1);
                    if(last == '.'){
                        Toast.makeText(getApplicationContext(), "Cannot have multiple decimals in a row", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!decAdded) {
                            decAdded=true;
                            userInput = userInput.append(dec.getText());
                        }
                    }
                }
                else{
                    if(!decAdded) {
                        decAdded=true;
                        userInput = userInput.append(dec.getText());
                    }
                }

                display.setText(userInput);
                break;
            case R.id.add:
                decAdded=false;
                if (input.length() > 0) {
                    char c = input.charAt(input.length() - 1);
                    isDigit = (c >= '0' && c <= '9');
                    if (isDigit) {
                        userInput = userInput.append(" "+ add.getText()+" ");
                        display.setText(userInput);
                    } else {
                        display.setText(userInput);
                        Toast.makeText(getApplicationContext(), "Cannot have multiple operators in a row", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (savedAnswer !=0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(String.valueOf(ans2)+" "+add.getText()+" ");
                        display.setText(userInput);
                    } else {
                        String newAns = Double.toString(savedAnswer);
                        newAns = String.format("%.4f", savedAnswer);
                        userInput = userInput.append(newAns +" "+add.getText()+" ");
                        display.setText(userInput);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot start with an operator", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mul:
                decAdded=false;
                if (input.length() > 0) {
                    char c = input.charAt(input.length() - 1);
                    isDigit = (c >= '0' && c <= '9');
                    if (isDigit) {
                        userInput = userInput.append(" "+mul.getText()+" ");
                        display.setText(userInput);
                    } else {
                        display.setText(userInput);
                        Toast.makeText(getApplicationContext(), "Cannot have multiple operators in a row", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (savedAnswer !=0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(String.valueOf(ans2)+" "+mul.getText()+" ");
                        display.setText(userInput);
                    } else {
                        String newAns = Double.toString(savedAnswer);
                        newAns = String.format("%.4f", savedAnswer);
                        userInput = userInput.append(newAns+" "+mul.getText()+" ");
                        display.setText(userInput);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot start with an operator", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.div:
                decAdded=false;
                if (input.length() > 0) {
                    char c = input.charAt(input.length() - 1);
                    isDigit = (c >= '0' && c <= '9');
                    if (isDigit) {
                        userInput = userInput.append(" "+div.getText()+" ");
                        display.setText(userInput);
                    } else {
                        display.setText(userInput);
                        Toast.makeText(getApplicationContext(), "Cannot have multiple operators in a row", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (savedAnswer !=0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(String.valueOf(ans2)+" "+div.getText()+" ");
                        display.setText(userInput);
                    } else {
                        String newAns = Double.toString(savedAnswer);
                        newAns = String.format("%.4f", savedAnswer);
                        userInput = userInput.append(newAns+" "+div.getText()+" ");
                        display.setText(userInput);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot start with an operator", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sub:
                decAdded=false;
                if (input.length() > 0) {
                    char c = input.charAt(input.length() - 1);
                    isDigit = (c >= '0' && c <= '9');
                    if (isDigit) {
                        userInput = userInput.append(" "+sub.getText()+" ");
                        display.setText(userInput);
                    } else {
                        display.setText(userInput);
                        Toast.makeText(getApplicationContext(), "Cannot have multiple operators in a row", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (savedAnswer !=0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(String.valueOf(ans2)+" "+sub.getText()+" ");
                        display.setText(userInput);
                    } else {
                        String newAns = Double.toString(savedAnswer);
                        newAns = String.format("%.4f", savedAnswer);
                        userInput = userInput.append(newAns+" "+sub.getText()+" ");
                        display.setText(userInput);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot start with an operator", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mod:
                decAdded=false;
                if (input.length() > 0) {
                    char c = input.charAt(input.length() - 1);
                    isDigit = (c >= '0' && c <= '9');
                    if (isDigit) {
                        userInput = userInput.append(" "+mod.getText()+" ");
                        display.setText(userInput);
                    }
                    else {
                        display.setText(userInput);
                        Toast.makeText(getApplicationContext(), "Cannot have multiple operators in a row", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (savedAnswer !=0) {
                    if (savedAnswer % 1 == 0) {
                        int ans2 = savedAnswer.intValue();
                        userInput = userInput.append(String.valueOf(ans2)+" "+mod.getText()+" ");
                        display.setText(userInput);
                    } else {
                        String newAns = Double.toString(savedAnswer);
                        newAns = String.format("%.4f", savedAnswer);
                        userInput = userInput.append(newAns+" "+mod.getText()+" ");
                        display.setText(userInput);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot start with an operator", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.equal:
                decAdded=false;
                input = input+" ";

                if (!input.equals(" ")){

                    String RPNNotation = infixToPostfix(input);
                    String[] inputs = RPNNotation.split(" ");
                    Double answer = evalRPN(inputs);
                    savedAnswer = answer;
                    if (answer % 1 == 0) {
                        int ans = answer.intValue();
                        historyDisplay.setText(input + "= " + String.valueOf(ans));
                        display.setText("");
                    } else {
                        historyDisplay.setText(input + "= " + String.format("%.4f", answer)); //Formats to 4 decimal points accuracy
                        display.setText("");
                    }
                }
                break;
        }
    }

    //This calculates the final answer
    public Double evalRPN(String[] tokens) {
        Double returnValue = 0.0;
        String operators = "+-X/%";

        Stack<String> stack = new Stack<String>();

        for(String t : tokens){
            if(!operators.contains(t)){
                stack.push(t);
            }else{
                double a = Double.valueOf(stack.pop());
                double b = Double.valueOf(stack.pop());
                int index = operators.indexOf(t);
                switch(index){
                    case 0:
                        stack.push(String.valueOf(a+b));
                        break;
                    case 1:
                        stack.push(String.valueOf(b-a));
                        break;
                    case 2:
                        stack.push(String.valueOf(a*b));
                        break;
                    case 3:
                        stack.push(String.valueOf(b/a));
                        break;
                    case 4:
                        stack.push(String.valueOf(b%a));
                        break;
                }
            }
        }
        returnValue = Double.valueOf(stack.pop());
        return returnValue;
    }
}