package com.example.chescu.model;

public class Pedido {
    private int Id_pedido;
    private User Usuario;
    private Cliente cliente;
    private Boolean Estado;

    public Pedido(int id_pedido, User usuario, Cliente cliente, Boolean estado) {
        Id_pedido = id_pedido;
        Usuario = usuario;
        this.cliente = cliente;
        Estado = estado;
    }

    public Pedido(int id_pedido) {
        Id_pedido = id_pedido;
    }

    public Pedido() {
    }

    public int getId_pedido() {
        return Id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        Id_pedido = id_pedido;
    }

    public User getUsuario() {
        return Usuario;
    }

    public void setUsuario(User usuario) {
        Usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }
}
