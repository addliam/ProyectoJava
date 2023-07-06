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
        contentStream.setFont(PDType1Font.HELVETICA, 10);
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

            // Dibujar titulo
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(180, 740);
            contentStream.showText("Presupuesto de lista de compras");
            contentStream.endText();
            
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
            // formatear decimales a 2 digitos
            DecimalFormat df2 = new DecimalFormat("#####.00");
            for (ArrayList<Object> innerList: datosTabla){
                try {
                    ArrayList<Object> formattedList = new ArrayList<>();
                    formattedList.add(0, contador);
                    formattedList.add(1, innerList.get(1));
                    String nombreProducto = String.valueOf(innerList.get(2));
                    if (nombreProducto.length() > 32){
                        nombreProducto = nombreProducto.substring(0, 32) + "...";
                    }
                    // formatear el nombre de producto a max 32 chars, completar con puntos suspensivos    
                    formattedList.add(2, nombreProducto);                    
                    int cantidad = Integer.parseInt(String.valueOf(innerList.get(3)));
                    formattedList.add(3, innerList.get(3));
                    double precioUnitario = Double.parseDouble(String.valueOf(innerList.get(4)));
                    formattedList.add(4, df2.format(precioUnitario));
                    double subTotal = precioUnitario * cantidad;
                    formattedList.add(5, df2.format(subTotal));                    
                    total += subTotal;
                    innerList.set(0, document);
                    drawTableRow(contentStream, formattedList);                    
                } catch (Exception e) {
                    System.out.println(e);
                }
                contador += 1;
            }
            // dibujar el total en el PDF
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(-60, -30);            
            contentStream.showText("Total");
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(60, 0);
            contentStream.showText(df2.format(total));
            System.out.println("Total general: "+total);

            contentStream.endText();
            contentStream.close();
            // TODO: generar nombre dinamicamente o preguntar al usuario
            document.save("presupuesto.pdf");
            document.close();
            
            System.out.println("Invoice PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    public static void main(String[] args) {
    }
}
