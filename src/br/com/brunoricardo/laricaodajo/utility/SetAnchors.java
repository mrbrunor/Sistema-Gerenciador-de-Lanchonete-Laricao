/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoricardo.laricaodajo.utility;

import javafx.scene.layout.AnchorPane;

public class SetAnchors {

    public SetAnchors(AnchorPane ap) {
        AnchorPane.setTopAnchor(ap, Double.valueOf(0));
        AnchorPane.setBottomAnchor(ap, Double.valueOf(0));
        AnchorPane.setLeftAnchor(ap, Double.valueOf(0));
        AnchorPane.setRightAnchor(ap, Double.valueOf(0));
    }

}
