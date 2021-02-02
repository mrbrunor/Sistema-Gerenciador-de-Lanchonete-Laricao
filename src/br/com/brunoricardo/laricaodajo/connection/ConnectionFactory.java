/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mrbru
 */
public class ConnectionFactory {

    File resourcesDirectory;
    String user = "admin";
    String password = "27639932xD@";
    String url;
    Connection ready = null;

    /**
     *
     * @return Função para criar uma conexão e retorna-la
     */
    public Connection getConnection() {

        resourcesDirectory = new File("src/br/com/brunoricardo/laricaodajo/ressources/database");
        url = "jdbc:h2:" + resourcesDirectory.getAbsolutePath() + "\\database;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
        System.out.println(url);

        try {
            ready = DriverManager.getConnection(url, user, password);
            return ready;
        } catch (SQLException fail) {
            System.out.println("Impossivel Conectar: " + fail);
            return ready;
        }
    }
}