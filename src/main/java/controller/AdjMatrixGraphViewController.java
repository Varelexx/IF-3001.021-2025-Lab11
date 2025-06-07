package controller;

import domain.AdjacencyMatrixGraph;
import domain.GraphException;
import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import util.FXUtility;
import util.Utility;

import java.util.*;

public class AdjMatrixGraphViewController {
    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TextArea ta_toString;
    private ArrayList<Integer> vertices; // 10 números aleatorios
    AdjacencyMatrixGraph graph;
    private Alert alert;
    private TextInputDialog dialog;
    AdjMatrixGraphVisualization visualization;

    public void initialize() throws GraphException, ListException {
        graph = new AdjacencyMatrixGraph(10); // 10 vértices
        setGraph();
        generateEdges();
        displayGraph();
        alert = FXUtility.alert("AdjMatrixGraph", null);
        dialog = FXUtility.dialog("AdjMatrixGraph", null);
    }

    // Genera 10 números aleatorios entre 0 y 99, sin repetidos, y los añade como vértices
    private void setGraph() throws GraphException, ListException {
        vertices = new ArrayList<>();
        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i < 100; i++) pool.add(i); // pool de [0,99]
        Collections.shuffle(pool);
        for (int i = 0; i < 10; i++) {
            int number = pool.get(i);
            vertices.add(number);
            graph.addVertex(number);
        }
    }

    private void displayGraph() throws ListException, GraphException {
        paneGraph.getChildren().clear();
        visualization = new AdjMatrixGraphVisualization(graph);
        visualization.displayGraph();
        paneGraph.getChildren().add(visualization);
    }

    // Genera hasta 15 aristas entre los vértices, con pesos entre 1 y 50, sin bucles ni aristas duplicadas
    private void generateEdges() throws GraphException, ListException {
        int countEdges = 0;
        int n = vertices.size();
        while (countEdges < 15){
            int idxA = Utility.random(n) - 1;
            int idxB = Utility.random(n) - 1;
            Object a = vertices.get(idxA);
            Object b = vertices.get(idxB);

            if (!a.equals(b) && !graph.containsEdge(a, b)){
                int weight = Utility.random(50); // [1, 50]
                if (weight == 0) weight = 1;
                graph.addEdgeWeight(a, b, weight);
                countEdges++;
            }
        }
    }

    @javafx.fxml.FXML
    public void toStringOnAction(ActionEvent actionEvent) {
        ta_toString.setText(graph.toString());
        ta_toString.setVisible(true);
    }

    @javafx.fxml.FXML
    public void dfsTourOnAction(ActionEvent actionEvent) throws GraphException, ListException, StackException {
        alert.setContentText("DFS Tour: " + graph.dfs().substring(0, graph.dfs().length()-2));
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) throws GraphException, ListException {
        graph = new AdjacencyMatrixGraph(10);
        setGraph();
        generateEdges();
        displayGraph();
        ta_toString.setVisible(false);
    }

    @javafx.fxml.FXML
    public void bfsTourOnAction(ActionEvent actionEvent) throws GraphException, QueueException, ListException {
        alert.setContentText("DFS Tour: " + graph.bfs().substring(0, graph.bfs().length()-2));
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void containsEdgeOnAction(ActionEvent actionEvent) {
        dialog.setContentText("Type the edge to look in format: a-b (where a and b are numbers)");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vertex ->{
            try {
                String[] elements = vertex.split("-");
                Integer a = Integer.parseInt(elements[0].trim());
                Integer b = Integer.parseInt(elements[1].trim());

                if (graph.containsEdge(a, b)){
                    alert.setContentText("The elements [" + a + ", " + b + "] have an edge");
                    alert.showAndWait();
                } else {
                    alert.setContentText("The elements [" + a + ", " + b + "] don't have an edge");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                alert.setContentText("Please put this format: number-number (example: 13-54)");
                alert.showAndWait();
            }
        });
    }

    @Deprecated
    public void containsVertexOnAction(ActionEvent actionEvent) {
        dialog.setContentText("Type the vertex (number) to look");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vertex ->{
            try {
                Integer v = Integer.parseInt(vertex.trim());
                if (graph.containsVertex(v)){
                    alert.setContentText("The element [" + v + "] exists in the graph");
                    alert.showAndWait();
                } else {
                    alert.setContentText("The element [" + v + "] doesn't exist in the graph");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                alert.setContentText("Please enter a valid integer between 0 and 99.");
                alert.showAndWait();
            }
        });
    }

    @javafx.fxml.FXML
    public void containsVertex(ActionEvent actionEvent) {
        dialog.setContentText("Type the vertex (number) to look");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vertex ->{
            try {
                Integer v = Integer.parseInt(vertex.trim());
                if (graph.containsVertex(v)){
                    alert.setContentText("The element [" + v + "] exists in the graph");
                    alert.showAndWait();
                } else {
                    alert.setContentText("The element [" + v + "] doesn't exist in the graph");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                alert.setContentText("Please enter a valid integer between 0 and 99.");
                alert.showAndWait();
            }
        });
    }
}
