package br.com.brunoricardo.laricaodajo.dao;

import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao {
    
    Connection connection = null;

    public void openConnection() {
        connection = new ConnectionFactory().getConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
    }

    public int newIngredient(Ingredient newIngredient) {
        String sql = "INSERT INTO luanagon_laricao.Ingredient(description) values(?)";
        PreparedStatement stmt;
        int result = 0;

        try {
            stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newIngredient.getDescription());

            stmt.execute();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = (int) generatedKeys.getLong(1);
                } else {
                    //Logger.getLogger(IngredientDao.class).debug(ex);
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return result;
    }

    public List<Ingredient> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Ingredient ORDER BY description";
        PreparedStatement stmt;
        ResultSet res;
        List<Ingredient> resultListIngredient = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIdIngredient(res.getInt("idIngredient"));
                newIngredient.setDescription(res.getString("description"));
                resultListIngredient.add(newIngredient);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return resultListIngredient;
    }

    public List<Ingredient> getListFromProduct(int idProduct) {
        String sql = "SELECT * FROM luanagon_laricao.Ingredient WHERE idProduct=? ORDER BY description";
        PreparedStatement stmt;
        ResultSet res;
        List<Ingredient> resultListIngredient = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, String.valueOf(idProduct));
            res = stmt.executeQuery();
            while (res.next()) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIdIngredient(res.getInt("idIngredient"));
                newIngredient.setDescription(res.getString("description"));
                resultListIngredient.add(newIngredient);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return resultListIngredient;
    }

    public List<Ingredient> search(String search) {
        String sql = "SELECT * FROM luanagon_laricao.Ingredient WHERE description LIKE ? OR idIngredient LIKE ?";
        PreparedStatement stmt;
        ResultSet res;
        List<Ingredient> resultListIngredient = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, search);
            stmt.setString(2, search);
            res = stmt.executeQuery();
            while (res.next()) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIdIngredient(res.getInt("idIngredient"));
                newIngredient.setDescription(res.getString("description"));
                resultListIngredient.add(newIngredient);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return resultListIngredient;
    }



    public Ingredient findId(int idIngredient) {
        String sql = "SELECT * FROM luanagon_laricao.Ingredient WHERE idIngredient=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idIngredient);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setIdIngredient(res.getInt("idIngredient"));
                    newIngredient.setDescription(res.getString("description"));
                    return newIngredient;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return null;
    }

    public boolean validateName(String description) {
        String sql = "SELECT * FROM luanagon_laricao.Ingredient WHERE description=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, description);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return false;
    }

    //UPDATE
    public boolean updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE luanagon_laricao.Ingredient SET description=? WHERE idIngredient=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ingredient.getDescription());
            stmt.setInt(2, ingredient.getIdIngredient());
            result = stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return result;
    }

    //DELETE
    public boolean removeIngredient(Ingredient ingredient) {
        String sql = "DELETE FROM luanagon_laricao.Ingredient WHERE idIngredient=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ingredient.getIdIngredient());

            result = stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(IngredientDao.class).debug(ex);
        }
        return result;
    }
}
