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
import java.sql.SQLException;

public class Agregar_Plato_Activity extends AppCompatActivity {

    private EditText precio,desc,tipo;

    private Button guardar;

    private RadioGroup Stado;

    private RadioButton rbActive,rbInac;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_plato);
        precio = findViewById(R.id.etPrec);
        tipo = findViewById(R.id.etTipo);
        desc = findViewById(R.id.etDes);
        guardar = findViewById(R.id.btnNew_Plato);
        Stado = findViewById(R.id.rgStad);
        rbActive = findViewById(R.id.rbActi);
        rbInac = findViewById(R.id.rbNoActi);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearPlato();

                Intent intent = new Intent(guardar.getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public void CrearPlato(){
        try{
            PreparedStatement pst=conexionBD.conexionBD().prepareStatement("Execute SP_Agre_Plato ?,?,?,?");
            pst.setString(1,tipo.getText().toString());
            pst.setString(2,precio.getText().toString());
            pst.setString(3,desc.getText().toString());
            pst.setBoolean(4,Stado.getCheckedRadioButtonId()==R.id.rbActi?true:false);
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"REGISTRO CONFIRMADO",Toast.LENGTH_SHORT).show();

        }catch (SQLException e){
            Log.i("error",e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}