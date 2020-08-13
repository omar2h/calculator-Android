package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

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
                solved = false;
            }
        }
        switch (in) {
            case "=":
                if(bInput.length()>0) {
                   if (sInput.indexOf("=")==-1) {
                        tempOp+=bInput;
                        sInput+=bInput;
                        solve();
                        sInput += "=";
                        sScreen.setText(sInput);
                        bScreen.setText(bInput);
                        solved=false;
                    }
                }
                break;
            case ".":
                if(bInput.length()>0) {
                    if (bInput.indexOf(".")==-1) {
                        bInput += in;
                        bScreen.setText(bInput);
                    }
                }
                break;
            case "+/-":
                if(bInput.length()>0){
                    String c = bInput.substring(0,1);
                    if(c.equals("-")){
                        bInput = bInput.substring(1);
                    }
                    else {
                        bInput = "-" + bInput;
                    }
                    bScreen.setText(bInput);
                    if(sInput.indexOf("=")!=-1){
                        if(bInput.indexOf(".")!=-1){
                            tempOp = bInput;
                        }
                        else{
                            tempOp = bInput + ".0";
                        }
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
                        if(len2 > 0) {
                            if (!sInput.substring(len2 - 1).equals("=")) {
                                String s = bInput.substring(0, len1 - 1);
                                bInput = s;
                                s = sInput.substring(0, len2 - 1);
                                sInput = s;
                                s = tempOp.substring(0, len3 - 1);
                                tempOp = s;
                                bScreen.setText(bInput);
                            }
                        }
                        else{
                            String s = bInput.substring(0, len1 - 1);
                            bInput = s;
                            bScreen.setText(bInput);
                        }
                        if(bInput.equals("")){
                            bScreen.setText(R.string.defaultValue);
                        }
                    }
                }
                break;
            case "C":
                bInput = "";
                sInput = "";
                tempOp = "";
                bScreen.setText(R.string.defaultValue);
                sScreen.setText("");
                break;
            default:
                if (sInput == null) {
                    sInput = "";
                    bScreen.setText(sInput);
                }
                if ((in.equals("+") || in.equals("–") || in.equals("×") || in.equals("÷"))){
                    if (bInput.equals("Infinity")) {
                        sInput = "";
                        bInput = "";
                        tempOp = "";
                        sScreen.setText(sInput);
                    }
                    if(bInput.length()>0) {
                        int len = sInput.length() - 1;
                       // if (!lastCharOp()) {
                        if(!solved) {
                            if(bInput.indexOf("E")!=-1){
                                sInput +=bInput;
                            }
                            else {
                                BigDecimal bd = new BigDecimal(bInput);
                                String t;
                                t = (bd.stripTrailingZeros().toPlainString());
                                sInput += t;
                            }
                        }
                        if(sInput.indexOf("=")!=-1){
                            if(bInput.indexOf("E")!=-1){
                                sInput = bInput;
                            }
                            else {
                                BigDecimal bd = new BigDecimal(bInput);
                                String t;
                                t = (bd.stripTrailingZeros().toPlainString());
                                sInput = t;
                            }
                        }

                        if(tempOp.indexOf(".")==-1) {
                            tempOp += bInput;
                            tempOp += in;
                        }
                        else if (tempOp.substring(tempOp.length()-1).equals("+") || tempOp.substring(tempOp.length()-1).equals("–") || tempOp.substring(tempOp.length()-1).equals("÷") || tempOp.substring(tempOp.length()-1).equals("×"))
                        {
                            tempOp += bInput;
                            tempOp += in;
                        }
                        else{
                            tempOp += in;
                            if(!sInput.equals(bInput))
                                tempOp += bInput;
                        }
                        sInput += in;

                        solve();
                        sScreen.setText(sInput);
                        operator = true;
                        //}
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
                    if(in.equals("0") && bInput.length()>0 && Double.parseDouble(bInput)==0);
                    else {
                        if(bInput.length()>0 && Double.parseDouble(bInput)==0 ){
                            bInput = in;
                            bScreen.setText(bInput);
                        }
                        else {
                            if(bInput.length()<10) {
                                bInput += in;
                                bScreen.setText(bInput);
                            }
                        }
                    }
                }

        }

    }

    public void solve(){
        if(tempOp.split("×").length==2){
            String c ="";
            if(tempOp.substring(tempOp.length()-1).equals("+") || tempOp.substring(tempOp.length()-1).equals("–") || tempOp.substring(tempOp.length()-1).equals("÷") || tempOp.substring(tempOp.length()-1).equals("×")){
                c = tempOp.substring(tempOp.length()-1);
                tempOp = tempOp.substring(0,tempOp.length()-1);
            }
            solved = true;
            String numbers[]=tempOp.split("×");
            try{
                double mul=Double.parseDouble(numbers[0])*Double.parseDouble(numbers[1]);
                bInput=mul+"";
                tempOp=bInput+c;
            }
            catch (Exception e){
                //Display error
            }

        }
        else if(tempOp.split("÷").length==2){
            String c ="";
            if(tempOp.substring(tempOp.length()-1).equals("+") || tempOp.substring(tempOp.length()-1).equals("–") || tempOp.substring(tempOp.length()-1).equals("÷") || tempOp.substring(tempOp.length()-1).equals("×")){
                c = tempOp.substring(tempOp.length()-1);
                tempOp = tempOp.substring(0,tempOp.length()-1);

            }
            solved=true;
            String numbers[]=tempOp.split("÷");
            try{
                double div=Double.parseDouble(numbers[0])/Double.parseDouble(numbers[1]);
                bInput=div+"";
                tempOp=bInput+c;
            }
            catch (Exception e){
                //Display error
            }

        }
        else if(tempOp.split("\\+").length==2){
            solved=true;
            String c ="";
            if(tempOp.substring(tempOp.length()-1).equals("+") || tempOp.substring(tempOp.length()-1).equals("–") || tempOp.substring(tempOp.length()-1).equals("÷") || tempOp.substring(tempOp.length()-1).equals("×")){
                c = tempOp.substring(tempOp.length()-1);
                tempOp = tempOp.substring(0,tempOp.length()-1);

            }
            String numbers[]=tempOp.split("\\+");
            try{
                double sum=Double.parseDouble(numbers[0])+Double.parseDouble(numbers[1]);
                bInput=sum+"";
                tempOp=bInput+c;
            }
            catch (Exception e){
                //Display error
            }
        }
        else if(tempOp.split("\\–").length>1){
            solved=true;
            String c ="";
            if(tempOp.substring(tempOp.length()-1).equals("+") || tempOp.substring(tempOp.length()-1).equals("–") || tempOp.substring(tempOp.length()-1).equals("÷") || tempOp.substring(tempOp.length()-1).equals("×")){
                c = tempOp.substring(tempOp.length()-1);
                tempOp = tempOp.substring(0,tempOp.length()-1);

            }
            String numbers[]=tempOp.split("\\–");

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
                tempOp=bInput+c;
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
        if(len > 0)
            if(sInput.substring(len).equals("+") || sInput.substring(len).equals("–") || sInput.substring(len).equals("×") || sInput.substring(len).equals("÷") || sInput.substring(len).equals("=") || sInput.substring(len).equals("."))
                return true;
            else
                return false;
            return false;
    }

}