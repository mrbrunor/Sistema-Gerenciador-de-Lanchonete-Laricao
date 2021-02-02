/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.dao;


import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.Box;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoxDao {
    
    Connection connection = null;
    
    public void openConnection() {
        connection = new ConnectionFactory().getConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
    }

    public int newBox(Box newBox) {
        String sql = "INSERT INTO luanagon_laricao.Box(idEmployee, funds, openingDate, closingDate, isOpen, value) values(?,?,?,?,?,?)";
        PreparedStatement stmt;
        int result = 0;

        try {
            stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, newBox.getIdEmployee());
            stmt.setDouble(2, newBox.getFunds());
            stmt.setTimestamp(3, newBox.getOpeningDate());
            stmt.setTimestamp(4, newBox.getClosingDate());
            stmt.setInt(5, newBox.getIsOpen());
            stmt.setDouble(6, newBox.getValue());            
            stmt.execute();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = (int) generatedKeys.getLong(1);
                } else {
                    //Logger.getLogger(BoxDao.class).debug("Falha, id não obtido");
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        } 
        return result;
    }
    
    public List<Box> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Box";
        PreparedStatement stmt;
        ResultSet res;
        List<Box> resultListBox = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Box box = new Box();
                box.setIdBox(res.getInt("idBox"));
                box.setIdEmployee(res.getInt("idEmployee"));
                box.setFunds(res.getDouble("funds"));
                box.setOpeningDate(res.getTimestamp("openingDate"));
                box.setClosingDate(res.getTimestamp("closingDate"));
                box.setIsOpen(res.getInt("isOpen"));
                box.setValue(res.getDouble("value"));                
                resultListBox.add(box);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
        return resultListBox;
    }

    public List<Box> listBoxsFromEmployee(int idEmployee) {
        String sql = "SELECT * FROM luanagon_laricao.Box where idEmployee=? AND isOpen=1";
        PreparedStatement stmt;
        ResultSet res;
        List<Box> resultListBox = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idEmployee);
            res = stmt.executeQuery();
            while (res.next()) {
                Box box = new Box();
                box.setIdBox(res.getInt("idBox"));
                box.setIdEmployee(res.getInt("idEmployee"));
                box.setFunds(res.getDouble("funds"));
                box.setOpeningDate(res.getTimestamp("openingDate"));
                box.setClosingDate(res.getTimestamp("closingDate"));
                box.setIsOpen(res.getInt("isOpen"));
                box.setValue(res.getDouble("value"));                
                resultListBox.add(box);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
        return resultListBox;
    }
    
    public Box listBoxById(int idBox) {
        String sql = "SELECT * FROM luanagon_laricao.Box where idBox=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idBox);
            res = stmt.executeQuery();
            while (res.next()) {
                Box box = new Box();
                box.setIdBox(res.getInt("idBox"));
                box.setIdEmployee(res.getInt("idEmployee"));
                box.setFunds(res.getDouble("funds"));
                box.setOpeningDate(res.getTimestamp("openingDate"));
                box.setClosingDate(res.getTimestamp("closingDate"));
                box.setIsOpen(res.getInt("isOpen"));
                box.setValue(res.getDouble("value"));                
                return box;
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
        return null;
    }

    public boolean updateBox(Box newBox) {
        String sql = "UPDATE luanagon_laricao.Box SET idEmployee=?, funds=?, openingDate=?, closingBox=?, isOpen=?, value=? WHERE idBox=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newBox.getIdEmployee());
            stmt.setDouble(2, newBox.getFunds());
            stmt.setTimestamp(3, newBox.getOpeningDate());
            stmt.setTimestamp(4, newBox.getClosingDate());
            stmt.setInt(5, newBox.getIsOpen());
            stmt.setDouble(6, newBox.getValue());            
            stmt.setInt(7, newBox.getIdBox());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
        return resultado;
    }

    public boolean removeBox(Box newBox) {
        String sql = "DELETE FROM luanagon_laricao.Box WHERE idBox=?";
        PreparedStatement stmt;
        boolean resultado = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newBox.getIdBox());

            stmt.execute();
            stmt.close();
            resultado = true;

        } catch (SQLException ex) {
            //Logger.getLogger(BoxDao.class).debug(ex);
        }
        return resultado;
    }
    
}
