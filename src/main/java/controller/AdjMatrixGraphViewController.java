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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AdjMatrixGraphViewController {
    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TextArea ta_toString;

    private AdjacencyMatrixGraph graph;
    private Alert alert;
    private TextInputDialog dialog;

    @javafx.fxml.FXML
    public void initialize() {
        alert = FXUtility.alert("Adjacency Matrix Graph", null);
        dialog = FXUtility.dialog("Adjacency Matrix Graph", null);
        randomizeGraph();
    }

    private void randomizeGraph() {
        try {
            graph = new AdjacencyMatrixGraph(10);
            Set<Integer> uniqueVertices = new HashSet<>();
            while (uniqueVertices.size() < 10) {
                uniqueVertices.add(Utility.random(100));
            }
            for (Integer vertex : uniqueVertices) {
                graph.addVertex(vertex);
            }
            Integer[] vertices = uniqueVertices.toArray(new Integer[0]);
            for (int i = 0; i < 15; i++) {
                int indexA = Utility.random(10);
                int indexB = Utility.random(10);
                if (indexA != indexB) {
                    int weight = Utility.random(50) + 1;
                    graph.addEdgeWeight(vertices[indexA], vertices[indexB], weight);
                }
            }
            ta_toString.setVisible(false);
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al generar el grafo: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void toStringOnAction(ActionEvent actionEvent) {
        try {
            ta_toString.setText(graph.toString());
            ta_toString.setVisible(true);
        } catch (Exception e) {
            alert.setContentText("Error al mostrar el grafo: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void dfsTourOnAction(ActionEvent actionEvent) {
        try {
            String result = graph.dfs();
            alert.setContentText("Recorrido DFS: " + result);
            alert.showAndWait();
        } catch (GraphException | StackException | ListException e) {
            alert.setContentText("Error en recorrido DFS: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void bfsTourOnAction(ActionEvent actionEvent) {
        try {
            String result = graph.bfs();
            alert.setContentText("Recorrido BFS: " + result);
            alert.showAndWait();
        } catch (GraphException | QueueException | ListException e) {
            alert.setContentText("Error en recorrido BFS: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void containsVertex(ActionEvent actionEvent) {
        dialog.setContentText("Ingrese el vértice a buscar:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                int vertex = Integer.parseInt(input.trim());
                if (graph.containsVertex(vertex)) {
                    alert.setContentText("El vértice [" + vertex + "] existe en el grafo.");
                } else {
                    alert.setContentText("El vértice [" + vertex + "] no existe en el grafo.");
                }
                alert.showAndWait();
            } catch (NumberFormatException e) {
                alert.setContentText("Entrada inválida. Ingrese un número entero.");
                alert.showAndWait();
            } catch (GraphException | ListException e) {
                alert.setContentText("Error al verificar el vértice: " + e.getMessage());
                alert.showAndWait();
            }
        });
    }

    @javafx.fxml.FXML
    public void containsEdgeOnAction(ActionEvent actionEvent) {
        dialog.setContentText("Ingrese la arista en formato A-B:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                String[] parts = input.trim().split("-");
                if (parts.length != 2) {
                    alert.setContentText("Formato incorrecto. Use el formato A-B.");
                    alert.showAndWait();
                    return;
                }
                int vertexA = Integer.parseInt(parts[0].trim());
                int vertexB = Integer.parseInt(parts[1].trim());
                if (graph.containsEdge(vertexA, vertexB)) {
                    alert.setContentText("Existe una arista entre [" + vertexA + "] y [" + vertexB + "].");
                } else {
                    alert.setContentText("No existe una arista entre [" + vertexA + "] y [" + vertexB + "].");
                }
                alert.showAndWait();
            } catch (NumberFormatException e) {
                alert.setContentText("Entrada inválida. Ingrese números enteros en el formato A-B.");
                alert.showAndWait();
            } catch (GraphException | ListException e) {
                alert.setContentText("Error al verificar la arista: " + e.getMessage());
                alert.showAndWait();
            }
        });
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        randomizeGraph();
    }
}
