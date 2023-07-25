package com.example.chescu.model;

public class DetallePedido {

    private int Id_detallePedido;

    private Pedido pedido;

    private Plato plato;

    public DetallePedido(int id_detallePedido, Pedido pedido, Plato plato) {
        Id_detallePedido = id_detallePedido;
        this.pedido = pedido;
        this.plato = plato;
    }

    public DetallePedido(Plato plato) {
        this.plato = plato;
    }

    public DetallePedido() {
    }

    public int getId_detallePedido() {
        return Id_detallePedido;
    }

    public void setId_detallePedido(int id_detallePedido) {
        Id_detallePedido = id_detallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}
