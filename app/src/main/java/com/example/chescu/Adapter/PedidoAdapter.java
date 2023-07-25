package com.example.chescu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chescu.Conexion;
import com.example.chescu.Detalle.DetallePedido_Activity;
import com.example.chescu.Detalle.Detalle_Plato_Activity;
import com.example.chescu.model.Pedido;
import com.example.chescu.R;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder>{

    Conexion conexionBD = new Conexion();

    private List<Pedido> pedidoList;

    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        Pedido pedido=pedidoList.get(position);
        holder.getAdapterPosition();
        holder.tvNomUser.setText(pedido.getUsuario().getUsuario());
        holder.tvCliente.setText(pedido.getCliente().getNomCliente());
        holder.tvEstado.setText(pedido.getEstado()?"Activo":"Inactivo");
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(pedido.getId_pedido());

                notifyDataSetChanged();
            }
        });
        holder.detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.detalle.getContext(), DetallePedido_Activity.class);
                intent.putExtra("id",pedido.getId_pedido());
                holder.detalle.getContext().startActivity(intent);
            }
        });
    }

    public synchronized void eliminar(int id){
        try {
            String query = "UPDATE Pedido SET Estado = ? WHERE Id_pedido = ?;";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            ps.executeUpdate();

        }catch (SQLException e){
            Log.i("error",e.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return this.pedidoList.size();
    }

    public PedidoAdapter(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomUser,tvCliente,tvEstado;
        ImageView ivUser;
        Button eliminar,detalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomUser = itemView.findViewById(R.id.tvNomUser);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            ivUser = itemView.findViewById(R.id.ivUser);
            eliminar = itemView.findViewById(R.id.Eliminar);
            detalle = itemView.findViewById(R.id.Detalle);
        }
    }


}
