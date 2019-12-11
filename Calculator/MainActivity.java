package com.janfranco.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText, editText2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
    }

    public int getNum1(){
        return Integer.parseInt(editText.getText().toString());
    }

    public int getNum2(){
        return Integer.parseInt(editText2.getText().toString());
    }

    public boolean isSafe(){
        if(editText.getText().toString().matches("") || editText2.getText().toString().matches("")){
            return false;
        }
        else
            return true;
    }

    public void add(View view){
        if(isSafe()) {
            int num1 = getNum1();
            int num2 = getNum2();
            textView.setText("Result= " + (num1 + num2));
        }
    }

    public void sub(View view){
        if(isSafe()) {
            int num1 = getNum1();
            int num2 = getNum2();
            textView.setText("Result= " + (num1 - num2));
        }
    }

    public void multiply(View view){
        if(isSafe()) {
            int num1 = getNum1();
            int num2 = getNum2();
            textView.setText("Result= " + (num1 * num2));
        }
    }

    public void divide(View view){
        if(isSafe()) {
            int num1 = getNum1();
            int num2 = getNum2();
            textView.setText("Result= " + (num1 / num2));
        }
    }

}
