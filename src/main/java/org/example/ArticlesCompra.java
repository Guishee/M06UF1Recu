package org.example;


import java.io.Serializable;

public class ArticlesCompra implements Serializable {
    private String descripcion;
    private double cantidad;
    private String unidad;
    private String seccion;
    private double precio; //Atributo extra(Precio)

    public ArticlesCompra() {
    }

    public ArticlesCompra(String descripcion, double cantidad, String unidad, String seccion, double precio) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.seccion = seccion;
        this.precio = precio;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

