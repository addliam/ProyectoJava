/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyectojava;

/**
 *
 * @author liamy
 */
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class InvoiceTableExample {
    // define la separacion entre columnas    
    static private int[] tableOffsets = {60,40,80,180,60,60};   
    
    private void drawTableRow(PDPageContentStream contentStream, ArrayList<Object> data) throws IOException, Exception {
        // el array de datos debe ser exactamente 6 elementos
        // N, Tienda, Producto, Cantidad, Precio, SubTotal
        if (data.size() != 6) {
            throw new Exception("Data debe ser exactamente 6 elementos: N, Tienda, Producto, Cantidad, Precio, SubTotal");
        }
        contentStream.setFont(PDType1Font.HELVETICA, 11);
        // 420 es la suma de los tableOffsets[1:] 
        contentStream.newLineAtOffset(-420, -20); // Move to the next row
        contentStream.showText(String.valueOf(data.get(0)));

        contentStream.newLineAtOffset(tableOffsets[1], 0); // Move to the next column
        contentStream.showText(String.valueOf(data.get(1)));        

        contentStream.newLineAtOffset(tableOffsets[2], 0); // Move to the next column
        contentStream.showText(String.valueOf(data.get(2)));        
        
        contentStream.newLineAtOffset(tableOffsets[3], 0); // Move to the next column
        contentStream.showText(String.valueOf(data.get(3)));        
        
        contentStream.newLineAtOffset(tableOffsets[4], 0); // Move to the next column
        contentStream.showText(String.valueOf(data.get(4)));        
        
        contentStream.newLineAtOffset(tableOffsets[5], 0); // Move to the next column
        contentStream.showText(String.valueOf(data.get(5)));        
        
    }
    
    public void drawTable(ArrayList<ArrayList<Object>> datosTabla){
try {
            // Recibe el contenido sin formato array de array: N, Tienda, Producto, Cantidad, Precio, SubTotal
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        
            // Draw table headers
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();;
            contentStream.newLineAtOffset(tableOffsets[0], 700); // Set starting position of the table
            contentStream.showText("N°");
            contentStream.newLineAtOffset(tableOffsets[1], 0); // Move to the next column
            contentStream.showText("Tienda");
            contentStream.newLineAtOffset(tableOffsets[2], 0); // Move to the next column
            contentStream.showText("Producto");
            contentStream.newLineAtOffset(tableOffsets[3], 0); // Move to the next column
            contentStream.showText("Cantidad");
            contentStream.newLineAtOffset(tableOffsets[4], 0); // Move to the next column
            contentStream.showText("Precio");
            contentStream.newLineAtOffset(tableOffsets[5], 0); // Move to the next column
            contentStream.showText("Subtotal");            
            
            // Draw table rows
            int contador = 1;
            double total = 0;
            for (ArrayList<Object> innerList: datosTabla){
                try {
                    ArrayList<Object> formattedList = new ArrayList<>();
                    formattedList.add(0, contador);
                    formattedList.add(1, innerList.get(1));
                    formattedList.add(2, innerList.get(2));
                    formattedList.add(3, innerList.get(3));
                    formattedList.add(4, innerList.get(4));
                    double subTotal = Double.parseDouble(String.valueOf(innerList.get(4)));
                    DecimalFormat df2 = new DecimalFormat("#####.00");
                    formattedList.add(5, df2.format(subTotal));                    
                    total += subTotal;
                    innerList.set(0, document);
                    drawTableRow(contentStream, formattedList);                    
                } catch (Exception e) {
                    System.out.println(e);
                }
                contador += 1;
            }
            System.out.println("Total general: "+total);

            contentStream.endText();
            contentStream.close();
            
            document.save("invoice.pdf");
            document.close();
            
            System.out.println("Invoice PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public static void main(String[] args) {
    }
}