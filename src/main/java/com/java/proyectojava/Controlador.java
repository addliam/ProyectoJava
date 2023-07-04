package com.java.proyectojava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
        configurarTablas();
    }

    private void jButtonCalcularTotalActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        int numeroFilas = modeloTablaLista.getRowCount();
        double total = 0;
        // POR HACER: validacion de datos         
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
        
        modeloTablaLista = new DefaultTableModel(data, nombreColumnasLista);
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
            public void mouseClicked(MouseEvent me){
                if (me.getClickCount() == 2){
                    int row = vista.jTableProductos.getSelectedRow();
                    
                    int numeroColumnas = 4;
                    int idSeleccionado = Integer.parseInt(vista.jTableProductos.getValueAt(row, 0).toString());
                    String tiendaSeleccionado = vista.jTableProductos.getValueAt(row, 1).toString();
                    String productoSeleccionado = vista.jTableProductos.getValueAt(row, 2).toString();
                    
                    double precioSeleccionado = Double.parseDouble(vista.jTableProductos.getValueAt(row, 3).toString());
                    Producto prod = new Producto();
                    prod.setId(idSeleccionado);
                    prod.setTienda(tiendaSeleccionado);
                    prod.setProducto(productoSeleccionado);
                    prod.setPrecio(precioSeleccionado);
                    Object[] nuevaFila = {prod.getId(),prod.getTienda(),prod.getProducto(),1,prod.getPrecio()};
                    modeloTablaLista.addRow(nuevaFila);
                }
            }
        });      
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("a");
    }
}
