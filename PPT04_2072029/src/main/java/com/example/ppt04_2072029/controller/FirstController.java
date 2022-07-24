package com.example.ppt04_2072029.controller;

import com.example.ppt04_2072029.HelloApplication;
import com.example.ppt04_2072029.dao.CategoryDao;
import com.example.ppt04_2072029.dao.ItemsDao;
import com.example.ppt04_2072029.model.Category;
import com.example.ppt04_2072029.model.Items;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstController {
    @FXML
    public TableView<Items> tableHome;
    @FXML
    private TableColumn<Integer, Items> colId;
    @FXML
    private TableColumn<String, Items> colName;
    @FXML
    private TableColumn<Double, Items> colPrice;
    @FXML
    private TableColumn<Category, Items> colCategory;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDescription;
    @FXML
    public ComboBox<Category> comboCategory;
    public Button btnUpdate;
    public Button btnDelete;
    ObservableList<Items> iList;
    private Stage stage;
    @FXML
    private MenuItem showCategory;
    @FXML
    private MenuItem closeHome;
    public CategoryDao categoryDao = new CategoryDao();
    public ItemsDao itemsDao = new ItemsDao();
    private FXMLLoader fxmlLoader;

    public void initialize() {
        ObservableList<Category> cList = categoryDao.getData();
        comboCategory.setItems(cList);
        showCategory.setAccelerator(KeyCombination.keyCombination("Alt+F2"));
        closeHome.setAccelerator(KeyCombination.keyCombination("Alt+X"));
        ShowData();
    }

    public void OnShowTab(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.showAndWait();

        ObservableList<Category> cList = categoryDao.getData();
        comboCategory.setItems(cList);
    }

    public void OnCloseHome(ActionEvent actionEvent) {
        txtId.getScene().getWindow().hide();
    }

    public void ShowData() {
        iList = itemsDao.getData();
        tableHome.setItems(iList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category_id"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void OnSave(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDescription.getText().isEmpty() || comboCategory.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Please fill the field", ButtonType.OK);
            alert.show();
        } else {
            int iId = Integer.parseInt(txtId.getText());
            String iName = txtName.getText();
            double iPrice = Double.parseDouble(txtPrice.getText());
            String iDescription = txtDescription.getText();
            Category iCategorys = comboCategory.getValue();
            itemsDao.addData(new Items(iId, iName, iPrice, iDescription, iCategorys));
            ShowData();
        }
    }

    public void OnReset(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        txtDescription.clear();
        comboCategory.setValue(null);
    }
}