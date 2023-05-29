/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.java.proyectojava;

/**
 *
 * @author liamy
 */
public class ProyectoJava {

    public static void main(String[] args) {
        System.out.println("Iniciando aplicacion!");
//        Principal interfazPrincipal = new Principal();
//        interfazPrincipal.setLocationRelativeTo(null);
//        interfazPrincipal.setVisible(true);
//        interfazPrincipal.toFront();
            Principal vista = new Principal();
            BaseDatos modelo = new BaseDatos();
            Controlador controlador = new Controlador(vista, modelo);
            controlador.iniciar();
            vista.setVisible(true);
    }
}
