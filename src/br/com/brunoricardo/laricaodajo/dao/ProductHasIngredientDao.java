package br.com.brunoricardo.laricaodajo.dao;

import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.ProductHasIngredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductHasIngredientDao {

    Connection connection = null;

    public void openConnection() {
        connection = new ConnectionFactory().getConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
    }

    public void newProductHasIngredients(int idProduct, int idIngredient) {
        String sql = "INSERT INTO luanagon_laricao.Product_Has_Ingredient(idProduct, idIngredient) values(?,?)";
        PreparedStatement stmt;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idProduct);
            stmt.setInt(1, idIngredient);

            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
    }

    public List<ProductHasIngredient> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Product_Has_Ingredient ORDER BY idProduct";
        PreparedStatement stmt;
        ResultSet res;
        List<ProductHasIngredient> resultListProductHasIngredients = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                ProductHasIngredient newProductHasIngredient = new ProductHasIngredient();
                newProductHasIngredient.setIdProduct(res.getInt("idProduct"));
                newProductHasIngredient.setIdIngredient(res.getInt("idIngredient"));
                resultListProductHasIngredients.add(newProductHasIngredient);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return resultListProductHasIngredients;
    }

    //DELETE
    public boolean removeProductHasIngredient(int idProduct, int idIngredient) {
        String sql = "DELETE FROM luanagon_laricao.Product_Has_Ingredient WHERE idProduct=? AND idIngredient=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idProduct);
            stmt.setInt(2, idIngredient);

            result = stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return result;
    }
}
