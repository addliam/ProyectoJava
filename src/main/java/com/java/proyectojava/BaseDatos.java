/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyectojava;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author liamy
 */
public class BaseDatos {
    
//    private static final String URL_STRING = "jdbc:postgresql://ep-rapid-wave-338368.us-west-2.aws.neon.tech/neondb?user=liamyostin2004&password=gaPS1ytGBp7M";
    private static final String URL_STRING = "jdbc:postgresql://localhost:5432/listacompras?user=postgres&password=TryHackM3";    
    private Connection db = null;

    
    public Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        if (db == null){
            try{
                db = DriverManager.getConnection(URL_STRING);
            }
            catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return db;
    }    
    
    // retorna un arrayList de productos que empiecen con el "nombre" proporcionado como parametro
    public ArrayList<Producto> buscarProductos(String nombre){
        ArrayList<Producto> listaProductos = new ArrayList<>();
        
        System.out.println("BUSCANDO PRODUCTO "+nombre+"...");
        long startTime = System.currentTimeMillis();

        Connection db = this.getConnection();
        try {
            String sql = "select * from productos WHERE producto LIKE ? || '%' LIMIT 30";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int supermercado_id = rs.getInt("supermercado_id");
                String producto = rs.getString("producto");
                double precio = rs.getDouble("precio");
                listaProductos.add(new Producto(id, supermercado_id, producto, precio));                
            }
            rs.close();
        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Tiempo demora: " + elapsedTime + " ms");        
        return listaProductos;
    }        
}
