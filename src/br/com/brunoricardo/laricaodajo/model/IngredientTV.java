/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.model;

import javafx.beans.property.*;

public class IngredientTV {

    IntegerProperty code;
    StringProperty description;

    Ingredient ingredient;

    public IngredientTV(Ingredient ingredient) {
        this.ingredient = ingredient;
        
        code = new SimpleIntegerProperty();
        code.set(ingredient.getIdIngredient());
        
        description = new SimpleStringProperty();
        description.set(ingredient.getDescription());

    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Ingredient getIngredient() {
        return ingredient;
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