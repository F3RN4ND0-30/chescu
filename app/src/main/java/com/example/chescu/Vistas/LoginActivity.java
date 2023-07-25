package com.example.chescu.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chescu.Conexion;
import com.example.chescu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    Button btn_logear, btn_register;

    Conexion conexionBD = new Conexion();

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password);
        btn_logear=(Button) findViewById(R.id.btn_entrar);
        btn_register=(Button) findViewById(R.id.btn_registrarse);

        btn_logear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
    public void verificar(){
        try {
            PreparedStatement ps= conexionBD.conexionBD().prepareStatement("select *from Usuario where Correo=? and Pass=? and Estado='true'");
            ps.setString(1,user.getText().toString());
            ps.setString(2,password.getText().toString());
            ResultSet rs = ps.executeQuery();
            iniciarSesion(user.getText().toString(),password.getText().toString());
            while(rs.next()){
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        }catch (SQLException e){
            user.setText("");
            password.setText("");
            Toast.makeText(getApplicationContext(),"USUARIO NO ENCONTRADO",Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"USUARIO NO ENCONTRADO",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}