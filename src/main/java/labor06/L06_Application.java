package labor06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class L06_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		URL resource = getClass().getResource("/labor06/JDBC_FX.fxml");
		Parent root = FXMLLoader.load(resource);
		primaryStage.setTitle("JDBC Visualizer");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
