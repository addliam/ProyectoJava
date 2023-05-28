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
    
    public Producto(int id, int codTienda, String producto, double precio) {
        this.id = id;
        this.codTienda = codTienda;
        this.producto = producto;
        this.precio = precio;
        
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
