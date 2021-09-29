package sample;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Calculate {
    public static void plus_minus(ActionEvent event, String sign){
        String text = ((Button)event.getSource()).getText();
        Test1.str1.append(text);
        Test1.myLabel.setText(Test1.str1.toString());
        if(Test1.str2.length()!=0){
            Test1.a.add(Float.valueOf(Test1.str2.toString()).floatValue());
        }
        Test1.a.add(sign);
        Test1.str2.delete(0, Test1.str2.length());
        Test1.length = Test1.str1.length();
    }
    public void plus(ActionEvent event) {
        plus_minus(event, "+");
    }
    public void minus(ActionEvent event) {
        plus_minus(event, "-");
    }
}
