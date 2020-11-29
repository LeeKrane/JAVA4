package labor04.num2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class L04N2_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
	private Warehouse warehouse;
	private Producer producer;
	private Consumer consumer;
	private Rectangle[] rectangles;
	private XYChart.Series<Number, Number> series;
	private Rectangle producerRectangle;
	private Rectangle consumerRectangle;
	
	private static final Color FILLED_OR_RUNNING_COLOR = Color.LIMEGREEN;
	private static final Color EMPTY_OR_WAITING_COLOR = Color.ORANGERED;
	private static final Color DONE_COLOR = Color.BLACK;
	private static final Color STROKE_COLOR = Color.LIGHTSLATEGRAY;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		initializeSimulation();
		initializeGridPane();
		warehouse.stockProperty().addListener(observable -> Platform.runLater(this::updateRectangles));
		warehouse.stockProperty().addListener(observable -> series.getData().add(new XYChart.Data<>(series.getData().size(), warehouse.getStock())));
		warehouse.stockProperty().addListener(observable -> setConsumerRectangleStatusToDone());
		warehouse.lastShipmentProperty().addListener(observable -> producerRectangle.setFill(DONE_COLOR));
		startSimulation(warehouse, producer, consumer);
	}
	
	private void initializeSimulation () {
		warehouse = new Warehouse(20);
		producer = new Producer(warehouse, 1000, 5000, 5, 10, 35);
		consumer = new Consumer(warehouse, 3000, 6000, 7);
	}
	
	private void initializeGridPane () {
		createStockRectangles();
		createThreadRectanglesAndText();
		createRectangleLegend();
		createLineChart();
		createLoggingListView();
	}
	
	private void createStockRectangles () {
		rectangles = new Rectangle[20];
		for (int column = 0; column < 20; column++) {
			addColumnConstraints();
			Rectangle rectangle = createRectangle(30, 60, EMPTY_OR_WAITING_COLOR);
			rectangles[column] = rectangle;
			addNodeTo(rectangle, column, 2);
		}
	}
	
	private void createThreadRectanglesAndText () {
		producerRectangle = createRectangle(90, 30, FILLED_OR_RUNNING_COLOR);
		addNodeTo(producerRectangle, 5, 1, 3, 1);
		producer.waitingProperty().addListener(observable -> Platform.runLater(() -> updateThreadRectangle(producerRectangle, producer.isWaiting())));
		
		consumerRectangle = createRectangle(90, 30, FILLED_OR_RUNNING_COLOR);
		addNodeTo(consumerRectangle, 14, 1, 3, 1);
		consumer.waitingProperty().addListener(observable -> Platform.runLater(() -> updateThreadRectangle(consumerRectangle, consumer.isWaiting())));
		
		addNodeTo(new Text("Producer Activity:"), 2, 1, 3, 1);
		addNodeTo(new Text("Consumer Activity:"), 11, 1, 3, 1);
	}
	
	private void createRectangleLegend () {
		addNodeTo(createRectangle(30, 30, EMPTY_OR_WAITING_COLOR), 4, 0);
		addNodeTo(new Text(" = Empty/Waiting"), 5, 0, 3, 1);
		addNodeTo(createRectangle(30, 30, FILLED_OR_RUNNING_COLOR), 8, 0);
		addNodeTo(new Text(" = Filled/Running"), 9, 0, 3, 1);
		addNodeTo(createRectangle(30, 30, DONE_COLOR), 12, 0);
		addNodeTo(new Text(" = Done"), 13, 0, 2, 1);
	}
	
	private Rectangle createRectangle (int width, int height, Color color) {
		Rectangle rectangle = new Rectangle(width, height, color);
		rectangle.setStroke(STROKE_COLOR);
		rectangle.setStyle("-fx-arc-height: 15; -fx-arc-width: 15; -fx-padding: 10; -fx-alignment: center-center; -fx-stroke-width: 2;");
		return rectangle;
	}
	
	private void addColumnConstraints () {
		final ColumnConstraints constraint = new ColumnConstraints();
		constraint.setPercentWidth(5);
		constraint.setHalignment(HPos.CENTER);
		pane.getColumnConstraints().add(constraint);
	}
	
	private void updateRectangles () {
		for (int i = 0; i < rectangles.length; i++) {
			if (warehouse.getStock() <= i)
				rectangles[i].setFill(EMPTY_OR_WAITING_COLOR);
			else
				rectangles[i].setFill(FILLED_OR_RUNNING_COLOR);
		}
	}
	
	private void updateThreadRectangle (Rectangle rectangle, boolean waiting) {
		if (waiting)
			rectangle.setFill(EMPTY_OR_WAITING_COLOR);
		else
			rectangle.setFill(FILLED_OR_RUNNING_COLOR);
	}
	
	private void createLineChart () {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Shipment/Consumption");
		xAxis.setMinorTickLength(0);
		yAxis.setLabel("Stock");
		yAxis.setMinorTickLength(0);
		
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Stock Monitoring History");
		
		series = new XYChart.Series<>();
		lineChart.setLegendVisible(false);
		lineChart.getData().add(series);
		
		addNodeTo(lineChart, 0, 3, 20, 1);
	}
	
	private void addNodeTo (Node node, int columnIndex, int rowIndex) {
		addNodeTo(node, columnIndex, rowIndex, 1, 1);
	}
	
	private void addNodeTo (Node node, int columnIndex, int rowIndex, int columnSpan, int rowSpan) {
		GridPane.setRowIndex(node, rowIndex);
		GridPane.setColumnIndex(node, columnIndex);
		GridPane.setColumnSpan(node, columnSpan);
		GridPane.setRowSpan(node, rowSpan);
		pane.getChildren().addAll(node);
	}
	
	private void createLoggingListView () {
		ListView<String> listView = new ListView<>();
		listView.setPrefHeight(200);
		addNodeTo(listView, 0, 4, 20, 1);
		warehouse.addHandler(new Handler() {
			@Override
			public void publish (LogRecord record) {
				Platform.runLater(() -> listView.getItems().add(record.getMessage()));
			}
			
			@Override
			public void flush () {}
			
			@Override
			public void close () throws SecurityException {}
		});
	}
	
	private void setConsumerRectangleStatusToDone () {
		if (warehouse.getStock() == 0 && warehouse.isLastShipment())
			consumerRectangle.setFill(DONE_COLOR);
	}
	
	private void startSimulation (Warehouse warehouse, Producer producer, Consumer consumer) {
		Simulation.simulate(warehouse, producer, consumer);
	}
}
