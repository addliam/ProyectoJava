/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyectojava;

import java.util.ArrayList;

/**
 *
 * @author liamy
 */
public class Utilidad {
    static public ArrayList<ArrayList<Object>> ordenarAlfabeticamente(ArrayList<ArrayList<Object>> data ){
        // ArrayList<ArrayList<Object>> data es un array que contiene otro array
        // Estructura lista interior: [ID, Tienda, Producto, Cantidad, Precio]
        // EJEMPLO ENTRADA: 
        // [
        // ["1","Tottus", "papa amarilla cocktail tottus", "1", "1.0"],
        // ["12","Tottus", "arroz partido tottus", "1", "1.0"],
        // ["4","Tottus", "canela en polvo tottus", "1", "1.0"],
        // ["2","Tottus", "sandia tottus", "1", "1.0"]
        // ]
        // 
        // debe ser ordenado alfabeticamente por nombre de PRODUCTO
        // EJEMPLO SALIDA:
        // [
        // ["12","Tottus", "arroz partido tottus", "1", "1.0"],        
        // ["4","Tottus", "canela en polvo tottus", "1", "1.0"],
        // ["1","Tottus", "papa amarilla cocktail tottus", "1", "1.0"],        
        // ["2","Tottus", "sandia tottus", "1", "1.0"]
        // ]
        // 
     return data;  
    }
}
