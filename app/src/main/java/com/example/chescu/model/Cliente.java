package com.example.chescu.model;

public class Cliente {

    private int Id_cliente;

    private String NomCliente,DNI;

    public Cliente(int id_cliente, String nomCliente, String DNI) {
        Id_cliente = id_cliente;
        NomCliente = nomCliente;
        this.DNI = DNI;
    }

    public Cliente(String nomCliente) {
        NomCliente = nomCliente;
    }

    public Cliente() {
    }

    public int getId_cliente() {
        return Id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        Id_cliente = id_cliente;
    }

    public String getNomCliente() {
        return NomCliente;
    }

    public void setNomCliente(String nomCliente) {
        NomCliente = nomCliente;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
