package com.example.chescu.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chescu.model.DetallePedido;
import com.example.chescu.R;
import com.example.chescu.model.DetallePedidoV2;

import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder>{

    private List<DetallePedidoV2> detallepedidoList;
    @NonNull
    @Override
    public DetallePedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DetallePedidoAdapter.ViewHolder holder, int position) {
        DetallePedidoV2 detallePedido=detallepedidoList.get(position);
        holder.getAdapterPosition();
        holder.tvNomProduc.setText(detallePedido.getNameProduct());
//        holder.tvClase.setText(detallePedido.getPlato().getId_tipoplato());
        holder.tvPrec.setText(detallePedido.getPrecioProduct().toString());
    }

    @Override
    public int getItemCount() {
        return this.detallepedidoList.size();
    }

    public DetallePedidoAdapter(List<DetallePedidoV2> detallepedidoList) {
        this.detallepedidoList = detallepedidoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomProduc,tvClase,tvPrec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomProduc = itemView.findViewById(R.id.tvNomProduc);
            tvClase = itemView.findViewById(R.id.tvClase);
            tvPrec = itemView.findViewById(R.id.tvPrec);
        }
    }
}
