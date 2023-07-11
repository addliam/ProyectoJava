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
    static public ArrayList<ArrayList<Object>> ordenarAlfabeticamente(ArrayList<ArrayList<Object>> data) {
        mergeSort(data, 0, data.size() - 1);
        return data;
    }

    static private void mergeSort(ArrayList<ArrayList<Object>> data, int inicio, int fin) {
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;

            mergeSort(data, inicio, medio);
            mergeSort(data, medio + 1, fin);

            merge(data, inicio, medio, fin);
        }
    }

    static private void merge(ArrayList<ArrayList<Object>> data, int inicio, int medio, int fin) {
        ArrayList<ArrayList<Object>> izquierda = new ArrayList<>(data.subList(inicio, medio + 1));
        ArrayList<ArrayList<Object>> derecha = new ArrayList<>(data.subList(medio + 1, fin + 1));

        int i = 0, j = 0, k = inicio;

        while (i < izquierda.size() && j < derecha.size()) {
            String nombreIzq = (String) izquierda.get(i).get(2);
            String nombreDer = (String) derecha.get(j).get(2);

            if (nombreIzq.compareToIgnoreCase(nombreDer) <= 0) {
                data.set(k, izquierda.get(i));
                i++;
            } else {
                data.set(k, derecha.get(j));
                j++;
            }
            k++;
        }

        while (i < izquierda.size()) {
            data.set(k, izquierda.get(i));
            i++;
            k++;
        }

        while (j < derecha.size()) {
            data.set(k, derecha.get(j));
            j++;
            k++;
        }
    }
}
