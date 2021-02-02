package br.com.brunoricardo.laricaodajo.controller;

import br.com.brunoricardo.laricaodajo.dao.BoxDao;
import br.com.brunoricardo.laricaodajo.dao.ProductDao;
import br.com.brunoricardo.laricaodajo.dao.OrdererDao;
import br.com.brunoricardo.laricaodajo.model.Box;
import br.com.brunoricardo.laricaodajo.model.Orderer;
import br.com.brunoricardo.laricaodajo.model.OrderHasProducts;
import br.com.brunoricardo.laricaodajo.model.OrderHasProductsTV;
import br.com.brunoricardo.laricaodajo.model.Product;
import br.com.brunoricardo.laricaodajo.model.ProductTV;
import br.com.brunoricardo.laricaodajo.utility.Alerts;
import br.com.brunoricardo.laricaodajo.utility.Clock;
import br.com.brunoricardo.laricaodajo.utility.DateUtils;
import br.com.brunoricardo.laricaodajo.utility.Dialogues;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SalesController implements Initializable {

    @FXML
    private AnchorPane sales;

    @FXML
    private GridPane gpInfo;

    @FXML
    private MenuItem miChangePassword;

    @FXML
    private MenuItem miOpenBox;

    @FXML
    private MenuItem miCloseBox;

    @FXML
    private MenuItem miCancelCoupon;

    @FXML
    private MenuItem miReprintCoupon;

    @FXML
    private MenuItem miWithdrawal;

    @FXML
    private MenuItem miLogout;

    @FXML
    private MenuItem miClose;

    @FXML
    private MenuItem miMethodOfPayment;

    @FXML
    private MenuItem miEmployees;

    @FXML
    private MenuItem miProducts;

    @FXML
    private MenuItem miIngredients;

    @FXML
    private MenuItem miGeneralSales;

    @FXML
    private MenuItem miSalesByMethod;

    @FXML
    private MenuItem miConfiguration;

    @FXML
    private MenuItem miAbout;

    @FXML
    private Label lbName;

    @FXML
    private Button btLogout;

    @FXML
    private Button btOpenBox;

    @FXML
    private Label lbTime;

    @FXML
    private Label lbDate;

    @FXML
    private Label lbLastOrderer;

    @FXML
    private AnchorPane apOrderer;

    @FXML
    private Button btRemoveS;

    @FXML
    private TextField tfCodeS;

    @FXML
    private Button btAddS;

    @FXML
    private TableView<OrderHasProductsTV> tvSale;

    @FXML
    private TableColumn<?, ?> tcCodeS;

    @FXML
    private TableColumn<?, ?> tcDescS;

    @FXML
    private TableColumn<?, ?> tcValUS;

    @FXML
    private TableColumn<?, ?> tcAmountS;

    @FXML
    private TableColumn<?, ?> tcValTS;

    @FXML
    private Label lbTotalValue;

    @FXML
    private Button btCancelOrderer;

    @FXML
    private Button btCloseOrderer;

    @FXML
    private Button btRetract;

    @FXML
    private AnchorPane apSearch;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btSearch;

    @FXML
    private TableView<ProductTV> tvSearch;

    @FXML
    private TableColumn<?, ?> tcCode;

    @FXML
    private TableColumn<?, ?> tcDescB;

    @FXML
    private TableColumn<?, ?> tcVal;

    private boolean retracted = false;
    private ProductDao productDao = new ProductDao();
    private BoxDao boxDao = new BoxDao();
    private ObservableList<ProductTV> products;
    private ObservableList<OrderHasProductsTV> orderHasProducts;
    private FilteredList<ProductTV> filteredProducts;
    private double totalOrderer = 0;
    private int auxOdererProduct = 1;
    private static Integer orderNumber;
    private static boolean verifiedOrderNumber = false;
    private static Integer idBox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        lbName.setText(MainViewController.employee.getName());
        MainViewController.clock = new Clock(lbTime, lbDate);
        closedBox();

        idBox = verifyBox();
        if (idBox == null) {
            MainViewController.box = openBox();
            boxDao.openConnection();
            idBox = boxDao.newBox(MainViewController.box);

            if (idBox == 0) {
                new Alerts("Abrir Box", null, "Falha ao abrir caixa. Contate o Administrador do sistema").ErrorAlert();
            } else {
                openedBox();
                MainViewController.box.setIdBox(idBox);
            }
        } else {
            openedBox();
        }

        System.out.println("idBox: " + idBox);
        productDao.openConnection();
        List<Product> listProducts = productDao.getList();
        productDao.closeConnection();

        products = FXCollections.observableArrayList();
        orderHasProducts = FXCollections.observableArrayList();
        listProducts.forEach(c -> products.add(new ProductTV(c)));

        filteredProducts = new FilteredList<>(products);
        filteredProducts.setPredicate(p -> true);

        //Colunas Tabela Orderer
        tcCodeS.setCellValueFactory(new PropertyValueFactory("code"));
        tcDescS.setCellValueFactory(new PropertyValueFactory("description"));
        tcValUS.setCellValueFactory(new PropertyValueFactory("unitaryValue"));
        tcAmountS.setCellValueFactory(new PropertyValueFactory("amount"));
        tcValTS.setCellValueFactory(new PropertyValueFactory("total"));

        //Colunas Tabela de Busca
        tcCode.setCellValueFactory(new PropertyValueFactory("code"));
        tcDescB.setCellValueFactory(new PropertyValueFactory("description"));
        tcVal.setCellValueFactory(new PropertyValueFactory("value"));

        tvSearch.setItems(filteredProducts);
        tvSale.setItems(orderHasProducts);

        btLogout.setOnAction(e -> deslogar());
        btRetract.setOnAction(e -> retrair());
        btSearch.setOnAction(e -> pesquisar());
        btAddS.setOnAction(e -> addOrdererItem());
        btRemoveS.setOnAction(e -> removeItem());
        tfSearch.setOnKeyReleased(e -> pesquisar());
        btCloseOrderer.setOnAction(e -> fecharOrderer());
        miChangePassword.setOnAction(event -> new Dialogues("createNewPasswordDialog"));
        miProducts.setOnAction(event -> new Dialogues("registerProduct"));
        miIngredients.setOnAction(event -> new Dialogues("registerIngredient"));

    }

    private Box openBox() {
        Box newBox = new Box();
        Timestamp date = new Timestamp(System.currentTimeMillis());

        newBox.setOpeningDate(date);
        newBox.setIsOpen((byte) 1);
        newBox.setIdEmployee(MainViewController.employee.getIdEmployee());
        newBox.setValue(0);
        return newBox;
    }

    private void openDialogIngredient() {

    }

    private void addOrdererItem() {
        OrderHasProducts ordererItem = new OrderHasProducts();
        String pattern = "[0-9]{1,2}";

        productDao.openConnection();
        ordererItem.setProduct(productDao.findNumber(Integer.valueOf(tfCodeS.getText())));
        productDao.closeConnection();

        if (ordererItem.getProduct() == null) {
            new Alerts("Adicionar Item", null, "Código do produto não encontrado!").ErrorAlert();
            tfCodeS.requestFocus();
            tfCodeS.selectAll();
        } else if (ordererItem.getProduct().getNumber() == 0) {
            //LOGICA PASTEL CUSTOMIZADO
        }
        ordererItem.setIdProduct(ordererItem.getProduct().getIdProduct());
        ordererItem.setAmount(-1);

        while (ordererItem.getAmount() == -1) {
            String amount = new Alerts("Quantidade", null, "Digite a quantidade para o produto: " + ordererItem.getProduct().getDescription()).InputAlert();
            if (amount == null) {
                return;
            } else if (amount.matches(pattern)) {
                ordererItem.setAmount(Integer.valueOf(amount));
            }
        }

        if (ordererItem.getProduct().getNumber() == 0) {
            ordererItem.setTotal(ordererItem.getAmount() * ordererItem.getTotal());
        } else {
            ordererItem.setTotal((ordererItem.getAmount() * ordererItem.getProduct().getValue()));
        }

        for (int i = 0; i < orderHasProducts.size(); i++) {
            System.out.println("FOR i=" + i);
            if (orderHasProducts.get(i).getOrderHasProducts().getIdProduct() == ordererItem.getIdProduct()) {
                System.out.println("IF idProd Iguais");
                if ("nulo".equals(orderHasProducts.get(i).getOrderHasProducts().getName()) && "nulo".equals(ordererItem.getName())) {
                    System.out.println("IF nomePastel iguais");

                    System.out.println("Total Orderer Antes de Remover: " + totalOrderer);
                    totalOrderer = totalOrderer - orderHasProducts.get(i).getOrderHasProducts().getTotal();
                    System.out.println("Total Orderer Depois de Remover: " + totalOrderer);

                    if (ordererItem.getAmount() == 0) {
                        System.out.println("Remover este Item");
                        orderHasProducts.remove(i);
                        return;
                    } else {
                        System.out.println("IF Quantidade Diferente de 0");
                        orderHasProducts.set(i, new OrderHasProductsTV(ordererItem));
                        totalOrderer = totalOrderer + ordererItem.getTotal();
                        System.out.println("Total Orderer Depois de Adicionar: " + totalOrderer);
                        updateTotal();
                        return;
                    }
                }
            }
        }

        ordererItem.setOrderNumber(auxOdererProduct++);

        System.out.println("Ordem : " + ordererItem.getOrderNumber());
        System.out.println("AuxOrdem : " + auxOdererProduct);

        orderHasProducts.add(new OrderHasProductsTV(ordererItem));
        totalOrderer += ordererItem.getTotal();
        updateTotal();
        tfCodeS.setText("");
        tfCodeS.requestFocus();
    }

    private void updateTotal() {
        lbTotalValue.setText(String.format("Valor Total: %3.2f", totalOrderer));
    }

    private void openedBox() {
        btOpenBox.setText("Fechar Caixa");
        miOpenBox.setDisable(true);
        miCloseBox.setDisable(false);
        miWithdrawal.setDisable(false);
        miCancelCoupon.setDisable(false);
        miReprintCoupon.setDisable(false);

        tfCodeS.setDisable(false);
        tfSearch.setDisable(false);

        btAddS.setDisable(false);
        btSearch.setDisable(false);
        btCancelOrderer.setDisable(false);
        btRemoveS.setDisable(false);
        btCloseOrderer.setDisable(false);

        tvSearch.setDisable(false);
        tvSale.setDisable(false);
    }

    private void closedBox() {
        btOpenBox.setText("Abrir Caixa");
        miOpenBox.setDisable(false);
        miCloseBox.setDisable(true);
        miWithdrawal.setDisable(true);
        miCancelCoupon.setDisable(true);
        miReprintCoupon.setDisable(true);

        tfCodeS.setDisable(true);
        tfSearch.setDisable(true);

        btAddS.setDisable(true);
        btSearch.setDisable(true);
        btCancelOrderer.setDisable(true);
        btRemoveS.setDisable(true);
        btCloseOrderer.setDisable(true);

        tvSearch.setDisable(true);
        tvSale.setDisable(true);
    }

    private void deslogar() {
        MainViewController.employee = null;
        MainViewController.nav.navega("login");
    }

    private boolean fecharBox(Integer index) {
        /*
         new TelaFechamentoBox(this, true, index).setVisible(true);
         if (TelaFechamentoBox.isFechou()) {
         JOptionPane.showMessageDialog(this, "Box Fechado com Sucesso!", "Fechamento de Box", JOptionPane.INFORMATION_MESSAGE);
         return true;
         }
         */
        return false;
    }

    private void fecharOrderer() {
        if (validateOrderer()) {
            Orderer orderer = new Orderer(orderHasProducts);
            if (verifiedOrderNumber) {
                orderer.setNumber(orderNumber);
                orderNumber++;
            } else {
                orderer.setNumber(verificaNumeroOrderer());
                orderNumber++;
            }
            orderer.setIdBox(idBox);
            orderer.setSubtotal(totalOrderer);

            MainViewController.orderer = orderer;
            //Main.setScene("confirmPayment");

            if (MainViewController.isPaid) {
                MainViewController.isPaid = false;
                cleanOrderer();
            }
        }

    }

    private void cleanOrderer() {
        orderHasProducts = FXCollections.observableArrayList();
        tvSale.setItems(orderHasProducts);
        totalOrderer = 0;
        auxOdererProduct = 1;
        updateTotal();
        tfCodeS.setText("");
        tfCodeS.requestFocus();
    }

    private void pesquisar() {
        filteredProducts.setPredicate(c -> c.filter(tfSearch.getText()));
    }

    private void removeItem() {

        if (orderHasProducts.size() == 1) {
            cleanOrderer();
        }

        int index = tvSale.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            totalOrderer = totalOrderer - orderHasProducts.get(index).getOrderHasProducts().getTotal();
            orderHasProducts.remove(index);
            updateTotal();
        } else {
            new Alerts("Remover Item", null, "Selecione um item para remover do pedido!").WarningAlert();
            tfCodeS.setText("");
            tfCodeS.requestFocus();
        }
    }

    private void retrair() {
        if (retracted) {
            apOrderer.setPrefWidth(584);
            tcCodeS.setPrefWidth(46);
            tcDescS.setPrefWidth(218);
            tcValUS.setPrefWidth(102);
            tcAmountS.setPrefWidth(89);
            tcValTS.setPrefWidth(88);
            apSearch.setVisible(true);
            btRetract.setText(">");
            retracted = false;
        } else {
            apOrderer.setPrefWidth(1004);
            tcCodeS.setPrefWidth(46);
            tcDescS.setPrefWidth(636);
            tcValUS.setPrefWidth(99);
            tcAmountS.setPrefWidth(99);
            tcValTS.setPrefWidth(99);
            apSearch.setVisible(false);
            btRetract.setText("<");
            retracted = true;
        }
    }

    public boolean validateOrderer() {
        boolean validade = true;
        if (orderHasProducts.size() == 0) {
            validade = false;
            new Alerts("Orderer sem Itens", null, "É necessário ao menos um item no pedido para concluí-lo!").WarningAlert();
            tfCodeS.requestFocus();
        }
        return validade;
    }

    private Integer verifyBox() {
        byte x = 1;
        String dataStr = DateUtils.asStr(new Date());
        BoxDao boxDao = new BoxDao();
        boxDao.openConnection();
        List<Box> boxes = boxDao.listBoxsFromEmployee(MainViewController.employee.getIdEmployee());
        boxDao.closeConnection();

        return 1;

//        for (int i = 0; boxes.size() > i; i++) {
//            Date dataBox = new Date(boxes.get(i).getOpeningDate().getTime());
//
//            if (String.valueOf(dataBox).equals(dataStr)) {
//                openedBox();
//                return boxes.get(i).getIdBox();
//            } else {
//                new Alerts("Caixa anterior encontrado", null, "Você possui um caixa aberto com data anterior ao dia de hoje. \nPor favor, clique em OK para fechar o caixa anterior").AlertaInformacao();
//                //Fechar Box Lógica
//                /*
//                 while (!TelaFechamentoBox.isFechou()) {
//                 fecharBox(caixas.get(i).getIdBox());
//
//                 }
//                 TelaFechamentoBox.setFechou(false);
//                 System.out.println("i= " + i);
//                 */
//            }
//        }
//        return null;
    }

    private Integer verificaNumeroOrderer() {
        OrdererDao ordererDao = new OrdererDao();
        ordererDao.openConnection();
        orderNumber = ordererDao.findLastOrderer(idBox);
        ordererDao.closeConnection();

        if (orderNumber == null) {
            orderNumber = 1;
        } else {
            orderNumber += 1;
        }
        verifiedOrderNumber = true;
        return orderNumber;
    }

    private OrderHasProducts verifyIfExists(OrderHasProducts orderHasProduct) {
        for (int i = 0; i < orderHasProducts.size(); i++) {
            if (orderHasProducts.get(i).getOrderHasProducts().getIdProduct() == orderHasProduct.getIdProduct()) {
                if (!"nulo".equals(orderHasProducts.get(i).getOrderHasProducts().getName()) && !"nulo".equals(orderHasProduct.getName())) {
                    if (orderHasProduct.getName().equals(orderHasProducts.get(i).getOrderHasProducts().getName())) {
                        int aux = orderHasProducts.get(i).getOrderHasProducts().getOrderNumber();
                        totalOrderer = totalOrderer - orderHasProducts.get(i).getOrderHasProducts().getTotal();
                        if (orderHasProduct.getAmount() == 0) {
                            if (orderHasProducts.size() == 1) {
                                cleanOrderer();
                            } else {
                                orderHasProducts.remove(i);
                            }
                        } else {
                            orderHasProduct.setOrderNumber(aux);
                            orderHasProducts.add(i, new OrderHasProductsTV(orderHasProduct));
                        }
                        return null;
                    }
                } else {
                    int aux = orderHasProducts.get(i).getOrderHasProducts().getOrderNumber();
                    totalOrderer = totalOrderer - orderHasProducts.get(i).getOrderHasProducts().getTotal();
                    if (orderHasProduct.getAmount() == 0) {
                        if (orderHasProducts.size() == 1) {
                            cleanOrderer();
                        } else {
                            orderHasProducts.remove(i);
                        }
                    } else {
                        orderHasProduct.setOrderNumber(aux);
                        orderHasProducts.add(i, new OrderHasProductsTV(orderHasProduct));
                    }
                    return null;
                }
            }
        }

        if (orderHasProduct.getAmount() == 0) {
            return null;
        }
        orderHasProduct.setOrderNumber(auxOdererProduct);
        auxOdererProduct = auxOdererProduct + 1;
        orderHasProducts.add(new OrderHasProductsTV(orderHasProduct));
        return orderHasProduct;
    }
}
