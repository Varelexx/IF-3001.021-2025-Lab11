package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.lab.HelloApplication;

import java.io.IOException;

public class HelloController {

    @FXML
    private Text messageText;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void linkedGraphOnAction(ActionEvent actionEvent) {
        loadPage("singlyLinkedListGraphView.fxml");
    }

    @FXML
    public void home(ActionEvent actionEvent) {
        this.messageText.setText("Laboratory No.11");
        this.bp.setCenter(ap);
    }

    @FXML
    public void linkedOnAction(ActionEvent actionEvent) {
        loadPage("singlyLinkedListGraphOperations.fxml");
    }

    @FXML
    public void MatrixGraphOnAction(ActionEvent actionEvent) {
        loadPage("adjMatrixGraphView.fxml");

    }

    @FXML
    public void MatrixOperationsOnAction(ActionEvent actionEvent) {
        loadPage("adjMatrixGraphOperationsView.fxml");

    }

    @FXML
    public void ListGraphOnAction(ActionEvent actionEvent) {
        loadPage("adjListGraphView.fxml");

    }

    @FXML
    public void ListOperatioOnAction(ActionEvent actionEvent) {
        loadPage("adjListGraphOperationsView.fxml");
    }
}