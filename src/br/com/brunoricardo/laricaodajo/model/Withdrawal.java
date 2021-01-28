/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.model;

import java.time.LocalDateTime;

/**
 *
 * @author mrbru
 */
public class Withdrawal {
    
    private int idWithdrawal;
    private LocalDateTime date;
    private String description;
    private double value;
    private int idBox;

    public int getIdWithdrawal() {
        return idWithdrawal;
    }

    public void setIdWithdrawal(int idWithdrawal) {
        this.idWithdrawal = idWithdrawal;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public int getIdBox() {
        return idBox;
    }

    public void setIdBox(int idBox) {
        this.idBox = idBox;
    }
    
    
}
