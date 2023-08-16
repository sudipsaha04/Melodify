package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText LoginPhone,LoginPassword;
    Button LoginButton;
    String Phone,Password;
    String userPassword;

    Dialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoadingBar= new Dialog(this);
        LoginPhone = (EditText) findViewById(R.id.login_phone);
        LoginPassword= (EditText) findViewById(R.id.login_password);
        LoginButton= (Button)findViewById(R.id.login_btn);

        LoadingBar.setTitle("Login Account");
        //LoadingBar.setMessage("Please wait,we are checking your credentials in our database");
        LoadingBar.setCanceledOnTouchOutside(false);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone = LoginPhone.getText().toString();
                Password= LoginPassword.getText().toString();
                LoginAccount(Phone,Password);
            }
        });

    }

    private void LoginAccount(String phone, String password) {

        if(TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this,"Please enter your phone number..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this,"Please enter your password..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.show();

            final DatabaseReference mRef;
            mRef = FirebaseDatabase.getInstance().getReference();

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if(snapshot.child("Users").child(Phone).exists())
                    {
                        userPassword = snapshot.child("Users").child(Phone).child("password").getValue().toString();

                        if(Password.equals(userPassword))
                        {
                            LoadingBar.dismiss();
                            Intent i = new Intent(LoginActivity.this,Upload.class);
                            startActivity(i);
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this,"Please enter valid password..",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this,"User with this number does not exist",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }
}