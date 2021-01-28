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
public class Product {
        
    private int idProduct;
    private int number;
    private String description;
    private double value;
    private String barcode;
    private int isIndustrialized;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getIsIndustrialized() {
        return isIndustrialized;
    }

    public void setIsIndustrialized(int isIndustrialized) {
        this.isIndustrialized = isIndustrialized;
    }        
}
