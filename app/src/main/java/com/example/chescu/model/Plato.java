package com.example.chescu.model;

public class Plato {

    private int Id_plato;
    private String Id_tipoplato,Desc;

    private Double Precio;

    private boolean Stado;

    public Plato(int id_plato, String id_tipoplato, Double precio, String desc, boolean stado) {
        Id_plato = id_plato;
        Id_tipoplato = id_tipoplato;
        Precio = precio;
        Desc = desc;
        Stado = stado;
    }

    public Plato(String desc, Double precio) {
        Desc = desc;
        Precio = precio;
    }

    public Plato() {
    }

    public int getId_plato() {
        return Id_plato;
    }

    public void setId_plato(int id_plato) {
        Id_plato = id_plato;
    }

    public String getId_tipoplato() {
        return Id_tipoplato;
    }

    public void setId_tipoplato(String id_tipoplato) {
        Id_tipoplato = id_tipoplato;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public boolean isStado() {
        return Stado;
    }

    public void setStado(boolean stado) {
        Stado = stado;
    }
}
