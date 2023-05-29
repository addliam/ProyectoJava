package com.java.proyectojava;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author liamy
 */
public class Producto {
    int id;
    int codTienda;
    String producto;
    double precio;
    String tienda;

    public Producto(){};
    
    public Producto(int id, int codTienda, String producto, double precio) {
        this.id = id;
        this.codTienda = codTienda;
        this.producto = producto;
        this.precio = precio;
        actualizarTienda();
    }    
    
    private void actualizarTienda(){
        String nombreTienda = "";
        switch (codTienda) {
            case 1:
               nombreTienda = "Metro";
                break;
            case 2:
               nombreTienda = "Tottus";
                break;
            case 3:
               nombreTienda = "Plaza Vea";
                break;                
            default:
                nombreTienda = "Desconocido";
        }
        tienda = nombreTienda;
    }

    @Override
    public String toString() {
        return ("Id: "+id+", Tienda: "+tienda+", Producto: "+producto+", Precio: "+precio);
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setCodTienda(int codTienda) {
        this.codTienda = codTienda;
        actualizarTienda();
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }
    
    public int getId() {
        return id;
    }

    public int getCodTienda() {
        return codTienda;
    }
    
    public String getTienda(){
        return tienda;
    }

    public String getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }
    
}
