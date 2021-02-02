package br.com.brunoricardo.laricaodajo.dao;

import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.FormOfPayment;
import br.com.brunoricardo.laricaodajo.model.Product;
import br.com.brunoricardo.laricaodajo.model.Orderer;
import br.com.brunoricardo.laricaodajo.model.OrderHasProducts;
import br.com.brunoricardo.laricaodajo.model.OrderHasProductsTV;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdererDao {
    Connection connection = null;
    
    public void openConnection() {
        connection = new ConnectionFactory().getConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
    }

    public int newOrderer(Orderer newOrderer) {
        String sql = "INSERT INTO luanagon_laricao.Orderer(number, date, idBox, subtotal, discount, total, amountReceived, idFormOfPayment, status, formOfConsumption) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt;
        int result = 0;

        try {
            stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, newOrderer.getNumber());
            stmt.setTimestamp(2, newOrderer.getDate());            
            stmt.setInt(3, newOrderer.getIdBox());
            stmt.setDouble(4, newOrderer.getSubtotal());
            stmt.setDouble(5, newOrderer.getDiscount());
            stmt.setDouble(6, newOrderer.getTotal());
            stmt.setDouble(7, newOrderer.getAmountReceived());
            stmt.setInt(8, newOrderer.getIdFormOfPayment());
            stmt.setString(9, newOrderer.getStatus());
            stmt.setString(10, newOrderer.getFormOfConsumption());

            stmt.execute();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = (int) generatedKeys.getLong(1);
                } else {
                    //Logger.getLogger(OrdererDao.class).debug("Falha, id não obtido");
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return result;
    }

    //READ
    public List<Orderer> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Orderer";
        PreparedStatement stmt;
        ResultSet res;
        List<Orderer> resultOrdererList = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Orderer orderer = new Orderer();
                orderer.setIdOrder(res.getInt("idOrderer"));
                orderer.setNumber(res.getInt("number"));
                orderer.setDate(res.getTimestamp("date"));                
                orderer.setIdBox(res.getInt("idBox"));
                orderer.setSubtotal(res.getDouble("subtotal"));
                orderer.setDiscount(res.getDouble("discount"));
                orderer.setTotal(res.getDouble("total"));
                orderer.setAmountReceived(res.getDouble("amountReceived"));
                orderer.setIdFormOfPayment(res.getInt("idFormOfPayment"));
                orderer.setStatus(res.getString("status"));
                orderer.setFormOfConsumption(res.getString("formOfConsumption"));
                resultOrdererList.add(orderer);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return resultOrdererList;
    }

    public Orderer findOrdererById(int idOrderer) {
        String sql = "SELECT * FROM luanagon_laricao.pedido as p JOIN luanagon_laricao.formapagamento as fp on p.idFormOfPayment = fp.idFormOfPayment WHERE p.idOrderer=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idOrderer);
            res = stmt.executeQuery();
            while (res.next()) {
                Orderer orderer = new Orderer();
                orderer.setIdOrder(res.getInt("idOrderer"));
                orderer.setNumber(res.getInt("number"));
                orderer.setDate(res.getTimestamp("date"));
                
                orderer.setIdBox(res.getInt("idBox"));
                orderer.setSubtotal(res.getDouble("subtotal"));
                orderer.setDiscount(res.getDouble("discount"));
                orderer.setTotal(res.getDouble("total"));
                orderer.setAmountReceived(res.getDouble("amountReceived"));
                orderer.setIdFormOfPayment(res.getInt("idFormOfPayment"));
                orderer.setStatus(res.getString("status"));
                orderer.setFormOfConsumption(res.getString("formOfConsumption"));

                FormOfPayment formOfPayment = new FormOfPayment();
                formOfPayment.setIdFormOfPayment(res.getInt("idFormOfPayment"));
                formOfPayment.setName(res.getString("name"));
                formOfPayment.setType(res.getString("type"));
                formOfPayment.setIsActive(res.getByte("isActive"));

                orderer.setFormOfPayment(formOfPayment);

                return orderer;
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return null;
    }

    public Integer findLastOrderer(int idBox) {
        Integer last = null;
        String sql = "select MAX(number) as lastOrderer from SISTEMAPASTELAO.PEDIDO WHERE idBox=?";
        PreparedStatement stmt;
        ResultSet res;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idBox);
            res = stmt.executeQuery();
            if (res.next()) {
                last = res.getInt("lastOrderer");
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return last;
    }

    public List<Orderer> listOrdererByBox(int idBox) {
        String sql = "SELECT * FROM luanagon_laricao.Orderer WHERE idBox=?";
        PreparedStatement stmt;
        ResultSet res;
        List<Orderer> resultOrdererList = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idBox);
            res = stmt.executeQuery();
            while (res.next()) {
                Orderer orderer = new Orderer();
                orderer.setIdOrder(res.getInt("idOrderer"));
                orderer.setNumber(res.getInt("number"));
                orderer.setDate(res.getTimestamp("date"));                
                orderer.setIdBox(res.getInt("idBox"));
                orderer.setSubtotal(res.getDouble("subtotal"));
                orderer.setDiscount(res.getDouble("discount"));
                orderer.setTotal(res.getDouble("total"));
                orderer.setAmountReceived(res.getDouble("amountReceived"));
                orderer.setIdFormOfPayment(res.getInt("idFormOfPayment"));
                orderer.setStatus(res.getString("status"));
                orderer.setFormOfConsumption(res.getString("formOfConsumption"));
                resultOrdererList.add(orderer);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return resultOrdererList;
    }

    public Orderer listOrdererItems(Orderer orderer) {
        String sql = "SELECT * FROM luanagon_laricao.OrderHasProducts as op JOIN luanagon_laricao.product as product on op.idProduct = product.idProduct WHERE op.idOrderer=? ORDER BY op.orderNumber";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orderer.getIdOrder());
            res = stmt.executeQuery();
            while (res.next()) {
                OrderHasProducts orderHasProducts = new OrderHasProducts();
                 orderHasProducts.setIdOrderer(res.getInt("idOrderer"));
                 orderHasProducts.setIdProduct(res.getInt("idProduct"));
                 orderHasProducts.setAmount(res.getInt("amount"));
                 orderHasProducts.setTotal(res.getDouble("total"));
                 orderHasProducts.setOrderNumber(res.getInt("orderNumber"));
                 orderHasProducts.setName(res.getString("name"));

                Product product = new Product();
                product.setIdProduct(res.getInt("idProduct"));
                product.setNumber(res.getInt("number"));
                product.setDescription(res.getString("description"));
                product.setValue(res.getDouble("value"));
                product.setBarcode(res.getString("barcode"));
                product.setIsIndustrialized(res.getByte("isIndustrialized"));

                orderHasProducts.setProduct(product);
                orderer.getOrdererItems().add(new OrderHasProductsTV(orderHasProducts));
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return orderer;
    }

    //UPDATE
    public boolean atualizaOrderer(Orderer newOrderer) {
        String sql = "UPDATE luanagon_laricao.Orderer SET number=?, date=?, idBox=?, subtotal=?, discount=?, total=?, amountReceived=?, idFormOfPayment=?, status=?, formOfConsumption=? WHERE idOrderer=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newOrderer.getNumber());
            stmt.setTimestamp(2, newOrderer.getDate());
            
            stmt.setInt(3, newOrderer.getIdBox());
            stmt.setDouble(4, newOrderer.getSubtotal());
            stmt.setDouble(5, newOrderer.getDiscount());
            stmt.setDouble(6, newOrderer.getTotal());
            stmt.setDouble(7, newOrderer.getAmountReceived());
            stmt.setInt(8, newOrderer.getIdFormOfPayment());
            stmt.setString(9, newOrderer.getStatus());
            stmt.setString(10, newOrderer.getFormOfConsumption());
            stmt.setInt(12, newOrderer.getIdOrder());

            stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return result;
    }

    //DELETE
    public boolean deletaOrderer(Orderer newOrderer) {
        String sql = "DELETE FROM luanagon_laricao.Orderer WHERE idOrderer=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newOrderer.getIdOrder());

            stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(OrdererDao.class).debug(ex);
        }
        return result;
    }
}
