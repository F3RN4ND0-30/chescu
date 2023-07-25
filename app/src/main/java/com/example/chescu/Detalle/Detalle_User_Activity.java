package com.example.chescu.Detalle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chescu.Conexion;
import com.example.chescu.R;
import com.example.chescu.Vistas.MenuActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Detalle_User_Activity extends AppCompatActivity {

    private EditText rol,usuario,correo,pass;
    private Button guardar;

    private Integer id_usuario;

    private RadioGroup estado;

    private RadioButton rbActivo,rbInactivo;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_user);

        rol = findViewById(R.id.etRol);
        usuario = findViewById(R.id.etUser);
        correo = findViewById(R.id.etCorreo);
        guardar = findViewById(R.id.btnguardar);
        pass = findViewById(R.id.etPass);
        estado = findViewById(R.id.rgEstado);
        rbActivo = findViewById(R.id.rbActivo);
        rbInactivo = findViewById(R.id.rbInactivo);

        id_usuario = getIntent().getIntExtra("id",1);

        if(id_usuario != null){
            getUser();
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editarUser();

                Intent intent = new Intent(guardar.getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

    }


    public void editarUser(){
        try {
            String query = "execute sp_update_Usuario ?, ?, ?, ?, ?, ?";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setInt(1, id_usuario);
            ps.setString(2, rol.getText().toString());
            ps.setString(3, usuario.getText().toString());
            ps.setString(4, correo.getText().toString());
            ps.setString(5,pass.getText().toString());
            ps.setBoolean(6,estado.getCheckedRadioButtonId()==R.id.rbActivo?true:false);
            ps.executeUpdate();

            Toast.makeText(this,"SE GUARDARON LOS CAMBIOS",Toast.LENGTH_SHORT).show();

        }catch (SQLException e){
            Log.i("error",e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void getUser() {
        try {
            String query = "SELECT * FROM Usuario WHERE Id_usuario = ?";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setInt(1,id_usuario);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                rol.setText(rs.getString("Id_rol"));
                usuario.setText(rs.getString("Usuario"));
                correo.setText(rs.getString("Correo"));
                pass.setText(rs.getString("Pass"));
                boolean Estado = rs.getBoolean("Estado");
                rbActivo.setChecked(Estado?true:false);
                rbInactivo.setChecked(!Estado?true:false);
            }

        } catch (SQLException e) {
            Log.i("error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}