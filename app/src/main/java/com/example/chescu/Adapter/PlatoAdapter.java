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
import com.example.chescu.Detalle.Detalle_Plato_Activity;
import com.example.chescu.model.Plato;
import com.example.chescu.R;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.ViewHolder>{

    Conexion conexionBD = new Conexion();
    private List<Plato>platoList;

    @NonNull
    @Override
    public PlatoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plato,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoAdapter.ViewHolder holder, int position) {
        Plato plato=platoList.get(position);
        holder.getAdapterPosition();
        holder.tvTipo.setText(plato.getId_tipoplato());
        holder.tvPrecio.setText(plato.getPrecio().toString());
        holder.tvDesc.setText(plato.getDesc());
        holder.tvEstado.setText(plato.isStado()?"Activo":"Inactivo");
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.editar.getContext(), Detalle_Plato_Activity.class);
                intent.putExtra("id",plato.getId_plato());
                holder.editar.getContext().startActivity(intent);
            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(plato.getId_plato());

                notifyDataSetChanged();
            }
        });
    }

    public synchronized void eliminar(int id){
        try {
            String query = "Execute sp_inhabilitar_platos ?, ?";
            PreparedStatement ps = conexionBD.conexionBD().prepareStatement(query);
            ps.setInt(1, id);
            ps.setBoolean(2, false);
            ps.executeUpdate();

        }catch (SQLException e){
            Log.i("error",e.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return this.platoList.size();
    }


    public PlatoAdapter(List<Plato> platoList) {
        this.platoList = platoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTipo,tvPrecio,tvDesc,tvEstado;
        ImageView ivUser;
        Button eliminar,editar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivUser = itemView.findViewById(R.id.ivUser);
            eliminar = itemView.findViewById(R.id.Eliminar);
            editar = itemView.findViewById(R.id.Editar);
        }
    }
}
