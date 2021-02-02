package br.com.brunoricardo.laricaodajo.controller;

import br.com.brunoricardo.laricaodajo.model.OrderHasProductsTV;
import br.com.brunoricardo.laricaodajo.model.Orderer;
import br.com.brunoricardo.laricaodajo.utility.SetAnchors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    @FXML
    private AnchorPane checkout;

    @FXML
    private TableView<OrderHasProductsTV> tvOrderer;

    @FXML
    private TableColumn<?, ?> tcCode;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TableColumn<?, ?> tcValue;

    @FXML
    private TableColumn<?, ?> tcAmount;

    @FXML
    private TableColumn<?, ?> tcTotal;

    @FXML
    private TextField tfDiscount;

    @FXML
    private Label lbTotal;

    @FXML
    private RadioButton rbMoney;

    @FXML
    private ToggleGroup fp;

    @FXML
    private RadioButton rbCredit;

    @FXML
    private RadioButton rbDebit;

    @FXML
    private RadioButton rbTcket;

    @FXML
    private TextField tfAmountReceived;

    @FXML
    private ComboBox<?> cbCredit;

    @FXML
    private ComboBox<?> cbDebit;

    @FXML
    private ComboBox<?> cbTicket;

    @FXML
    private RadioButton rbBalcony;

    @FXML
    private ToggleGroup fc;

    @FXML
    private RadioButton rbTable;

    @FXML
    private TextField tfTable;

    @FXML
    private RadioButton rbTravel;

    @FXML
    private Button btCancel;

    @FXML
    private Button btConfirm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SetAnchors(checkout);
        MainViewController.isPaid = false;
        
        //Colunas Tabela Pedido
        tcCode.setCellValueFactory(new PropertyValueFactory("cod"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("descricao"));
        tcValue.setCellValueFactory(new PropertyValueFactory("valUnitario"));
        tcAmount.setCellValueFactory(new PropertyValueFactory("quantidade"));
        tcTotal.setCellValueFactory(new PropertyValueFactory("valTotal"));
        
        tvOrderer.setItems(MainViewController.orderer.getOrdererItems());
        
    }
    
    static void setIsPaid(boolean isPaid) {
        MainViewController.isPaid = isPaid;
    }
    
    public static void setOrderer(Orderer orderer){
        MainViewController.orderer = orderer;
    }

}
