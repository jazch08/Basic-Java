/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class ClienteDao {
    
    private Connection conectar(){
        
        String baseDeDatos = "java";
        String usuario = "root";
        String password = "";
        String host = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://"+host+":"+puerto+"/"+baseDeDatos+"?useSSL=false";
        
        Connection conexion = null;
        
        try {
            
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
            System.out.println("Conexion a la Base de datos exitosa");
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conexion;
    }
    
    public void agregar(Cliente cliente){
        
        try {
            Connection conexion = conectar();
            String sql = "INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`) VALUES (NULL, '"+cliente.getNombre()+"', '"+cliente.getApellido()+"', '"+cliente.getTelefono()+"', '"+cliente.getEmail()+"');";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
            
        }catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Cliente> Listar(){
       
        List<Cliente> listado = new ArrayList<>();
        
        try {
            Connection conexion = conectar();
            String sql = "SELECT * FROM `clientes`;";
            Statement statement = conexion.createStatement();
            ResultSet response = statement.executeQuery(sql);
            
            
            
            while(response.next()){
                Cliente cliente = new Cliente();
                cliente.setId(response.getString("id"));
                cliente.setNombre(response.getString("nombre"));
                cliente.setApellido(response.getString("apellido"));
                cliente.setTelefono(response.getString("telefono"));
                cliente.setEmail(response.getString("email"));
                
                listado.add(cliente);
            
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listado;
        
    }
    
    public void eliminar(String id){
  
        try {
            Connection conexion = conectar();
            String sql = "DELETE FROM `clientes` WHERE `clientes`.`id` =" + id + ";";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
    public void actualizar(Cliente cliente){
        
        try {
            Connection conexion = conectar();
            
            
            String sql = "UPDATE `clientes` SET `nombre` = '"+cliente.getNombre()+"', `apellido` = '"+cliente.getApellido()+"', `telefono` = '"+cliente.getTelefono()+"', `email` = '"+cliente.getEmail()+"' WHERE `clientes`.`id` = "+cliente.getId()+";";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
            
        }catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void guardar(Cliente cliente) {
        if(StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())){
            agregar(cliente);
        }
        else{
            actualizar(cliente);
        }
    }
    
}
