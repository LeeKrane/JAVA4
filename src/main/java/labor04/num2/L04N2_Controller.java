package labor04.num2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		initializeSimulation();
		createRectangles();
		
		startSimulation(warehouse, producer, consumer);
	}
	
	private void createRectangles () {
		rectangles = new Rectangle[20];
		for (int col = 0; col < 20; col++) {
			Rectangle rectangle = new Rectangle();
			rectangle.setWidth(30);
			rectangle.setHeight(100);
			rectangle.setFill(Color.LIMEGREEN);
			GridPane.setRowIndex(rectangle, 0);
			GridPane.setColumnIndex(rectangle, col);
			Platform.runLater(() -> pane.getChildren().addAll(rectangle));
			rectangles[col] = rectangle;
		}
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
