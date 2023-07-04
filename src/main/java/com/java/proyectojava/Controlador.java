package com.java.proyectojava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author liamy
 */
public class Controlador implements ActionListener {
    private Principal vista;
    private BaseDatos modelo;
    
    DefaultTableModel modeloTablaProducto;
    DefaultTableModel modeloTablaLista;
    private final String[] nombreColumnasProducto = {"ID", "Tienda", "Producto","Precio"};
    private final String[] nombreColumnasLista = {"ID", "Tienda", "Producto","Cantidad","Precio"};
    DecimalFormat df2 = new DecimalFormat("#####.00");    
    
    public Controlador(Principal vista, BaseDatos modelo){
        this.vista = vista;
        this.modelo = modelo;
    };
    
    public void iniciar(){
        vista.setLocationRelativeTo(null);
        vista.setTitle("Buy easy");
        vista.jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vista.BarraBusqueda.setText("");
            }
        });    
        vista.jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        vista.jButtonCalcularTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCalcularTotalActionPerformed(evt);
            }
        });  
        vista.jButtonImprimir.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                dibujarTablaEnPDF();
            }
        });
        configurarTablas();
    }

    private void dibujarTablaEnPDF(){
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        for (int row = 0; row < modeloTablaLista.getRowCount(); row++) {
            ArrayList<Object> rowData = new ArrayList<>();
            for (int col = 0; col < modeloTablaLista.getColumnCount(); col++) {
                Object value = modeloTablaLista.getValueAt(row, col);
                rowData.add(value);
            }
            data.add(rowData);
        }
        // iterar sobre la data en forma de arraylist anidado
        InvoiceTableExample invoiceTable = new InvoiceTableExample();
        // Ordenar los elementos de data alfabeticamente por producto;
        ArrayList<ArrayList<Object>> dataOrdenada = Utilidad.ordenarAlfabeticamente(data);
        invoiceTable.drawTable(dataOrdenada);   
        // abrir el archivo "presupuesto.pdf" con la app predeterminada del sistema
        String fileName = "presupuesto.pdf";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println("Error al abrir el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo no existe en la ubicación especificada.");
        }        
    }
    private void calcularPrecioTotal(){
        // se convirtio a metodo para q sea llamado automaticamente
        int numeroFilas = modeloTablaLista.getRowCount();
        double total = 0;
        for (int i=0;i<numeroFilas;i++){
            int cantidad = Integer.parseInt(modeloTablaLista.getValueAt(i, 3).toString());
            double precio = Double.parseDouble(modeloTablaLista.getValueAt(i, 4).toString());
            double subTotal = precio * cantidad;
            total += subTotal;
        }
        vista.jTextFieldTotal.setText(df2.format(total));    
    }
    private void jButtonCalcularTotalActionPerformed(java.awt.event.ActionEvent evt) {                       
        int numeroFilas = modeloTablaLista.getRowCount();
        double total = 0;
        // POR HACER: validacion de datos enteros en tabla       
        for (int i=0;i<numeroFilas;i++){
            int cantidad = Integer.parseInt(modeloTablaLista.getValueAt(i, 3).toString());
            double precio = Double.parseDouble(modeloTablaLista.getValueAt(i, 4).toString());
            double subTotal = precio * cantidad;
            total += subTotal;
        }
        vista.jTextFieldTotal.setText(df2.format(total));
    }
    
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // limpiamos la tabla
        modeloTablaProducto.setRowCount(0);
        String productoBusqueda = vista.BarraBusqueda.getText();
        System.out.println("ProductoBusqueda:"+productoBusqueda);
        if (!productoBusqueda.isBlank()){
            // consultamos por los productos que empiezan con la palabra de busqueda
            ArrayList<Producto> listaProductos =  modelo.buscarProductos(productoBusqueda);
            // completamos la tabla con los valores obtenidos
            for (Producto prod: listaProductos){
                Object[] fila = {prod.getId(), prod.getTienda(), prod.getProducto(), df2.format(prod.getPrecio())};
                modeloTablaProducto.addRow(fila);            
            }
        }else{
            JOptionPane.showMessageDialog(null, "El campo no puede estar vacio", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }           
    
    private void configurarTablas(){
        Object[][] data = {};
        modeloTablaProducto = new DefaultTableModel(data, nombreColumnasProducto);
        vista.jTableProductos.setModel(modeloTablaProducto);    
        vista.jTableProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        vista.jTableProductos.getColumnModel().getColumn(0).setMaxWidth(50);        
        vista.jTableProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
        vista.jTableProductos.getColumnModel().getColumn(1).setMaxWidth(80);        
        vista.jTableProductos.getColumnModel().getColumn(2).setPreferredWidth(250);
        vista.jTableProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
        vista.jTableProductos.getColumnModel().getColumn(3).setMaxWidth(50);
        
        modeloTablaLista = new DefaultTableModel(data, nombreColumnasLista){
            // hacer solo la columna cantida editable
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 3;
            }
        };

        // verificar si en tabla lista, el valor EDITADO en cantidad es entero positivo
        modeloTablaLista.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 3){
                    int row = e.getFirstRow();
                    String data = modeloTablaLista.getValueAt(row, 3).toString();
                    boolean esEnteroPositivo = false;
                    try {
                        int dataNum = Integer.parseInt(data);
                        if (dataNum > 0){
                            esEnteroPositivo = true;
                        }else{ esEnteroPositivo = false;}
                    } catch (NumberFormatException excp) {
                        esEnteroPositivo = false;
                    }        
                    if (!esEnteroPositivo){
                        // Resetear el valor a uno
                        modeloTablaLista.setValueAt(1, row, 3);
                        JOptionPane.showMessageDialog(null, "Solo se permiten valores enteros positivos", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        // si el valor es entero, calculamos nuevamente el total
                        calcularPrecioTotal();
                    }
                    
                }
            }
        });
        vista.jTableLista.setModel(modeloTablaLista);
        vista.jTableLista.getColumnModel().getColumn(0).setPreferredWidth(50);
        vista.jTableLista.getColumnModel().getColumn(0).setMaxWidth(50);        
        vista.jTableLista.getColumnModel().getColumn(1).setPreferredWidth(80);
        vista.jTableLista.getColumnModel().getColumn(1).setMaxWidth(80);        
        vista.jTableLista.getColumnModel().getColumn(2).setPreferredWidth(250);
        vista.jTableLista.getColumnModel().getColumn(3).setPreferredWidth(70);
        vista.jTableLista.getColumnModel().getColumn(3).setMaxWidth(70);          
        vista.jTableLista.getColumnModel().getColumn(4).setPreferredWidth(50);
        vista.jTableLista.getColumnModel().getColumn(4).setMaxWidth(50);  
        
        vista.jTableProductos.addMouseListener(new MouseAdapter(){
            // metodo que maneja el doble click en la columna producto
            public void mouseClicked(MouseEvent me){
                if (me.getClickCount() == 2){
                    int row = vista.jTableProductos.getSelectedRow();
                    
                    // int numeroColumnas = 4;
                    int idSeleccionado = Integer.parseInt(vista.jTableProductos.getValueAt(row, 0).toString());
                    String tiendaSeleccionado = vista.jTableProductos.getValueAt(row, 1).toString();
                    String productoSeleccionado = vista.jTableProductos.getValueAt(row, 2).toString();
                    
                    double precioSeleccionado = Double.parseDouble(vista.jTableProductos.getValueAt(row, 3).toString());
                    // Revisar si el producto ya fue agregado a la lista, aumentar cantidad += 1
                    int numFilaProductoRepetido = -1;
                    for (int i=0; i<modeloTablaLista.getRowCount();i++){
                        // comprobar si el ID ya se encuentra en la lista seleccionada
                        int id = Integer.parseInt(modeloTablaLista.getValueAt(i, 0).toString());
                        if (id == idSeleccionado){
                            numFilaProductoRepetido = i;
                            break;
                        }
                    }
                    // sino se repite el elemento seleccionado, agregar a la tabla lista
                    if (numFilaProductoRepetido == -1){
                        Producto prod = new Producto();
                        prod.setId(idSeleccionado);
                        prod.setTienda(tiendaSeleccionado);
                        prod.setProducto(productoSeleccionado);
                        prod.setPrecio(precioSeleccionado);
                        Object[] nuevaFila = {prod.getId(),prod.getTienda(),prod.getProducto(),1,prod.getPrecio()};
                        modeloTablaLista.addRow(nuevaFila);                        
                    }
                    else{
                        // actualizar el valor de cantidad 
                        int actualValorCantidad = Integer.parseInt(modeloTablaLista.getValueAt(numFilaProductoRepetido, 3).toString());
                        modeloTablaLista.setValueAt(actualValorCantidad + 1, numFilaProductoRepetido, 3);
                    }
                    // actualizar
                    calcularPrecioTotal();
                }
            }
        });      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("a");
    }
}
