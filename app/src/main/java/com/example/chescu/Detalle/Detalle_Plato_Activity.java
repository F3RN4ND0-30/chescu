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
import com.example.chescu.Gestion.Gestion_platos_Activity;
import com.example.chescu.R;
import com.example.chescu.Vistas.MenuActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Detalle_Plato_Activity extends AppCompatActivity {

    private EditText nombre,precio,desc;

    private Button guardar;

    private Integer id_plato;

    private RadioGroup Stado;

    private RadioButton rbActive,rbInac;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plato);

        nombre = findViewById(R.id.etNombre);
        precio = findViewById(R.id.etPrecio);
        desc = findViewById(R.id.etDesc);
        guardar = findViewById(R.id.btnSave);
        Stado = findViewById(R.id.rgStado);
        rbActive = findViewById(R.id.rbActive);
        rbInac = findViewById(R.id.rbInac);

        id_plato = getIntent().getIntExtra("id",1);

        if(id_plato != null){
            getPlato();
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPlato();

                Intent intent = new Intent(guardar.getContext(), MenuActivity.class);
                startActivity(intent);

            }
        });

    }

    public void editarPlato(){
        try {
            String query = "execute sp_update_platos ?, ?, ?, ?,?";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setInt(1, id_plato);
            ps.setString(2, nombre.getText().toString());
            ps.setString(3, precio.getText().toString());
            ps.setString(4, desc.getText().toString());
            ps.setBoolean(5,Stado.getCheckedRadioButtonId()==R.id.rbActive?true:false);
            ps.executeUpdate();

            Toast.makeText(this,"SE GUARDARON LOS CAMBIOS",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Log.i("error",e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void getPlato(){
        try {
            String query = "SELECT * FROM Plato WHERE Id_plato = ?";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setInt(1,id_plato);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                nombre.setText(rs.getString("Id_tipoplato"));
                precio.setText(rs.getString("Prec_plato"));
                desc.setText(rs.getString("Desc_plato"));
                boolean Estado = rs.getBoolean("Stado");
                rbActive.setChecked(Estado?true:false);
                rbInac.setChecked(!Estado?true:false);
            }

        } catch (SQLException e) {
            Log.i("error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}