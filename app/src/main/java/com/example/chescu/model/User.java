package com.example.chescu.model;

public class User {

    private int Id_usuario;
    private String Usuario,correo,rol;

    private boolean estado;

    public User(int id_usuario, String usuario, String correo, String rol, boolean estado) {
        Id_usuario = id_usuario;
        Usuario = usuario;
        this.correo = correo;
        this.rol = rol;
        this.estado = estado;
    }

    public User(String usuario) {
        Usuario = usuario;
    }

    public User() {
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        Id_usuario = id_usuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setPass(String pass) {
        this.rol = pass;
    }
}
