/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Controlador;

import com.Conexion.SQLCompra;
import com.Modelo.Compra;
import com.Modelo.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class LogicaCompraControlador { //Controlador que maneja las operaciones relacionadas con las compras
    
    DefaultTableModel modelo = new DefaultTableModel();
    
    //metodo para cargar datos de las compras en una tabla
    public void cargarDatos(DefaultTableModel modelo){
        
        modelo.addColumn("ID");
        modelo.addColumn("Funcionario");
        modelo.addColumn("Fecha");
        modelo.addColumn("Total");
        modelo.addColumn("Productos");
        
        SQLCompra modSql = new SQLCompra();
        ResultSet rs = modSql.obtenerCompras();
        modelo.setRowCount(0); //Limpia tabla
        
        try{
            while(rs.next()){
                modelo.addRow(new Object[]{
                    rs.getInt("id"), 
                    rs.getString("funcionario"), 
                    rs.getDate("fecha"), 
                    rs.getDouble("total"),
                    rs.getString("productos")
                });
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        } finally{
            try{
                if(rs !=null){
                    rs.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    //Metodo para registrar una nueva compra
    public void registrarCompra(SQLCompra modSql, Compra mod, JTextField txtId, JTextField txtFuncionario, JTextField txtFecha, JTextField txtTotal, List<Producto> productos) {
        
        mod.setId(Integer.parseInt(txtId.getText()));
        mod.setFuncionario(txtFuncionario.getText()); // Aquí asumo que el nombre del funcionario es un String
        mod.setFecha(java.sql.Date.valueOf(txtFecha.getText())); // Convierte el texto a tipo Date
        mod.setTotal(Double.parseDouble(txtTotal.getText()));
        mod.setProductos(productos); // Asigna la lista de productos a la compra

        if (modSql.registrar(mod)) {
            JOptionPane.showMessageDialog(null, "Compra registrada con éxito");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra");
        }
    }
    
}
