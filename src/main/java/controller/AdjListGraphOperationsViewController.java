package controller;

import domain.AdjacencyListGraph;
import domain.GraphException;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import util.Utility;
import util.FXUtility;

public class AdjListGraphOperationsViewController
{
    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TextArea ta_toString;

    private String[] letters;
    AdjacencyListGraph graph;
    private Alert alert;
    AdjListGraphVisualization visualization;

    @javafx.fxml.FXML
    public void initialize() throws GraphException, ListException {
        graph = new AdjacencyListGraph(26); // Para el abecedario
        letters = new String[]{
                "A","B","C","D","E","F","G","H","I","J","K","L","M",
                "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        setGraph();
        generateEdges();
        displayGraph();
        alert = FXUtility.alert("AdjListGraph", null);
        ta_toString.setVisible(true);
        ta_toString.setText(graph.toString());
    }

    private void setGraph() throws GraphException, ListException {
        int count = 1;
        while (count <= 10){
            int randomIndex = Utility.random(26)-1;
            if (graph.isEmpty()) {
                graph.addVertex(letters[randomIndex]);
                count++;
            } else{
                String currentLetter = letters[randomIndex];
                if (!graph.containsVertex(currentLetter)){
                    graph.addVertex(currentLetter);
                    count++;
                }
            }
        }
    }
    private void displayGraph() throws ListException, GraphException {
        paneGraph.getChildren().clear();
        visualization = new AdjListGraphVisualization(graph);
        visualization.displayGraph();
        paneGraph.getChildren().add(visualization);
    }

    private void generateEdges() throws GraphException, ListException {
        int countEdges = 0;
        while (countEdges < 15){
            Object a = graph.getVertexByIndex(Utility.random(graph.size())-1).data;
            Object b = graph.getVertexByIndex(Utility.random(graph.size())-1).data;

            if (!a.equals(b) && !graph.containsEdge(a, b)){
                int weight = Utility.random(200) + 200;
                graph.addEdgeWeight(a, b, weight);
                countEdges++;
            }
        }
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) throws GraphException, ListException {
        graph = new AdjacencyListGraph(26);
        setGraph();
        generateEdges();
        displayGraph();
        ta_toString.setVisible(true);
        ta_toString.setText(graph.toString());
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        graph.clear();
        ta_toString.setVisible(false);
        ta_toString.setText("");
        paneGraph.getChildren().clear();
    }

    @javafx.fxml.FXML
    public void addVertexOnAction(ActionEvent actionEvent) throws ListException, GraphException {
        if (graph.isEmpty()){
            int randomIndex = Utility.random(26) - 1;
            String currentLetter = letters[randomIndex];
            graph.addVertex(currentLetter);
            displayGraph();
            ta_toString.setVisible(true);
            ta_toString.setText(graph.toString());
        } else if (graph.size() == 10) {
            alert.setContentText("The graph is full");
            alert.showAndWait();
        } else {
            boolean added = false;
            while (!added) {
                int randomIndex = Utility.random(26) - 1;
                String currentLetter = letters[randomIndex];
                if (!graph.containsVertex(currentLetter)) {
                    graph.addVertex(currentLetter);
                    added = true;
                }
            }
            displayGraph();
            ta_toString.setVisible(true);
            ta_toString.setText(graph.toString());
        }
    }

    @javafx.fxml.FXML
    public void removeVertexOnAction(ActionEvent actionEvent) throws ListException, GraphException {
        if (graph.isEmpty()) {
            alert.setContentText("Can't remove more vertex \nbecause graph is empty");
            alert.showAndWait();
        } else if (graph.size() == 1) {
            graph.clear();
            ta_toString.setVisible(false);
            ta_toString.setText("");
            paneGraph.getChildren().clear();
            alert.setContentText("Can't remove more vertex \nbecause graph is empty");
            alert.showAndWait();
        } else {
            boolean removed = false;
            while (!removed) {
                int randomIndex = Utility.random(26) - 1;
                String currentLetter = letters[randomIndex];
                if (graph.containsVertex(currentLetter)) {
                    graph.removeVertex(currentLetter);
                    removed = true;
                }
            }
            displayGraph();
            ta_toString.setText(graph.toString());
        }
    }

    @javafx.fxml.FXML
    public void removeEdgesWeightOnAction(ActionEvent actionEvent) throws ListException, GraphException {
        if (graph.isEmpty()) {
            alert.setContentText("Can't remove more edges \nbecause graph is empty");
            alert.showAndWait();
        } else if (graph.size() == 1) {
            alert.setContentText("Can't remove more edges \nbecause there is only one vertex");
            alert.showAndWait();
        } else {
            boolean removed = false;
            while (!removed) {
                Object a = graph.getVertexByIndex(Utility.random(graph.size())-1).data;
                Object b = graph.getVertexByIndex(Utility.random(graph.size())-1).data;
                if (graph.containsEdge(a,b)) {
                    graph.removeEdge(a, b);
                    removed = true;
                }
            }
            displayGraph();
            ta_toString.setText(graph.toString());
        }
    }

    @javafx.fxml.FXML
    public void addEdgesWeightOnAction(ActionEvent actionEvent) throws ListException, GraphException {
        if (graph.isEmpty()){
            alert.setContentText("Can't add edges and weight because the graph is empty");
            alert.showAndWait();
        } else if (graph.size() == 1) {
            alert.setContentText("Can't add more edges \nbecause there is only one vertex");
            alert.showAndWait();
        } else {
            boolean added = false;
            while (!added) {
                Object a = graph.getVertexByIndex(Utility.random(graph.size())-1).data;
                Object b = graph.getVertexByIndex(Utility.random(graph.size())-1).data;
                if (!graph.containsEdge(a,b) && !a.equals(b)) {
                    int weight = Utility.random(200) + 200;
                    graph.addEdgeWeight(a, b, weight);
                    added = true;
                }
            }
            displayGraph();
            ta_toString.setText(graph.toString());
        }
    }
}
