package controller;

import domain.AdjacencyMatrixGraph;
import domain.GraphException;
import domain.list.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import util.FXUtility;
import util.Utility;

import java.util.HashSet;
import java.util.Set;

public class AdjMatrixGraphOperationsViewController {

    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TextArea ta_toString;

    private AdjacencyMatrixGraph graph;
    private Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        alert = FXUtility.alert("Adjacency Matrix Operations", null);
        graph = new AdjacencyMatrixGraph(30); // Capacidad máxima arbitraria
        ta_toString.setText("Presione 'Randomize' para comenzar...");
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        graph = new AdjacencyMatrixGraph(30); // Reinicia el grafo
        ta_toString.clear();
        ta_toString.setText("Grafo limpiado.");
    }

    @javafx.fxml.FXML
    public void addVertexOnAction(ActionEvent actionEvent) {
        try {
            int newVertex;
            do {
                newVertex = Utility.random(100);
            } while (graph.containsVertex(newVertex));
            graph.addVertex(newVertex);
            ta_toString.setText("Vértice agregado: " + newVertex + "\n" + graph.toString());
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al agregar vértice: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeVertexOnAction(ActionEvent actionEvent) {
        try {
            Object vertex = graph.containsVertex(Utility.random(graph.size()));
            graph.removeVertex(vertex);
            ta_toString.setText("Vértice eliminado: " + vertex + "\n" + graph.toString());
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al eliminar vértice: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addEdgesWeightOnAction(ActionEvent actionEvent) {
        try {
            int n = graph.size();
            if (n < 2) return;

            Object[] vertices = new Object[n];
            for (int i = 0; i < n; i++) {
                vertices[i] = graph.containsVertex(i);
            }

            // Agregar 5 aristas aleatorias
            for (int i = 0; i < 5; i++) {
                int a = Utility.random(n);
                int b;
                do {
                    b = Utility.random(n);
                } while (a == b || graph.containsEdge(vertices[a], vertices[b]));

                int weight = Utility.random(50) + 1;
                graph.addEdgeWeight(vertices[a], vertices[b], weight);
            }
            ta_toString.setText("Se agregaron aristas aleatorias.\n" + graph.toString());
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al agregar aristas: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeEdgesWeightOnAction(ActionEvent actionEvent) {
        try {
            int n = graph.size();
            if (n < 2) return;

            Object[] vertices = new Object[n];
            for (int i = 0; i < n; i++) {
                vertices[i] = graph.containsVertex(i);
            }

            // Eliminar hasta 3 aristas aleatorias existentes
            int removed = 0;
            for (int i = 0; i < n && removed < 3; i++) {
                for (int j = 0; j < n && removed < 3; j++) {
                    if (i != j && graph.containsEdge(vertices[i], vertices[j])) {
                        graph.removeEdge(vertices[i], vertices[j]);
                        removed++;
                    }
                }
            }
            ta_toString.setText("Se eliminaron aristas aleatorias.\n" + graph.toString());
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al eliminar aristas: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        try {
            graph = new AdjacencyMatrixGraph(30);
            Set<Integer> unique = new HashSet<>();
            while (unique.size() < 10) {
                unique.add(Utility.random(100));
            }
            for (Integer value : unique) {
                graph.addVertex(value);
            }

            Object[] vertices = unique.toArray();
            for (int i = 0; i < 15; i++) {
                int a = Utility.random(vertices.length);
                int b;
                do {
                    b = Utility.random(vertices.length);
                } while (a == b);
                int weight = Utility.random(50) + 1;
                graph.addEdgeWeight(vertices[a], vertices[b], weight);
            }

            ta_toString.setText("Grafo aleatorio generado.\n" + graph.toString());
        } catch (GraphException | ListException e) {
            alert.setContentText("Error al randomizar grafo: " + e.getMessage());
            alert.showAndWait();
        }
    }

}