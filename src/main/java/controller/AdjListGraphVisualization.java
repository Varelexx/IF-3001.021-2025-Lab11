package controller;

import domain.AdjacencyListGraph;
import domain.GraphException;
import domain.list.ListException;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AdjListGraphVisualization extends Pane {

    private AdjacencyListGraph graph;
    private static final double WIDTH = 600;
    private static final double HEIGHT = 300;

    Circle circles[];
    int visitedEdges = 0;

    public AdjListGraphVisualization(AdjacencyListGraph graph) throws ListException {
        this.graph = graph;
        this.setWidth(1050);
        this.setHeight(1500);
        setStatus("Tree is empty");
        // El arreglo de círculos ahora depende del tamaño del grafo
        circles = new Circle[Math.max(graph.size() + 2, 12)];
    }

    public void setStatus(String msg){
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayGraph() throws ListException, GraphException {
        this.getChildren().clear();
        displayVertex();
        displayEdges();
    }

    private void displayVertex() throws ListException {
        double angleStep = 360.0 / graph.size();
        double offsetX = WIDTH * 0.5;
        double offsetY = HEIGHT * 1.0;
        double radius = 200;

        // Usa el arreglo circles ya creado en el constructor
        for (int i = 1; i <= graph.size(); i++) {
            double angle = Math.toRadians(angleStep * i);
            double x = offsetX + radius * Math.cos(angle);
            double y = offsetY + radius * Math.sin(angle);
            Circle circle = new Circle(x, y, 20, Color.STEELBLUE);
            circles[i] = circle;
            circle.setId("vertex-" + i);
            this.getChildren().add(circle);

            Text text = new Text(graph.getVertexByIndex(i - 1).data + "" ); // OJO: aquí puede ser i-1 si tu método empieza en 0
            text.setX(x - 7);
            text.setY(y + 4);
            text.setFill(Color.BLACK);
            text.setFont(new Font(14));
            text.setWrappingWidth(75);
            this.getChildren().add(text);
        }
    }

    private void displayEdges() throws GraphException, ListException {
        for (int i = 1; i <= graph.size(); i++) {
            for (int j = i + 1; j <= graph.size(); j++) {

                if (i == j) continue;

                Object a = graph.getVertexByIndex(i - 1).data; // OJO: i-1 si el index empieza en 0
                Object b = graph.getVertexByIndex(j - 1).data; // OJO: j-1

                if (graph.containsEdge(a, b)) {
                    Circle circleA = (Circle) this.lookup("#vertex-" + i);
                    Circle circleB = (Circle) this.lookup("#vertex-" + j);
                    Line line = new Line(circleA.getCenterX(),
                            circleA.getCenterY(),
                            circleB.getCenterX(),
                            circleB.getCenterY());

                    line.setStrokeWidth(3.5);
                    line.setCursor(Cursor.HAND);

                    line.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            line.setStroke(Color.RED);
                            showEdge(a, b);
                            line.setUserData("clicked");
                        }
                    });
                    line.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (!"clicked".equals(line.getUserData())) {
                                line.setStroke(Color.GREEN);
                            }
                        }
                    });
                    line.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (!"clicked".equals(line.getUserData())) {
                                line.setStroke(Color.BLACK);
                            }
                        }
                    });

                    this.getChildren().add(line);

                    // Para que las líneas estén detrás de los círculos
                    line.toBack();
                }
            }
        }
    }

    private void showEdge(Object a, Object b) {
        try {
            if (graph.containsEdge(a, b)) {
                Object weight = graph.getWeightEdges(a, b);
                Text text = new Text("Edge between the vertexes: " + a + "... " + b + ". Weight: " + weight);
                text.setX(30);
                text.setY(30 + 25 * visitedEdges); // Si quieres mostrar varias, cámbialo así para no tapar
                text.setFont(new Font(16));
                text.setFill(Color.BLUE);
                text.setWrappingWidth(500);

                this.getChildren().add(text);
                visitedEdges++;
            }
        } catch (GraphException | ListException e) {
            throw new RuntimeException(e);
        }
    }
}
