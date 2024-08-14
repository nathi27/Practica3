/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Conexion;

import com.Modelo.Compra;
import com.Modelo.Funcionario;
import com.Modelo.Producto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCompra extends ConexionMysql{
    
    //Metodo para registrar una nueva compra
    public boolean registrarCompra(Compra compra){
        
        Connection con = getConnection();
        String sql = "INSERT INTO Compra (funcionario_id, fecha, total) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, compra.getFuncionario().getId()); // Assumes Funcionario has a getId() method
            pst.setDate(2, new java.sql.Date(compra.getFecha().getTime()));
            pst.setDouble(3, compra.getTotal());
            pst.executeUpdate();
            
            //Obtiene el id de la compra recien insertada
            ResultSet rs = pst.getGenerateKeys();
            if(rs.next()){
                int compraId = rs.getInt(1);
                // Registrar los productos asociados a la compra (esto es un ejemplo, se puede ajustar según la lógica de la base de datos)
                for (Producto producto : compra.getProductos()) {
                    registrarProductoCompra(compraId, producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }
    }
            
    
    private void registrarProductoCompra(int compraId, Producto producto){
        Connection con = getConnection();
        String sql = "INSERT INTO CompraProducto(compra_id, producto_id) VALUES(?,?)";
        try{
            PreparedStatement pst = con.prepareStatement(sql);
               pst.setInt(1, compraId);
               pst.setInt(2, producto.getId());
               pst.executeUpdate();
           } catch (SQLException e) {
               e.printStackTrace();
           } finally {
               closeConnection(con);
           }
       }
    
    public List<Compra> obtenerCompras(){
        List<Compra> compras = new ArrayList<>();
        Connection con = getConnection();
        String sql = "SELECT * FROM Compra";
        try{
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Compra compra = new Compra();
                compra.setId(rs.getInt("id"));
                compra.setFuncionario(obtenerFuncionario(rs.getInt("funcionario_id")));
                compra.setFecha(rs.getDate("fecha"));
                compra.setTotal(rs.getDouble("total"));
                compra.setProductos(obtenerProductosPorCompras(rs.getInt("id")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con);
        }
        return compras;
    }
    
    private Funcionario obtenerFuncionario(int funcionarioId){
        Funcionario funcionario = null;
        Connection con = getConnection();
        String sql = "SELECT * FROM Funcionario WHERE id = ?";
        try{
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, funcionarioId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNombre(rs.getString("nombre"));
                // Agregar otros campos según la estructura de la tabla Funcionario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }
        return funcionario;
    }
    
    private List<Producto> obtenerProductosPorCompra(int compraId){
        List<Productto> productos = new ArrayList><();
        Connection con = getConnecction();
        String sql = "SELECT p.* FROM Producto p " +
                     "JOIN CompraProducto cp ON p.id = cp.producto_id " +
                     "WHERE cp.compra_id = ?";
        try{
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, compraId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTipo(rs.getString("tipo"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }
        return productos;
    }
}
