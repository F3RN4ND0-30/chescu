package com.example.chescu.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chescu.Adapter.PedidoAdapter;
import com.example.chescu.Conexion;
import com.example.chescu.model.Cliente;
import com.example.chescu.model.Pedido;
import com.example.chescu.model.User;
import com.example.chescu.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {

    RecyclerView pedidoRV;

    PedidoAdapter pedidoAdapter;

    Conexion conexionBD = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        pedidoRV = findViewById(R.id.rvUsuarios);
        pedidoRV.setLayoutManager(new LinearLayoutManager(this));
        pedidoAdapter = new PedidoAdapter(ObtenerpedidosBD());
        pedidoAdapter.notifyDataSetChanged();
        pedidoRV.setAdapter(pedidoAdapter);
    }

    public List<Pedido> ObtenerpedidosBD(){
        List<Pedido> Pedido = new ArrayList<>();
        try {
            Statement st = conexionBD.conexionBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT P.Id_pedido, U.*, C.* FROM Pedido P\n" +
                    "INNER JOIN Usuario U ON P.Id_usuario = U.Id_usuario\n" +
                    "INNER JOIN Cliente C ON P.Id_cliente = C.Id_cliente\n" +
                    "WHERE P.Estado = 'TRUE'");
            while (rs.next()){
                Pedido.add(new Pedido(rs.getInt("Id_pedido"),new User(rs.getString("Usuario")),new Cliente(rs.getString("Nom_cliente")),rs.getBoolean("Estado")));
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Pedido;
    }
}