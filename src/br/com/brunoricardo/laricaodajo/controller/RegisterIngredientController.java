package br.com.brunoricardo.laricaodajo.controller;

import br.com.brunoricardo.laricaodajo.dao.IngredientDao;
import br.com.brunoricardo.laricaodajo.model.Ingredient;
import br.com.brunoricardo.laricaodajo.model.IngredientTV;
import br.com.brunoricardo.laricaodajo.utility.Alerts;
import br.com.brunoricardo.laricaodajo.utility.Validation;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterIngredientController implements Initializable {

    @FXML
    private TextField tfName;

    @FXML
    private Label lbEName;

    @FXML
    private TextField tfId;

    @FXML
    private TableView<IngredientTV> tvIngredients;

    @FXML
    private TableColumn<?, ?> tcCode;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btSearch;

    @FXML
    private Button btClean;

    @FXML
    private Button btRemove;

    @FXML
    private Button btUpdate;

    @FXML
    private Button btCancel;

    @FXML
    private Button btRegister;


    private IngredientDao ingredientDao = new IngredientDao();
    private ObservableList<IngredientTV> ingredientTVS;
    private FilteredList<IngredientTV> filteredIngredients;
    private boolean result[] = {false};
    private final Validation validation = new Validation("-fx-border-color: rgb(181, 181, 181);",
            "-fx-border-color: white;",
            "-fx-background-color: white; -fx-border-color: rgb(181, 181, 181);",
            "-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%); -fx-border-color: white;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lbEName.setVisible(false);
        tfId.setText(null);

        initializeTableModel();
        enableSaveButtons();

        tfName.setOnAction(event -> validation.validateText(tfName, lbEName, 3));
        tfSearch.setOnKeyReleased(event -> search());

        btSearch.setOnAction(event -> search());
        btClean.setOnAction(event -> cleanFields());
        btCancel.setOnAction(event -> close());
        btRegister.setOnAction(e -> {if (validate()) register();});
        btUpdate.setOnAction(e -> {if (validate()) update();});
        btRemove.setOnAction(e -> {if (validate()) remove();});

        tvIngredients.setOnMouseClicked(event -> ingredientToForm());

        tfName.requestFocus();
    }

    private void close() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    private void enableSaveButtons() {
        enableOrDisableEditButtons(true);
    }

    private void disableSaveButtons() {
        enableOrDisableEditButtons(false);
    }

    private void enableOrDisableEditButtons(boolean enabled) {
        btRegister.setDisable(!enabled);
        btUpdate.setDisable(enabled);
        btRemove.setDisable(enabled);
    }

    private Ingredient formToIngredient() {
        Ingredient ingredient = new Ingredient();
        if (tfId.getText() != null) {
            ingredient.setIdIngredient(Integer.parseInt(tfId.getText()));
        }
        ingredient.setDescription(tfName.getText());
        return ingredient;
    }

    private void ingredientToForm() {
        if (tvIngredients.getSelectionModel().getSelectedIndex() != -1) {
            IngredientTV ingredient = tvIngredients.getSelectionModel().getSelectedItem();
            tfId.setText(String.valueOf(ingredient.getIngredient().getIdIngredient()));
            tfName.setText(ingredient.getIngredient().getDescription());
            disableSaveButtons();
            validate();
            tfName.requestFocus();
        }
    }

    private void register() {
        ingredientDao.openConnection();
        Ingredient ingredient = formToIngredient();
        if (ingredientDao.validateName(ingredient.getDescription())) {
            new Alerts("Cadastro de Ingredientes", null, "Um ingrediente com este nome já foi cadastrado!").ErrorAlert();
            ingredientDao.closeConnection();
            return;
        }
        ingredientDao.newIngredient(ingredient);
        ingredientDao.closeConnection();
        new Alerts("Cadastro de Ingredientes", null, "Ingrediente cadastrado com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void update() {
        ingredientDao.openConnection();
        Ingredient ingredient = formToIngredient();
        ingredientDao.updateIngredient(ingredient);
        ingredientDao.closeConnection();
        new Alerts("Cadastro de Ingredientes", null, "Ingrediente atualizado com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void remove() {
        ingredientDao.openConnection();
        Ingredient ingredient = formToIngredient();
        ingredientDao.removeIngredient(ingredient);
        ingredientDao.closeConnection();
        new Alerts("Cadastro de Ingredientes", null, "Ingrediente removido com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void search() {
        filteredIngredients.setPredicate(c -> c.filter(tfSearch.getText()));
    }

    private void initializeTableModel() {
        ingredientDao.openConnection();
        List<Ingredient> listIngredients = ingredientDao.getList();
        ingredientDao.closeConnection();

        ingredientTVS = FXCollections.observableArrayList();
        listIngredients.forEach(c -> ingredientTVS.add(new IngredientTV(c)));

        filteredIngredients = new FilteredList<>(ingredientTVS);
        filteredIngredients.setPredicate(p -> true);

        tcCode.setCellValueFactory(new PropertyValueFactory("code"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("description"));

        tvIngredients.setItems(filteredIngredients);
    }

    public void updateTableModel(List<Ingredient> listIngredients) {
        if (listIngredients != null && listIngredients.isEmpty()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setDescription("Nenhum Registro Encontrado");
            listIngredients.add(ingredient);
        }

        ingredientTVS = FXCollections.observableArrayList();
        listIngredients.forEach(c -> ingredientTVS.add(new IngredientTV(c)));

        filteredIngredients = new FilteredList<>(ingredientTVS);
        filteredIngredients.setPredicate(p -> true);

        tcCode.setCellValueFactory(new PropertyValueFactory("code"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("description"));

        tvIngredients.setItems(filteredIngredients);
    }

    public void cleanFields() {
        tfId.setText(null);
        tfName.setText("");
        lbEName.setVisible(false);
        enableSaveButtons();
    }

    private boolean validate() {
        result[0] = validation.validateText(tfName, lbEName, 3);
        return result[0];
    }
}




