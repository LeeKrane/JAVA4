package labor04.num2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class L04N2_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
	private Warehouse warehouse;
	private Producer producer;
	private Consumer consumer;
	private Rectangle[] rectangles;
	
	private static final Color FILLED_OR_RUNNING_COLOR = Color.LIMEGREEN;
	private static final Color EMPTY_OR_WAITING_COLOR = Color.ORANGERED;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		initializeSimulation();
		initializeGridPane();
		Platform.runLater(() -> warehouse.stockProperty().addListener(observable -> updateRectangles()));
		startSimulation(warehouse, producer, consumer);
	}
	
	private void updateRectangles () {
		for (int i = 0; i < rectangles.length; i++) {
			if (warehouse.getStock() <= i)
				rectangles[i].setFill(EMPTY_OR_WAITING_COLOR);
			else
				rectangles[i].setFill(FILLED_OR_RUNNING_COLOR);
		}
	}
	
	private void initializeGridPane () {
		rectangles = new Rectangle[20];
		for (int column = 0; column < 20; column++) {
			addColumnConstraints();
			Rectangle rectangle = createRectangle(30, 100, EMPTY_OR_WAITING_COLOR);
			rectangles[column] = rectangle;
			addNodeTo(rectangle, column, 2);
		}
		
		// TODO:
		//  Show stock as integer in FX
		//  Make the look of the working/waiting buttons cleaner
		Rectangle producerRectangle = createRectangle(30, 30, FILLED_OR_RUNNING_COLOR);
		addNodeTo(producerRectangle, 0, 0);
		Platform.runLater(() -> producer.waitingProperty().addListener(observable -> updateThreadRectangle(producerRectangle, producer.isWaiting())));
		
		Rectangle consumerRectangle = createRectangle(30, 30, FILLED_OR_RUNNING_COLOR);
		addNodeTo(consumerRectangle, 1, 0);
		Platform.runLater(() -> consumer.waitingProperty().addListener(observable -> updateThreadRectangle(consumerRectangle, consumer.isWaiting())));
	}
	
	private void updateThreadRectangle (Rectangle rectangle, boolean waiting) {
		if (waiting)
			rectangle.setFill(EMPTY_OR_WAITING_COLOR);
		else
			rectangle.setFill(FILLED_OR_RUNNING_COLOR);
	}
	
	private void addNodeTo (Node node, int column, int row) {
		GridPane.setRowIndex(node, row);
		GridPane.setColumnIndex(node, column);
		pane.getChildren().addAll(node);
	}
	
	private void addColumnConstraints () {
		final ColumnConstraints constraint = new ColumnConstraints();
		constraint.setPercentWidth(5);
		constraint.setHalignment(HPos.CENTER);
		pane.getColumnConstraints().add(constraint);
	}
	
	private Rectangle createRectangle (int width, int height, Color color) {
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		rectangle.setFill(color);
		rectangle.setStyle("-fx-arc-height: 15; -fx-arc-width: 15; -fx-padding: 10; -fx-alignment: center-center;");
		return rectangle;
	}
	
	private void initializeSimulation () {
		warehouse = new Warehouse(20);
		producer = new Producer(warehouse, 1000, 5000, 5, 10, 35);
		consumer = new Consumer(warehouse, 3000, 6000, 7);
	}
	
	private void startSimulation (Warehouse warehouse, Producer producer, Consumer consumer) {
		Simulation.simulate(warehouse, producer, consumer);
	}
}
