package com.example.tp4_grupo_7;

public class Articulo {
    int id;
    String nombre;
    int stock;
    int idCat;

    public Articulo(int id, String nombre, int stock, int idCat){
        this.id = id;
        this.nombre= nombre;
        this.stock = stock;
        this.idCat = idCat;
    };

    public Articulo() {

    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Articulos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", idCat=" + idCat +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
