/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Controlador;

import com.Conexion.SQLFuncionario;
import com.Modelo.Funcionario;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LogicaFuncionarioControlador {
    
     DefaultTableModel modelo = new DefaultTableModel();

    public void cargarDatos(DefaultTableModel modelo) {

        modelo.addColumn("id");
        modelo.addColumn("Nombre");

        SQLFuncionario modql = new SQLFuncionario();
        ResultSet rs = modql.obtenerFuncionarios();
        modelo.setRowCount(0);
        try {
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void registarUsusario(SQLFuncionario modSql, Funcionario mod, JPasswordField txtPassword, JTextField txtId, JTextField txtNombre) {
        String password = new String(txtPassword.getPassword());
        
            
        mod.setId(Integer.parseInt(txtId.getText()));
        mod.setNombre(txtNombre.getText());
        mod.setPassword(password);

        if (modSql.registrar(mod)) {
            JOptionPane.showMessageDialog(null, "Registro guardado");
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar");
        }
    }

}

