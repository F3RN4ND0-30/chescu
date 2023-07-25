package com.example.chescu.Detalle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chescu.Adapter.DetallePedidoAdapter;
import com.example.chescu.Adapter.PedidoAdapter;
import com.example.chescu.Conexion;
import com.example.chescu.R;
import com.example.chescu.model.Cliente;
import com.example.chescu.model.DetallePedido;
import com.example.chescu.model.DetallePedidoV2;
import com.example.chescu.model.Pedido;
import com.example.chescu.model.Plato;
import com.example.chescu.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetallePedido_Activity extends AppCompatActivity {

    RecyclerView pedidoRV;

    DetallePedidoAdapter DetallePedidoAdapter;

    Conexion conexionBD = new Conexion();

    private Integer id_pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        pedidoRV = findViewById(R.id.rvUsuarios);
        pedidoRV.setLayoutManager(new LinearLayoutManager(this));

        id_pedido = getIntent().getIntExtra("id", 1);

        DetallePedidoAdapter = new DetallePedidoAdapter(ObtenerDetallePedidosBD());
        DetallePedidoAdapter.notifyDataSetChanged();
        pedidoRV.setAdapter(DetallePedidoAdapter);


    }

    public List<DetallePedidoV2> ObtenerDetallePedidosBD() {
        List<DetallePedidoV2> DetallePedido = new ArrayList<>();
        try {
            Statement st = conexionBD.conexionBD().createStatement();
            ResultSet rs = st.executeQuery("SELECT PT.Desc_plato, PT.Prec_plato FROM DetallePedido DP" +
                    "INNER JOIN Plato PT ON DP.Id_plato = PT.Id_plato");
            while (rs.next()) {
                DetallePedido.add(new DetallePedidoV2(rs.getString("Desc_plato"), rs.getDouble("Prec_plato")));
            }
        } catch (SQLException e) {
            Log.d("error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return DetallePedido;
    }
}