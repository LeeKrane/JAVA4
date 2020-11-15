package labor03.num3;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class L03N3_Controller implements Initializable {
	@FXML
	private GridPane pane;
	
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
			} else
				alert(Alert.AlertType.ERROR, "File doesn't exist", "File doesn't exist", "The given file does not exist!", ButtonType.OK);
		} catch (NullPointerException e) {
			alert(Alert.AlertType.ERROR, "No file selected", "No file selected", "There was no file selected, please select a file!", ButtonType.OK);
		}
	}
	
	private void plot (File selectedFile) {
		try {
			List<TrackPoint> trackPoints = Tools.readCsv(selectedFile.getAbsolutePath());
			
			for (Node node : pane.getChildren()) {
				if (node instanceof ScatterChart) {
					pane.getChildren().remove(node);
					break;
				}
			}
			
			correctMinMax();
			final NumberAxis xAxis = new NumberAxis(Integer.parseInt(xMin.getText()), Integer.parseInt(xMax.getText()), 10.0);
			final NumberAxis yAxis = new NumberAxis(Integer.parseInt(yMin.getText()), Integer.parseInt(yMax.getText()), 10.0);
			
			sc = new ScatterChart<>(xAxis, yAxis);
			xAxis.setLabel("geographic latitude");
			yAxis.setLabel("geographic longitude");
			sc.setTitle("Track Points");
			sc.setLegendVisible(false);
			
			XYChart.Series<Number, Number> trackPointSeries = new XYChart.Series<>();
			for (TrackPoint tp : trackPoints) {
				trackPointSeries.getData().add(new XYChart.Data<>(tp.getGeoLatitude(), tp.getGeoLongitude()));
			}
			
			sc.getData().add(trackPointSeries);
			pane.add(sc, 0, 0, 5, 1);
		} catch (IOException e) {
			alert(Alert.AlertType.ERROR, "IOException", "IOException", e.getMessage(), ButtonType.OK);
		} catch (NumberFormatException e) {
			alert(Alert.AlertType.ERROR, "Not a number", "Not a number", "One of the given interval numbers is not a number!", ButtonType.OK);
		}
	}
	
	private void correctMinMax () {
		String h;
		if (Integer.parseInt(xMin.getText()) > Integer.parseInt(xMax.getText())) {
			h = xMax.getText();
			xMax.setText(xMin.getText());
			xMin.setText(h);
		}
		if (Integer.parseInt(yMin.getText()) > Integer.parseInt(yMax.getText())) {
			h = yMax.getText();
			yMax.setText(yMin.getText());
			yMin.setText(h);
		}
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
		fileChooser.setTitle("Select a csv file (*.csv) containing track points.");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.csv"));
		// This line is for quick testing purposes only
		fileChooser.setInitialDirectory(new File("C:\\Development\\Java\\JAVA4\\src\\main\\resources\\labor03"));
	}
}
