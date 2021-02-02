package br.com.brunoricardo.laricaodajo.dao;

import br.com.brunoricardo.laricaodajo.connection.ConnectionFactory;
import br.com.brunoricardo.laricaodajo.model.Employee;
import br.com.brunoricardo.laricaodajo.utility.Alerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    
Connection connection = null;
    
    public void openConnection() {
        connection = new ConnectionFactory().getConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
    }

    public boolean newEmployee(Employee newEmployee) {
        String sql = "INSERT INTO luanagon_laricao.Employee(name,mail,user,password,level,isactive) values(?,?,?,?,?,?)";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, newEmployee.getName());
            stmt.setString(2, newEmployee.getMail());
            stmt.setString(3, newEmployee.getUser());
            stmt.setString(4, newEmployee.getPassword());
            stmt.setInt(5, newEmployee.getLevel());
            stmt.setInt(6, newEmployee.getIsActive());

            stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return result;
    }
    
    public List<Employee> getList() {
        String sql = "SELECT * FROM luanagon_laricao.Employee";
        PreparedStatement stmt;
        ResultSet res;
        List<Employee> resultListEmployee = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Employee newEmployee = new Employee();
                newEmployee.setIdEmployee(res.getInt("idEmployee"));
                newEmployee.setName(res.getString("name"));
                newEmployee.setMail(res.getString("mail"));
                newEmployee.setUser(res.getString("user"));
                newEmployee.setPassword("");
                newEmployee.setLevel(res.getInt("level"));
                newEmployee.setIsActive(res.getInt("isactive"));
                resultListEmployee.add(newEmployee);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return resultListEmployee;
    }

    public List<Employee> searchEmployee(String search) {
        String sql = "SELECT * ROM luanagon_laricao.Employee where name like ?";
        PreparedStatement stmt;
        ResultSet res;
        List<Employee> listResult = new ArrayList<>();

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, search);
            res = stmt.executeQuery();
            while (res.next()) {
                Employee newEmployee = new Employee();
                newEmployee.setIdEmployee(res.getInt("idEmployee"));
                newEmployee.setName(res.getString("name"));
                newEmployee.setMail(res.getString("mail"));
                newEmployee.setUser(res.getString("user"));
                newEmployee.setPassword("");
                newEmployee.setLevel(res.getInt("level"));
                newEmployee.setIsActive(res.getInt("isactive"));
                listResult.add(newEmployee);
            }
            res.close();
            stmt.close();
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return listResult;
    }
    
    public Employee findLogin(String user, String password) {
        String sql = "SELECT * FROM luanagon_laricao.Employee WHERE user=? AND password=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, password);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    Employee newEmployee = new Employee();
                    newEmployee.setIdEmployee(res.getInt("idEmployee"));
                    newEmployee.setName(res.getString("name"));
                    newEmployee.setMail(res.getString("mail"));
                    newEmployee.setUser(res.getString("user"));
                    newEmployee.setPassword("");
                    newEmployee.setLevel(res.getInt("level"));
                    newEmployee.setIsActive(res.getInt("isactive"));
                    return newEmployee;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
            new Alerts("Efetuar Login", null, ex.toString()).WarningAlert();
        }
        return null;
    }

    public Employee findById(int idEmployee) {
        String sql = "SELECT * FROM luanagon_laricao.Employee WHERE idEmployee=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idEmployee);
            res = stmt.executeQuery();
            if (res != null) {
                while (res.next()) {
                    Employee newEmployee = new Employee();
                    newEmployee.setIdEmployee(res.getInt("idEmployee"));
                    newEmployee.setName(res.getString("name"));
                    newEmployee.setMail(res.getString("mail"));
                    newEmployee.setUser(res.getString("user"));
                    newEmployee.setPassword("");
                    newEmployee.setLevel(res.getInt("level"));
                    newEmployee.setIsActive(res.getInt("isactive"));
                    return newEmployee;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return null;
    }  

    public boolean validaUsuario(String usuario) {
        String sql = "SELECT * FROM luanagon_laricao.Employee WHERE user=?";
        PreparedStatement stmt;
        ResultSet res;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario);
            res = stmt.executeQuery();
            if (res.next()) {
                res.close();
                stmt.close();
                return true;
            } else {
                res.close();
                stmt.close();
                return false;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return true;
    }

    //UPDATE
    public boolean atualizaFuncionario(Employee newEmployee) {
        String sql = "UPDATE luanagon_laricao.Employee SET name=?, mail=?, user=?, password=?, level=?, isactive=? WHERE idEmployee=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, newEmployee.getName());
            stmt.setString(2, newEmployee.getMail());
            stmt.setString(3, newEmployee.getUser());
            stmt.setString(4, newEmployee.getPassword());
            stmt.setInt(5, newEmployee.getLevel());
            stmt.setInt(6, newEmployee.getIsActive());
            stmt.setInt(7, newEmployee.getIdEmployee());

            stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return result;
    }

    //DELETE
    public boolean removeEmployee(Employee newEmployee) {
        String sql = "DELETE FROM luanagon_laricao.Employee WHERE idEmployee=?";
        PreparedStatement stmt;
        boolean result = false;

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newEmployee.getIdEmployee());

            stmt.execute();
            stmt.close();
            result = true;
        } catch (SQLException ex) {
            //Logger.getLogger(EmployeeDao.class).debug(ex);
        }
        return result;
    }
}