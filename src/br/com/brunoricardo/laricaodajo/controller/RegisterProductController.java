package br.com.brunoricardo.laricaodajo.controller;

import br.com.brunoricardo.laricaodajo.dao.IngredientDao;
import br.com.brunoricardo.laricaodajo.dao.ProductDao;
import br.com.brunoricardo.laricaodajo.dao.ProductHasIngredientDao;
import br.com.brunoricardo.laricaodajo.model.Ingredient;
import br.com.brunoricardo.laricaodajo.model.IngredientTV;
import br.com.brunoricardo.laricaodajo.model.Product;
import br.com.brunoricardo.laricaodajo.model.ProductTV;
import br.com.brunoricardo.laricaodajo.utility.Alerts;
import br.com.brunoricardo.laricaodajo.utility.Dialogues;
import br.com.brunoricardo.laricaodajo.utility.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterProductController implements Initializable {

    @FXML
    private TextField tfCode;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfValue;

    @FXML
    private Label lbECode;

    @FXML
    private Label lbEName;

    @FXML
    private Label lbEValue;

    @FXML
    private Label lbEType;

    @FXML
    private RadioButton rbIndustrialized;

    @FXML
    private RadioButton rbPrepared;

    @FXML
    private TableView<IngredientTV> tvIngredients;

    @FXML
    private TableColumn<?, ?> tcIngredientsCode;

    @FXML
    private TableColumn<?, ?> tcIngredientsDescription;

    @FXML
    private TextField tfEan;

    @FXML
    private Label lbEEan;

    @FXML
    private TextField tfId;

    @FXML
    private ComboBox<Ingredient> cbIngredient;

    @FXML
    private Button btRemoveIngredient;

    @FXML
    private Label lbIngredient;

    @FXML
    private Label lbEan;

    @FXML
    private Button btAddIngredient;

    @FXML
    private Label lbEIngredient;

    @FXML
    private TableView<ProductTV> tvProducts;

    @FXML
    private TableColumn<?, ?> tcCode;

    @FXML
    private TableColumn<?, ?> tcDescription;

    @FXML
    private TableColumn<?, ?> tcValue;

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

    private ProductDao productDao = new ProductDao();
    private IngredientDao ingredientDao = new IngredientDao();
    private List<Ingredient> listIngredients;
    private ObservableList<ProductTV> productTVS;
    private ObservableList<IngredientTV> ingredientTVS;
    private FilteredList<ProductTV> filteredInProductTVS;
    private boolean result[] = {false, false, false, false, false, false};
    private final Validation validation = new Validation("-fx-border-color: rgb(181, 181, 181);",
            "-fx-border-color: white;",
            "-fx-background-color: white; -fx-border-color: rgb(181, 181, 181);",
            "-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%); -fx-border-color: white;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lbEName.setVisible(false);
        lbECode.setVisible(false);
        lbEEan.setVisible(false);
        lbEType.setVisible(false);
        lbEValue.setVisible(false);

        tfId.setText(null);

        initializeTableModel();
        initializeCombobox();
        enableSaveButtons();

        tfName.setOnAction(event -> validation.validateText(tfName, lbEName, 2));
        tfName.setOnKeyReleased(event -> validation.validateText(tfName, lbEName, 2));
        tfCode.setOnAction(event -> validation.validateText(tfCode, lbECode, 1));
        tfCode.setOnKeyReleased(event -> validation.validateText(tfCode, lbECode, 1));
        tfEan.setOnAction(event -> validation.validateText(tfEan, lbEEan, 1));
        tfEan.setOnKeyReleased(event -> validation.validateText(tfEan, lbEEan, 1));
        tfValue.setOnAction(event -> validation.validateText(tfValue, lbEValue, 1));
        tfValue.setOnKeyReleased(event -> validation.validateText(tfValue, lbEValue, 1));
        tfSearch.setOnKeyReleased(event -> search());

        btSearch.setOnAction(event -> search());
        btClean.setOnAction(event -> cleanFields());
        btCancel.setOnAction(event -> close());
        btRegister.setOnAction(e -> {
            if (validate()) register();
        });
        btUpdate.setOnAction(e -> {
            if (validate()) update();
        });
        btRemove.setOnAction(e -> {
            if (validate()) remove();
        });
        btAddIngredient.setOnAction(e -> addIngredient());
        btRemoveIngredient.setOnAction(e -> removeIngredient());

        rbIndustrialized.setOnAction(e -> {
            rbPrepared.setSelected(false);
            enableIndustrialized();
        });
        rbPrepared.setOnAction(e -> {
            rbIndustrialized.setSelected(false);
            disableIndustrialized();
        });

        cbIngredient.setOnAction(e -> {
            if (validation.validateCb(cbIngredient)) {
                btAddIngredient.setDisable(false);
            } else {
                btAddIngredient.setDisable(true);
            }
        });

        tvIngredients.setOnMouseClicked(e -> btRemoveIngredient.setDisable(false));
        tvProducts.setOnMouseClicked(event -> productToForm());

        listIngredients = new ArrayList();

        tfCode.requestFocus();
    }

    private void addIngredient() {
        if (cbIngredient.getSelectionModel().getSelectedIndex() != -1) {
            Ingredient addIngredient = cbIngredient.getSelectionModel().getSelectedItem();
            boolean alreadyIncluded = false;
            for (Ingredient c : listIngredients) {
                if (c.getIdIngredient() == addIngredient.getIdIngredient()){
                    alreadyIncluded = true;
                }
            }
            if (!alreadyIncluded) {
                listIngredients.add(addIngredient);
                lbEIngredient.setVisible(false);
                initializeIngredientTableModel(listIngredients);
            } else {
                new Alerts("Cadastro de Produtos", null, "O ingrediente selecionado ja foi adicionado!").ErrorAlert();
            }
            cbIngredient.getSelectionModel().select(-1);
        }
    }

    private void close() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }

    private void disableIndustrialized() {
        enableOrDisablePrepared(true);
    }

    private void disableSaveButtons() {
        enableOrDisableEditButtons(false);
    }

    private void enableOrDisableEditButtons(boolean enabled) {
        btRegister.setDisable(!enabled);
        btUpdate.setDisable(enabled);
        btRemove.setDisable(enabled);
    }

    private void enableOrDisablePrepared(boolean enabled) {
        lbIngredient.setVisible(enabled);
        cbIngredient.setVisible(enabled);
        btAddIngredient.setVisible(enabled);
        btRemoveIngredient.setVisible(enabled);
        tvIngredients.setVisible(enabled);
        lbEan.setVisible(!enabled);
        tfEan.setVisible(!enabled);
        lbEType.setVisible(false);
        if (enabled) {
            btAddIngredient.setDisable(true);
            btRemove.setDisable(true);
            cbIngredient.getSelectionModel().clearSelection();
        }
    }

    private void enableIndustrialized() {
        enableOrDisablePrepared(false);
    }

    private void enableSaveButtons() {
        enableOrDisableEditButtons(true);
    }

    private Product formToProduct() {
        Product product = new Product();
        if (tfId.getText() != null) {
            product.setIdProduct(Integer.parseInt(tfId.getText()));
        }
        product.setNumber(Integer.parseInt(tfCode.getText()));
        product.setDescription(tfName.getText());
        product.setValue(Double.parseDouble(tfValue.getText()));
        if (rbIndustrialized.isSelected()) {
            product.setBarcode(tfEan.getText());
            product.setIsIndustrialized(1);
        } else {
            product.setBarcode(null);
            product.setIsIndustrialized(0);
        }
        return product;
    }

    private void productToForm() {
        if (tvProducts.getSelectionModel().getSelectedIndex() != -1) {
            ProductTV product = tvProducts.getSelectionModel().getSelectedItem();
            tfId.setText(String.valueOf(product.getProduct().getIdProduct()));
            tfCode.setText(String.valueOf(product.getProduct().getNumber()));
            tfName.setText(product.getProduct().getDescription());
            tfValue.setText(String.valueOf(product.getProduct().getValue()));
            if (product.getProduct().getIsIndustrialized() == 1) {
                tfEan.setText(product.getProduct().getBarcode());
                rbIndustrialized.setSelected(true);
            } else {
                tfEan.setText(product.getProduct().getBarcode());
                rbPrepared.setSelected(true);
            }
            ingredientDao.openConnection();
            listIngredients = new ArrayList<>();
            initializeIngredientTableModel(ingredientDao.getListFromProduct(product.getProduct().getIdProduct()));
            ingredientDao.closeConnection();
            disableSaveButtons();
            validate();
            tfCode.requestFocus();
        }
    }

    private void register() {
        productDao.openConnection();
        Product product = formToProduct();
        if (productDao.validateNumber(product.getNumber())) {
            new Alerts("Cadastro de Produtos", null, "Um produto com este código já foi cadastrado!").ErrorAlert();
            productDao.closeConnection();
            return;
        }
        if (productDao.validateName(product.getDescription())) {
            new Alerts("Cadastro de Produtos", null, "Um produto com este nome já foi cadastrado!").ErrorAlert();
            productDao.closeConnection();
            return;
        }
        Integer newProduct = productDao.newProduct(product);
        productDao.closeConnection();

        if(newProduct != null){
            ProductHasIngredientDao productHasIngredientDao = new ProductHasIngredientDao();
            productHasIngredientDao.openConnection();
            for (IngredientTV c : tvIngredients.getItems()) {
                productHasIngredientDao.newProductHasIngredients(newProduct, c.getIngredient().getIdIngredient());
            }
        }

        new Alerts("Cadastro de Produtos", null, "Produto cadastrado com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void update() {
        productDao.openConnection();
        Product product = formToProduct();
        productDao.updateProduct(product);
        productDao.closeConnection();
        new Alerts("Cadastro de Produtos", null, "Produtos atualizado com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void remove() {
        productDao.openConnection();
        Product product = formToProduct();
        productDao.removeProduct(product);
        productDao.closeConnection();
        new Alerts("Cadastro de Produtos", null, "Produtos removido com sucesso").InformationAlert();
        cleanFields();
        initializeTableModel();
    }

    public void search() {
        filteredInProductTVS.setPredicate(c -> c.filter(tfSearch.getText()));
    }

    private void initializeCombobox() {
        ingredientDao.openConnection();
        List<Ingredient> listIngredients;
        ObservableList<Ingredient> ingredientObs;

        listIngredients = ingredientDao.getList();
        ingredientObs = FXCollections.observableArrayList();
        for (Ingredient c : listIngredients) {
            ingredientObs.add(c);
        }
        ingredientDao.closeConnection();
        cbIngredient.setItems(ingredientObs);
    }

    private void initializeIngredientTableModel(List<Ingredient> listIngredients) {

        ingredientTVS = FXCollections.observableArrayList();
        listIngredients.forEach(c -> ingredientTVS.add(new IngredientTV(c)));

        tcIngredientsCode.setCellValueFactory(new PropertyValueFactory("code"));
        tcIngredientsDescription.setCellValueFactory(new PropertyValueFactory("description"));
        tvIngredients.setItems(ingredientTVS);
    }

    private void initializeTableModel() {
        productDao.openConnection();
        List<Product> listProducts = productDao.getList();
        productDao.closeConnection();

        productTVS = FXCollections.observableArrayList();
        listProducts.forEach(c -> productTVS.add(new ProductTV(c)));

        filteredInProductTVS = new FilteredList<>(productTVS);
        filteredInProductTVS.setPredicate(p -> true);

        tcCode.setCellValueFactory(new PropertyValueFactory("code"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("description"));
        tcValue.setCellValueFactory(new PropertyValueFactory("value"));

        tvProducts.setItems(filteredInProductTVS);
    }

    public void cleanFields() {
        tfId.setText(null);
        tfCode.setText("");
        tfName.setText("");
        tfValue.setText("");
        tfEan.setText("");
        rbPrepared.setSelected(false);
        rbIndustrialized.setSelected(false);

        lbEName.setVisible(false);
        lbECode.setVisible(false);
        lbEEan.setVisible(false);
        lbEType.setVisible(false);
        lbEValue.setVisible(false);

        lbIngredient.setVisible(false);
        cbIngredient.setVisible(false);
        btAddIngredient.setVisible(false);
        btRemoveIngredient.setVisible(false);
        tvIngredients.setVisible(false);
        lbEan.setVisible(false);
        tfEan.setVisible(false);

        listIngredients = new ArrayList<>();

        initializeIngredientTableModel(listIngredients);
        initializeCombobox();

        enableSaveButtons();
    }

    private void removeIngredient() {
        if (tvIngredients.getSelectionModel().getSelectedIndex() != -1) {
            tvIngredients.getItems().remove(tvIngredients.getSelectionModel().getSelectedIndex());
            listIngredients = new ArrayList<>();
            tvIngredients.getItems().forEach(c -> {
                listIngredients.add(c.getIngredient());
            });
            initializeIngredientTableModel(listIngredients);
        }
        btRemoveIngredient.setDisable(true);
    }

    private boolean validate() {
        result[0] = validation.validateText(tfCode, lbECode, 1);
        result[1] = validation.validateText(tfName, lbEName, 2);
        result[2] = validation.validateText(tfValue, lbEValue, 1);
        result[3] = true;
        if (rbIndustrialized.isSelected() || rbPrepared.isSelected()) {
            result[4] = true;
            lbEType.setVisible(false);
        } else {
            result[4] = false;
            lbEType.setVisible(true);
        }
        if (rbPrepared.isSelected()) {
            if (tvIngredients.getItems().isEmpty()) {
                result[5] = false;
                lbEIngredient.setVisible(true);
            } else {
                result[5] = true;
                lbEIngredient.setVisible(false);
            }
        } else if (rbIndustrialized.isSelected()) {
            result[3] = validation.validateText(tfEan, lbEEan, 4);
            result[5] = validation.validateNumber(tfEan, lbEEan, 3);
        }
        return result[0] && result[1] && result[2] && result[3] && result[4] && result[5];
    }


}
