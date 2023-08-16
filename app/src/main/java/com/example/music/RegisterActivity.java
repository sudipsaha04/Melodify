package com.example.music;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText RegisterPhone,RegisterPassword,RegisterName;
    String Phone,Password,Name;
    Button RegisterButton;
    Dialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterPhone = (EditText) findViewById(R.id.register_phone);
        RegisterPassword = (EditText) findViewById(R.id.register_password);
        RegisterName = (EditText) findViewById(R.id.register_name);

        RegisterButton = (Button)findViewById(R.id.register_btn);

        LoadingBar=new Dialog(this);

        LoadingBar.setTitle("Creating Account...");
        //LoadingBar.setMessage("Please Wait, while we are checking our credentials");
        LoadingBar.setCanceledOnTouchOutside(false);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone= RegisterPhone.getText().toString();
                Password = RegisterPassword.getText().toString();
                Name = RegisterName.getText().toString();

                CreateNewAccount(Phone,Password,Name);

            }
        });
    }

    private void CreateNewAccount(String phone, String password, String name) {

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please enter your phone number..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter your password..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please enter your name..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.show();

            final DatabaseReference mRef;
            mRef = FirebaseDatabase.getInstance().getReference();

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(!(snapshot.child("Users").child(phone).exists()))
                    {
                        HashMap<String,Object>userdata = new HashMap<>();
                        userdata.put("phone",phone);
                        userdata.put("password",password);
                        userdata.put("name",name);

                        mRef.child("Users").child(phone).updateChildren(userdata)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                           LoadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this,"Registration Succesful" ,Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(i);
                                        }

                                        else
                                        {
                                            LoadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this,"Please try again after sometimes..." ,Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this,"User with this number already exist" ,Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }
}