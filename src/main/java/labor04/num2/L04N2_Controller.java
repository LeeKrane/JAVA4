package labor04.num2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
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
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		initializeSimulation();
		createGridPane();
		Platform.runLater(() -> warehouse.getStockProperty().addListener(observable -> updateRectangles()));
		startSimulation(warehouse, producer, consumer);
	}
	
	private void updateRectangles () {
		for (int i = 0; i < rectangles.length; i++) {
			if (warehouse.getStock() <= i)
				rectangles[i].setFill(Color.ORANGERED);
			else
				rectangles[i].setFill(Color.LIMEGREEN);
		}
	}
	
	private void createGridPane () {
		rectangles = new Rectangle[20];
		for (int col = 0; col < 20; col++) {
			Rectangle rectangle = new Rectangle();
			rectangle.setWidth(30);
			rectangle.setHeight(100);
			rectangle.setFill(Color.ORANGERED);
			
			// TODO: In FX einfügen, wer schläft/arbeitet/fertig ist und den Stock als int auch anzeigen.
			
			final ColumnConstraints constraint = new ColumnConstraints();
			constraint.setPercentWidth(5);
			constraint.setHalignment(HPos.CENTER);
			pane.getColumnConstraints().add(constraint);
			
			rectangle.setStyle("-fx-arc-height: 15; -fx-arc-width: 15; -fx-padding: 10; -fx-alignment: center-center;");
			GridPane.setRowIndex(rectangle, 1);
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
