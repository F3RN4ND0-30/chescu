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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chescu.Conexion;
import com.example.chescu.Detalle.Detalle_User_Activity;
import com.example.chescu.R;
import com.example.chescu.Vistas.MenuActivity;
import com.example.chescu.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Conexion conexionBD = new Conexion();
    private List<User>userList;

    @NonNull
    @Override

    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user=userList.get(position);
        holder.getAdapterPosition();
        holder.tvName.setText(user.getUsuario());
        holder.tvCorreo.setText(user.getCorreo());
        holder.tvRol.setText(user.getRol());
        holder.tvStado.setText(user.isEstado()?"Activo":"Inactivo");
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.editar.getContext(), Detalle_User_Activity.class);
                intent.putExtra("id",user.getId_usuario());
                holder.editar.getContext().startActivity(intent);
            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eliminar(user.getId_usuario());
                notifyDataSetChanged();

            }
        });
    }

    public synchronized void eliminar(int id){
        try {
            String query = "Execute sp_inhabilitar_Usuario ?, ?";
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
        return this.userList.size();
    }

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvRol,tvCorreo,tvStado;
        ImageView ivUser;

        Button editar, eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRol = itemView.findViewById(R.id.tvRol);
            tvStado = itemView.findViewById(R.id.tvStado);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            ivUser = itemView.findViewById(R.id.ivUser);
            editar = itemView.findViewById(R.id.Editar);
            eliminar = itemView.findViewById(R.id.Eliminar);
        }
    }
}
