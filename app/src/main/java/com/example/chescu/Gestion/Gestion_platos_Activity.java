package com.example.chescu.Gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chescu.Adapter.PlatoAdapter;
import com.example.chescu.Conexion;
import com.example.chescu.Detalle.Agregar_Plato_Activity;
import com.example.chescu.model.Plato;
import com.example.chescu.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Gestion_platos_Activity extends AppCompatActivity {

    RecyclerView platoRV;

    PlatoAdapter platoAdapter;

    Button agregarPlato;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_platos);
        platoRV = findViewById(R.id.rvPlatos);
        platoRV.setLayoutManager(new LinearLayoutManager(this));
        platoAdapter = new PlatoAdapter(obtenerPlatoBD());
        platoAdapter.notifyDataSetChanged();
        platoRV.setAdapter(platoAdapter);
        agregarPlato = findViewById(R.id.btn_AgregarPlato);

        agregarPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Agregar_Plato_Activity.class));
            }
        });
    }

    public List<Plato>obtenerPlatoBD(){
        List<Plato> Plato = new ArrayList<>();
        try {
            Statement st = conexionBD.conexionBD().createStatement();
            ResultSet rs = st.executeQuery("Execute sp_listar_platos");
            while (rs.next()){
                Plato.add(new Plato(rs.getInt("Id_plato"),rs.getString("Id_tipoplato"),rs.getDouble("Prec_plato"),rs.getString("Desc_plato"),rs.getBoolean("Stado")));
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Plato;
    }
}