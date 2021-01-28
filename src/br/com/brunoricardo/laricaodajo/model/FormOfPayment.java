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
public class FormOfPayment {
    
    private int idFormOfPayment;
    private String name;
    private String type;
    private int isActive;

    public int getIdFormOfPayment() {
        return idFormOfPayment;
    }

    public void setIdFormOfPayment(int idFormOfPayment) {
        this.idFormOfPayment = idFormOfPayment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
    
    
}
