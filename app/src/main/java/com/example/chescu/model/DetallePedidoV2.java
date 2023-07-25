package com.example.chescu.model;

public class DetallePedidoV2 {

    private String nameProduct;
    private Double precioProduct;

    public DetallePedidoV2(String nameProduct, Double precioProduct) {
        this.nameProduct = nameProduct;
        this.precioProduct = precioProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getPrecioProduct() {
        return precioProduct;
    }

    public void setPrecioProduct(Double precioProduct) {
        this.precioProduct = precioProduct;
    }
}
