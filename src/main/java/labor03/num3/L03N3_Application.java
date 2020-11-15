package labor03.num3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class L03N3_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/labor03/L03N3.fxml"));
		primaryStage.setTitle("Track Point Visualizer");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
