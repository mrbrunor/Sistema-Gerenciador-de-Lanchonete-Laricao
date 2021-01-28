/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;

/**
 *
 * @author mrbru
 */
public class Orderer {
    
    private int idOrder;
    private int number;
    private Timestamp date;
    private int idBox;
    private double subtotal;
    private double discount;
    private double total;
    private double amountReceived;
    private int idFormOfPayment;
    private String status;    
    private String formOfConsumption;    
    private ObservableList<OrderHasProductsTV> ordererItems;
    private FormOfPayment formOfPayment = new FormOfPayment();

    public Orderer() {
    }

    public Orderer(ObservableList<OrderHasProductsTV> ordererItems) {
        this.ordererItems = ordererItems;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getIdBox() {
        return idBox;
    }

    public void setIdBox(int idBox) {
        this.idBox = idBox;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdFormOfPayment() {
        return idFormOfPayment;
    }

    public void setIdFormOfPayment(int idFormOfPayment) {
        this.idFormOfPayment = idFormOfPayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObservableList<OrderHasProductsTV> getOrdererItems() {
        return ordererItems;
    }

    public void setOrdererItems(ObservableList<OrderHasProductsTV> ordererItems) {
        this.ordererItems = ordererItems;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getFormOfConsumption() {
        return formOfConsumption;
    }

    public void setFormOfConsumption(String formOfConsumption) {
        this.formOfConsumption = formOfConsumption;
    }
    
    
}
