package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    TextView bScreen, sScreen;
    String bInput="",sInput="", tempOp="";
    boolean operator = false, solved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bScreen = findViewById(R.id.inScreen);
        sScreen = findViewById(R.id.outScreen);

        btn0 = findViewById(R.id.zero);
        btn1 = findViewById(R.id.one);
        btn2 = findViewById(R.id.two);
        btn3 = findViewById(R.id.three);
        btn4 = findViewById(R.id.four);
        btn5 = findViewById(R.id.five);
        btn6 = findViewById(R.id.six);
        btn7 = findViewById(R.id.seven);
        btn8 = findViewById(R.id.eight);
        btn9 = findViewById(R.id.nine);
    }
    public void buttonClick(View view) {
        Button button = (Button) view;
        String in = button.getText().toString();

        if(operator){
            if(!bInput.equals("Infinity")) {
                bInput = "";
                operator = false;
            }
        }
        switch (in) {
            case "=":
                if(!lastCharOp()) {
                    solve();
                    sInput += "=";
                    sScreen.setText(sInput);
                    bScreen.setText(bInput);
                }
                break;
            case ".":
                if(bInput.length()>0) {
                    if (!lastCharOp()) {
                        bInput += in;
                        sInput += in;
                        tempOp += in;
                        bScreen.setText(bInput);
                    }
                }
                break;

            case "⌫":
                if(bInput.equals("Infinity")){}
                else {
                    int len1 = bInput.length();
                    int len2 = sInput.length();
                    int len3 = tempOp.length();
                    if (len1 > 0) {
                        String s = bInput.substring(0, len1 - 1);
                        bInput = s;
                        s = sInput.substring(0, len2 - 1);
                        sInput = s;
                        s = tempOp.substring(0, len3 - 1);
                        tempOp = s;
                        bScreen.setText(bInput);
                    }
                }
                break;
            default:
                if (sInput == null) {
                    sInput = "";
                    bScreen.setText(sInput);
                }
                if ((in.equals("+") || in.equals("-") || in.equals("×") || in.equals("÷"))){
                    int len = sInput.length()-1;
                    if(!lastCharOp()){
                        solve();
                        tempOp += in;
                        sInput += in;
                        sScreen.setText(sInput);
                        operator = true;
                    }
                }
                else {
                    int len = sInput.length()-1;
                    if(len>0) {
                        if (sInput.substring(len).equals("=")) {
                            sInput = "";
                            bInput = "";
                            tempOp = "";
                            sScreen.setText(sInput);
                        }
                    }
                    if(bInput.equals("Infinity")){
                        sInput = "";
                        bInput = "";
                        tempOp = "";
                        sScreen.setText(sInput);
                        operator = false;
                    }
                    bInput+=in;
                    tempOp+=in;
                    sInput+=in;
                    bScreen.setText(bInput);
                }



        }

    }

    public void solve(){
        if(tempOp.split("×").length==2){
            solved = true;
            String numbers[]=tempOp.split("×");
            try{
                double mul=Double.parseDouble(numbers[0])*Double.parseDouble(numbers[1]);
                bInput=mul+"";
                tempOp=bInput;
            }
            catch (Exception e){
                //Display error
            }

        }
        else if(tempOp.split("÷").length==2){
            solved=true;
            String numbers[]=tempOp.split("÷");
            try{
                double div=Double.parseDouble(numbers[0])/Double.parseDouble(numbers[1]);
                bInput=div+"";
                tempOp=bInput;
            }
            catch (Exception e){
                //Display error
            }

        }
        else if(tempOp.split("\\+").length==2){
            solved=true;
            String numbers[]=tempOp.split("\\+");
            try{
                double sum=Double.parseDouble(numbers[0])+Double.parseDouble(numbers[1]);
                bInput=sum+"";
                tempOp=bInput;
            }
            catch (Exception e){
                //Display error
            }
        }
        else if(tempOp.split("\\-").length>1){
            solved=true;
            String numbers[]=tempOp.split("\\-");
            if(numbers[0]=="" && numbers.length==2){
                numbers[0]=0+"";
            }
            try{
                double sub=0;
                if(numbers.length==2) {
                    sub = Double.parseDouble(numbers[0]) - Double.parseDouble(numbers[1]);
                }
                else if(numbers.length==3){
                    sub = -Double.parseDouble(numbers[1]) - Double.parseDouble(numbers[2]);
                }
                bInput=sub+"";
                tempOp=bInput;
            }
            catch (Exception e){
                //Display error
            }
        }
        String n[]=bInput.split("\\.");
        if(n.length>1){
            if(n[1].equals("0")){
                bInput=n[0];
            }
        }
        bScreen.setText(bInput);
    }

    public boolean lastCharOp(){
        int len = sInput.length()-1;
        if(sInput.substring(len).equals("+") || sInput.substring(len).equals("-") || sInput.substring(len).equals("×") || sInput.substring(len).equals("÷") || sInput.substring(len).equals("=") || sInput.substring(len).equals("."))
            return true;
        else
            return false;
    }

}