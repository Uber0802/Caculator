package sample;

import java.lang.StringBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.*;

import static java.lang.Math.round;

public class Controller {

    @FXML
    private Button myButtonC;
    @FXML
    private Label myLabel;

    ArrayList a = new ArrayList();

    StringBuilder str1 = new StringBuilder();
    StringBuilder str2 = new StringBuilder();
    int status = 0,va_i=-1,oa_i=-1,length=0;
    float ans;

    public void get_number(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        str2.append(text);
        myLabel.setText(str1.toString());
    }

    public void initialize(ActionEvent event){

        str1.delete(0,str1.length());
        str2.delete(0,str2.length());
        a.clear();
        myLabel.setText("0");
    }


    public void plus(ActionEvent event){

        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        myLabel.setText(str1.toString());
        if(str2.length()!=0){
            a.add(Float.valueOf(str2.toString()).floatValue());
        }
        a.add("+");
        str2.delete(0,str2.length());
        length = str1.length();
    }
    public void minus(ActionEvent event){

        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        myLabel.setText(str1.toString());
        if(str2.length()!=0){
            a.add(Float.valueOf(str2.toString()).floatValue());
        }
        a.add("-");
        str2.delete(0,str2.length());
        length = str1.length();
    }
    public void multiply(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        myLabel.setText(str1.toString());
        if(str2.length()!=0){
            a.add(Float.valueOf(str2.toString()).floatValue());
        }
        a.add("*");
        str2.delete(0,str2.length());
        length = str1.length();
    }
    public void division(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        myLabel.setText(str1.toString());
        if(str2.length()!=0){
            a.add(Float.valueOf(str2.toString()).floatValue());
        }
        a.add("/");
        str2.delete(0,str2.length());
        length = str1.length();
    }
    public void percent(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        if(str2.length()==1){
            str2.insert(0,"0");
            str2.insert(0,"0");
        }
        str2.insert(str2.length()-2,".");
        myLabel.setText(str1.toString());
        length = str1.length();
    }
    public void quote(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        if(status==0) {
            str1.append("(");
            a.add("(");
            status = 1;
        }
        else{
            str1.append(")");
            a.add(Float.valueOf(str2.toString()).floatValue());
            a.add(")");
            str2.delete(0,str2.length());
            status = 0;
        }
        myLabel.setText(str1.toString());
        length = str1.length();
    }
    public void dot(ActionEvent event){
        String text = ((Button)event.getSource()).getText();
        str1.append(text);
        str2.append(text);
        myLabel.setText(str1.toString());
    }
    public void posneg(ActionEvent event){

        String text = ((Button)event.getSource()).getText();
        str1.insert(length,"[-");
        str2.insert(0,"-");
        myLabel.setText(str1.toString());
        length = str1.length();
    }
    public void equal(ActionEvent event){

        if(str2.isEmpty()){
        }
        else{
            a.add(Float.valueOf(str2.toString()).floatValue());
        }

        str2.delete(0,str2.length());

        float ans = calculate(0);
        if(ans%1==0){
            int int_ans = round(ans);
            myLabel.setText(String.valueOf(int_ans));
        }
        else myLabel.setText(String.valueOf(ans));

        str1.delete(0,str1.length());
        str2.delete(0,str2.length());
        a.clear();

    }


    public float calculate(int cur){
        boolean immediate = false, quote_immediate=false, quote_finish=false;
        Stack<Float> vs = new Stack();
        Stack<Float> vse = new Stack();
        Stack os = new Stack();
        Stack ose = new Stack();
        for(int i=cur;i<a.size();i++){
            //遇到括號就遞迴
            if(quote_immediate==true){ //當遇到括號
                vs.push(calculate(i));
                while(a.get(i).equals(")")==false) {
                    i++;
                }
                quote_immediate=false;
                quote_finish=true;
            }
            //乘除要先算
            if(immediate){
                if(a.get(i).equals("(")){
                    quote_immediate = true;
                    continue;
                }
                if(quote_finish==false){
                    vs.push(Float.valueOf(a.get(i).toString()));
                }
                float num1 = vs.pop();
                float num2 = vs.pop();
                Object operator = os.pop();

                if(operator=="*") vs.push(num2*num1);
                else if(operator=="/") vs.push(num2/num1);
                immediate = false;
            }
            else if(a.get(i)=="+" || a.get(i)=="-"){
                os.push(a.get(i));
            }
            else if(a.get(i)=="*" ||a.get(i)=="/" ){
                os.push(a.get(i));
                immediate = true;
            }
            else if(a.get(i)==")"){
                if(quote_finish==false) break;
            }
            else if(a.get(i)=="("){
                quote_immediate = true;
            }
            else {
                vs.push(Float.valueOf(a.get(i).toString()));
            }
            quote_finish=false;
        }
        while(!os.empty()){

            Object obj1 = os.pop();
            ose.push(obj1);
        }
        while(!vs.empty()){
            float num1 = vs.pop();
            vse.push(num1);
        }
        while(!ose.empty()){
            float num1 = vse.pop();
            float num2 = vse.pop();
            Object operator = ose.pop();
            System.out.println(num1);
            System.out.println(num2);
            System.out.println(operator);

            if(operator=="+") vse.push(num1+num2);
            else if(operator=="-") vse.push(num1-num2);
            else if(operator=="*") vse.push(num1*num2);
            else if(operator=="/") vse.push(num1/num2);
        }
        float ans = vse.pop();
        return ans;
    }
}
