package br.com.brunoricardo.laricaodajo.dao;

import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    
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

    public int newProduct(Product newProduct) {
        String sql = "INSERT INTO luanagon_laricao.Product(number,description,value,barcode,isIndustrialized) values(?,?,?,?,?)";
        PreparedStatement stmt;
        int result = 0;

        try {
            stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, newProduct.getNumber());
            stmt.setString(2, newProduct.getDescription());
            stmt.setDouble(3, newProduct.getValue());
            stmt.setString(4, newProduct.getBarcode());
            stmt.setInt(5, newProduct.getIsIndustrialized());

            stmt.execute();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = (int) generatedKeys.getLong(1);
                } else {
                    //Logger.getLogger(ProductDao.class).debug(ex);
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return result;
    }

    public List<Product> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Product ORDER BY number";
        PreparedStatement stmt;
        ResultSet res;
        List<Product> resultListProduct = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Product newProduct = new Product();
                newProduct.setIdProduct(res.getInt("idProduct"));
                newProduct.setNumber(res.getInt("number"));
                newProduct.setDescription(res.getString("description"));
                newProduct.setValue(res.getDouble("value"));
                newProduct.setBarcode(res.getString("barcode"));
                newProduct.setIsIndustrialized(res.getInt("isIndustrialized"));
                resultListProduct.add(newProduct);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return resultListProduct;
    }
    
    public List<Product> productSearch(String search) {
        String sql = "SELECT * FROM luanagon_laricao.Product WHERE description LIKE ? OR number LIKE ?";
        PreparedStatement stmt;
        ResultSet res;
        List<Product> resultListProduct = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, search);
            stmt.setString(2, search);
            res = stmt.executeQuery();
            while (res.next()) {
                Product newProduct = new Product();
                newProduct.setIdProduct(res.getInt("idProduct"));
                newProduct.setNumber(res.getInt("number"));
                newProduct.setDescription(res.getString("description"));
                newProduct.setValue(res.getDouble("value"));
                newProduct.setBarcode(res.getString("barcode"));
                newProduct.setIsIndustrialized(res.getInt("isIndustrialized"));
                resultListProduct.add(newProduct);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return resultListProduct;
    }

    public Product findNumber(int number) {
        String sql = "SELECT * FROM luanagon_laricao.Product WHERE number=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, number);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    Product newProduct = new Product();
                    newProduct.setIdProduct(res.getInt("idProduct"));
                    newProduct.setNumber(res.getInt("number"));
                    newProduct.setDescription(res.getString("description"));
                    newProduct.setValue(res.getDouble("value"));
                    newProduct.setBarcode(res.getString("barcode"));
                    newProduct.setIsIndustrialized(res.getInt("isIndustrialized"));
                    return newProduct;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return null;
    }

    public Product findId(int idProduct) {
        String sql = "SELECT * FROM luanagon_laricao.Product WHERE idProduct=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idProduct);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    Product newProduct = new Product();
                    newProduct.setIdProduct(res.getInt("idProduct"));
                    newProduct.setNumber(res.getInt("number"));
                    newProduct.setDescription(res.getString("description"));
                    newProduct.setValue(res.getDouble("value"));
                    newProduct.setBarcode(res.getString("barcode"));
                    newProduct.setIsIndustrialized(res.getInt("isIndustrialized"));
                    return newProduct;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return null;
    }

    public boolean validateName(String description) {
        String sql = "SELECT * FROM luanagon_laricao.Product WHERE description=?";
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
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return false;
    }

    public boolean validateNumber(int number) {
        String sql = "SELECT * FROM luanagon_laricao.Product WHERE number=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, number);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return false;
    }

    //UPDATE
    public boolean updateProduct(Product produto) {
        String sql = "UPDATE luanagon_laricao.Product SET number=?, description=?, value=?, barcode=?, isIndustrialized=? WHERE idProduct=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getNumber());
            stmt.setString(2, produto.getDescription());
            stmt.setDouble(3, produto.getValue());
            stmt.setString(4, produto.getBarcode());
            stmt.setInt(5, produto.getIsIndustrialized());
            stmt.setInt(6, produto.getIdProduct());
            result = stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return result;
    }

    //DELETE
    public boolean removeProduct(Product produto) {
        String sql = "DELETE FROM luanagon_laricao.Product WHERE idProduct=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getIdProduct());

            result = stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ProductDao.class).debug(ex);
        }
        return result;
    }
}
