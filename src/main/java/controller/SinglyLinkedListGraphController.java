package controller;

import domain.GraphException;
import domain.SinglyLinkedListGraph;
import domain.list.ListException;
import domain.queue.QueueException;
import domain.stack.StackException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import util.Utility;
import util.FXUtility;

import java.util.Optional;

public class SinglyLinkedListGraphController {
    @javafx.fxml.FXML
    private Pane paneGraph;
    @javafx.fxml.FXML
    private TextArea ta_toString;

    SinglyLinkedListGraph graph;
    private String[] names;
    SinglyLinkedListGraphVisualization visualization;
    private Alert alert;
    private TextInputDialog dialog;

    public void initialize() throws GraphException, ListException {
        graph = new SinglyLinkedListGraph();
        names = new String[]{"Jesús", "Mahoma", "C.Colón", "J. Cesar", "A. Magno",
                "Platón", "Lao Tse", "L. Da Vinci", "Gutenberg", "A. Einstein",
                "C. Darwin", "Karl Marx", "I. Newton", "M. Gandhi", "L. Pasteur",
                "G. Galilei", "Mozart", "Aristóteles", "M. Ángel", "Amstrong",
                "Stalin", "M. Curie", "M. Luther King", "Napoleón", "M. Lutero",
                "Shakespeare", "Pitágoras", "G. Washington", "Lenin", "Buda",
                "Confucio", "Euclides", "Constantino I", "Isabel I", "F. Pizarro",
                "H. Cortés", "J. Calvino", "Beethoven", "N. Copérnico", "Homero",
                "Pedro I", "Cleopatra", "Edison", "N. Tesla", "B. Gates",
                "A. Fleming", "S. de Beauvoir", "J. Watt", "Tales", "J. Dalton"};
        setGraph();
        generateEdges();
        displayGraph();
        alert = FXUtility.alert("SinglyLinkedListGraph", null);
        dialog = FXUtility.dialog("SinglyLinkedListGraph", null);

    }

    private void setGraph() throws GraphException, ListException {
        int count = 1;
        while (count <= 10){
            int randomIndex = util.Utility.random(50)-1;
            if (graph.isEmpty()) {
                graph.addVertex(names[randomIndex]);
                count++;
            } else{
                String currentName = names[randomIndex];
                if (!graph.containsVertex(currentName)){
                    graph.addVertex(currentName);
                    count++;
                }
            }
        }
    }
    private void generateEdges() throws GraphException, ListException {
        for (int i = 0; i < 15; i++) {
            Object a = graph.getVertexByIndex(Utility.random(10)).data;
            Object b = graph.getVertexByIndex(Utility.random(10)).data;

            int weight = util.Utility.random(1000) + 1000;

            graph.addEdgeWeight(a, b, weight);
        }
    }

    private void displayGraph() throws ListException, GraphException {
        paneGraph.getChildren().clear();
        visualization = new SinglyLinkedListGraphVisualization(graph);
        visualization.displayGraph();
        paneGraph.getChildren().add(visualization);
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
        graph = new SinglyLinkedListGraph();
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
        dialog.setContentText("Type the edge to look in format: A-B");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vertex ->{
            try {
                String[] elements = vertex.split("-");

                if (graph.containsEdge((elements[0]),(elements[1]))){
                    alert.setContentText("The elements [" + elements[0] + ", " + elements[1] + "] have an edge");
                    alert.showAndWait();
                } else {
                    alert.setContentText("The elements [" + elements[0] + ", " + elements[1] + "] don't have an edge");
                    alert.showAndWait();
                }
            } catch (GraphException e) {
                throw new RuntimeException(e);
            } catch (ListException e) {
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                alert.setContentText("Please put this format 'A'-'B'");
                alert.showAndWait();
            }
        });
    }

    @javafx.fxml.FXML
    public void containsVertexOnAction(ActionEvent actionEvent) {
        dialog.setContentText("Type the vertex to look");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(vertex ->{
            try {
                if (graph.containsVertex(vertex)){
                    alert.setContentText("The element [" + vertex + "] exists in the graph");
                    alert.showAndWait();
                } else {
                    alert.setContentText("The element [" + vertex + "] doesn't exist in the graph");
                    alert.showAndWait();
                }
            } catch (GraphException e) {
                throw new RuntimeException(e);
            } catch (ListException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
