/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mrbru
 */
public class ProductTV {
    
    IntegerProperty code;
    StringProperty description;
    DoubleProperty value;
    
    Product product;
    
    public ProductTV(Product product) {
        this.product = product;
        
        code = new SimpleIntegerProperty();
        code.set(product.getNumber());
        
        description = new SimpleStringProperty();
        description.set(product.getDescription());
        
        value = new SimpleDoubleProperty();
        value.set(product.getValue());
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public Product getProduct() {
        return product;
    }
    
     public boolean filter(String text) {

        String data = code.get() + description.get();

        if (data.toUpperCase().contains(text.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }
}