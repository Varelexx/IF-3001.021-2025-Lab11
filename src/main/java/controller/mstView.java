package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class mstView
{
    @javafx.fxml.FXML
    private RadioButton rb_kruskal;
    @javafx.fxml.FXML
    private RadioButton rb_prim;
    @javafx.fxml.FXML
    private RadioButton rb_linkedList;
    @javafx.fxml.FXML
    private Pane paneGraphLeft;
    @javafx.fxml.FXML
    private RadioButton rb_adjList;
    @javafx.fxml.FXML
    private RadioButton rb_adjMatrix;
    @javafx.fxml.FXML
    private Pane paneGraphRight;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void kruskalOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void primOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void adjacencyListOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void linkedListOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void adjacencyMatrixOnAction(ActionEvent actionEvent) {
    }
}