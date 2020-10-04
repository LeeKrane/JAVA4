package labor01.num2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class L01N2_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
	@FXML
	private ChoiceBox<Integer> cb;
	
	@FXML
	private Button btn;
	
	@FXML
	private TextField xMax;
	
	@FXML
	private TextField yMax;
	
	@FXML
	private TextField xMin;
	
	@FXML
	private TextField yMin;
	
	private FileChooser fileChooser;
	private ScatterChart<Number, Number> sc;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		fileChooserSetup();
		choiceBoxSetup();
		btn.setOnAction(event -> plotValidation());
	}
	
	private void plotValidation () {
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		
		try {
			if (selectedFile.exists()) {
				if (!selectedFile.canRead())
					alert(Alert.AlertType.ERROR, "Illegal file location", "Illegal file location", "The given file location is illegal due to missing access permissions!", ButtonType.OK);
				else if (!selectedFile.isFile())
					alert(Alert.AlertType.ERROR, "No file", "No file", "The given file location is not a file!", ButtonType.OK);
				else
					plot(selectedFile);
			}
			else
				alert(Alert.AlertType.ERROR, "File doesn't exist", "File doesn't exist", "The given file does not exist!", ButtonType.OK);
		} catch (NullPointerException e) {
			alert(Alert.AlertType.ERROR, "No file selected", "No file selected", "There was no file selected, please select a file!", ButtonType.OK);
		}
	}
	
	private void plot (File selectedFile) {
		try {
			int quadrant = cb.getSelectionModel().getSelectedItem();
			List<Point> points = Tools.readPointsFromQuadrant(selectedFile.getAbsolutePath(), quadrant);
			
			for (Node node : pane.getChildren()) {
				if (node instanceof ScatterChart) {
					pane.getChildren().remove(node);
					break;
				}
			}
			
			final NumberAxis xAxis = new NumberAxis(Integer.parseInt(xMin.getText()), Integer.parseInt(xMax.getText()), 10.0);
			final NumberAxis yAxis = new NumberAxis(Integer.parseInt(yMin.getText()), Integer.parseInt(yMax.getText()), 10.0);
			
			sc = new ScatterChart<>(xAxis, yAxis);
			xAxis.setLabel("x axis");
			yAxis.setLabel("y axis");
			sc.setTitle("Points of Quadrant " + cb.getSelectionModel().getSelectedItem());
			sc.setLegendVisible(false);
			
			XYChart.Series<Number, Number> pointSeries = new XYChart.Series<>();
			for (Point p : points) {
				pointSeries.getData().add(new XYChart.Data<>(p.getX(), p.getY()));
			}
			
			sc.getData().add(pointSeries);
			pane.add(sc, 0, 0, 5, 1);
		} catch (IOException e) {
			alert(Alert.AlertType.ERROR, "IOException", "IOException", e.getMessage(), ButtonType.OK);
		} catch (NumberFormatException e) {
			alert(Alert.AlertType.ERROR, "Not a number", "Not a number", "One of the given interval numbers is not a number!", ButtonType.OK);
		}
	}
	
	private int getMinMaxPerQuadrant (int quadrant, boolean x, boolean min) {
		return switch (quadrant) {
			case 1 -> min ? 0 : 50;
			case 2 -> min ? (x ? -50 : 0) : (x ? 0 : 50);
			case 3 -> min ? -50 : 0;
			case 4 -> min ? (x ? 0 : -50) : (x ? 50 : 0);
			default -> 0;
		};
	}
	
	private void alert (Alert.AlertType alertType, String title, String header, String content, ButtonType... buttonTypes) {
		Alert alert = new Alert(alertType, "", buttonTypes);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	private void fileChooserSetup () {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select a data file (*.dat) containing points.");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
		fileChooser.setInitialDirectory(new File("C:\\Developement\\Java\\JAVA4\\src\\main\\resources\\labor01"));
	}
	
	private void choiceBoxSetup () {
		cb.getItems().addAll(1, 2, 3, 4);
		cb.getSelectionModel().select(0);
	}
}
