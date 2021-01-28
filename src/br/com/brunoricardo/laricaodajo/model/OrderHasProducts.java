/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.model;

/**
 *
 * @author mrbru
 */
public class OrderHasProducts {
    
    private int idOrderer;
    private int idProduct;
    private int amount;
    private double total;
    private int orderNumber;
    private String name;
    private Product product;

    public OrderHasProducts() {
        this.name = "null";
    }

    public int getIdOrderer() {
        return idOrderer;
    }

    public void setIdOrderer(int idOrderer) {
        this.idOrderer = idOrderer;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int order) {
        this.orderNumber = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }      
}
