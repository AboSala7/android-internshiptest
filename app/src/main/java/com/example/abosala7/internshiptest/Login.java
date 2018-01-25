package com.example.abosala7.internshiptest;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private Button loginBtn;
    private TextView hiddenCounter;
    private int loginAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intViews();

    }
    private void intViews(){
        email = (EditText)findViewById(R.id.login_emailid);
        password = (EditText)findViewById(R.id.login_password);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        hiddenCounter = (TextView)findViewById(R.id.hiddencounter);
    }
    public void loginBtnFunction(View view){

        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();

        Pattern emailPattern = Pattern.compile(Utils.EMAIL_REGX);
        Matcher emailMatcher = emailPattern.matcher(emailTxt);

        if((emailTxt.equals(Utils.EMAIL))&(passwordTxt.equals(Utils.PASSWORD))){
            loginAttempt = 0 ;
            email.setText(" ");
            password.setText(" ");
            Intent intent = new Intent(Login.this,Home.class);
            startActivity(intent);
        }

        else if (!emailMatcher.find()) {
            loginAttempt++;
            Toast.makeText(getApplicationContext(), "Email is not valid ", Toast.LENGTH_SHORT).show();
                disableLogin();


        }
        else if(!(passwordTxt.length()>5)) {
            loginAttempt++;
            Toast.makeText(getApplicationContext(), "password should be 6 chars minimum", Toast.LENGTH_SHORT).show();
                disableLogin();


        }

        else {
            loginAttempt++;
            Toast.makeText(getApplicationContext(),"Email ID and password are not registered",Toast.LENGTH_SHORT).show();
                disableLogin();


        }
    }
    private void disableLogin() {

        if (loginAttempt == 3) {

            email.setEnabled(false);
            password.setEnabled(false);
            loginBtn.setEnabled(false);
            hiddenCounter.setVisibility(View.VISIBLE);

            new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    hiddenCounter.setText("wait: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    hiddenCounter.setVisibility(View.GONE);
                    loginAttempt = 0;
                }
            }.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    email.setEnabled(true);
                    password.setEnabled(true);
                    loginBtn.setEnabled(true);
                }
            }, 60000);

        }
    }
}
