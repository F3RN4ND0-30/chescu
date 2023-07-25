package com.example.chescu.Vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chescu.Conexion;
import com.example.chescu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {

    EditText usuario,correo,password,rol;
    Button registrar;

    Conexion conexionBD = new Conexion();
    FirebaseAuth mAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        usuario=(EditText)findViewById(R.id.usuario);
        rol=(EditText)findViewById(R.id.rol);
        correo=(EditText)findViewById(R.id.correo);
        password=(EditText)findViewById(R.id.contraseña);
        registrar=(Button)findViewById(R.id.btn_registrar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarusuario();

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void agregarusuario(){
        try {
            PreparedStatement pst=conexionBD.conexionBD().prepareStatement("Execute SP_Agre_Usuario ?, ?, ?, ?, ?");
            pst.setString(1,rol.getText().toString());
            pst.setString(2,usuario.getText().toString());
            pst.setString(3,correo.getText().toString());
            pst.setString(4,password.getText().toString());
            pst.setBoolean(5,true);
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"REGISTRO CONFIRMADO",Toast.LENGTH_SHORT).show();

            registrarV2(usuario.getText().toString(),correo.getText().toString(),password.getText().toString());
            usuario.setText("");
            correo.setText("");
            password.setText("");
        }catch (SQLException e){
            Log.i("error",e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarV2(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                }else{
                    Toast.makeText(getApplicationContext(),"Error al registrar",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("registro", "onFailure: "+e.getMessage());
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}