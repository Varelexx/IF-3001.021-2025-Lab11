package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class shortestPathController
{
    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TableColumn col_vertex;
    @javafx.fxml.FXML
    private RadioButton rb_linkedList;
    @javafx.fxml.FXML
    private RadioButton rb_adjList;
    @javafx.fxml.FXML
    private TableColumn col_position;
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private RadioButton rb_adjMatrix;
    @javafx.fxml.FXML
    private TableColumn col_distance;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
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