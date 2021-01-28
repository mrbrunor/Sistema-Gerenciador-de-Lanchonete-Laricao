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
public class OrderHasProductsTV {
    
    IntegerProperty code;
    StringProperty description;
    DoubleProperty unitaryValue;
    IntegerProperty amount;
    DoubleProperty total;

    OrderHasProducts orderHasProducts;

    public OrderHasProductsTV(OrderHasProducts orderHasProducts) {
        this.orderHasProducts = orderHasProducts;

        code = new SimpleIntegerProperty();
        code.set(orderHasProducts.getProduct().getNumber());

        description = new SimpleStringProperty();
        description.set(orderHasProducts.getProduct().getDescription());

        unitaryValue = new SimpleDoubleProperty();
        unitaryValue.set(orderHasProducts.getProduct().getValue());

        amount = new SimpleIntegerProperty();
        amount.set(orderHasProducts.getAmount());

        total = new SimpleDoubleProperty();
        total.set(orderHasProducts.getTotal());
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public DoubleProperty unitaryValueProperty() {
        return unitaryValue;
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public OrderHasProducts getOrderHasProducts() {
        return orderHasProducts;
    }    
}
