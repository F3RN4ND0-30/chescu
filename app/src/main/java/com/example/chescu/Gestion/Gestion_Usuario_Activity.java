package com.example.chescu.Gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chescu.Adapter.UserAdapter;
import com.example.chescu.Conexion;
import com.example.chescu.model.User;
import com.example.chescu.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Gestion_Usuario_Activity extends AppCompatActivity {

    RecyclerView userRV;
    UserAdapter userAdapter;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuario);
        userRV = findViewById(R.id.rvUsuarios);
        userRV.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(obtenerUserBD());
        userAdapter.notifyDataSetChanged();
        userRV.setAdapter(userAdapter);

    }

    public List<User> obtenerUserBD(){
        List<User> User = new ArrayList<>();
        try {
            Statement st = conexionBD.conexionBD().createStatement();
            ResultSet rs = st.executeQuery("Execute sp_listar_Usuario");
            while (rs.next()){
                User.add(new User(rs.getInt("Id_usuario"),rs.getString("Usuario"),rs.getString("Correo"),rs.getString("Id_rol"),rs.getBoolean("Estado")));
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return User;
    }
}