package com.example.prjgetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_login extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
    EditText email, password;
    Button gotoSignUp, LogInButton;
    DatabaseReference userDB,userChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initilize();


    }

    private void initilize() {
        email =findViewById(R.id.edEmail);
        password = findViewById(R.id.edPass);
        gotoSignUp = findViewById(R.id.tvSignUp);
        LogInButton = findViewById(R.id.tvLogin);

        LogInButton.setOnClickListener(this);
        gotoSignUp.setOnClickListener(this);
        userDB = FirebaseDatabase.getInstance().getReference("user");
    }

    private void login() {

        String userid = email.getText().toString();
        userChild = userDB.child(userid);
        userChild.addValueEventListener(this);

    }
    private void gotohome(){
        Intent intent = new Intent(this,activity_home.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.tvLogin:login();break;
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        if (snapshot.exists()){
            String pass = snapshot.child("password").getValue().toString();
            String Email = snapshot.child("email").getValue().toString();
            if (useremail.equals(Email)){
                if(pass.equals(userpassword)){
                    gotohome();
                }else{
                    Toast.makeText(this, "errorpassword", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "error email", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}