package labor01.num3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class L01N3_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/labor01/Labor01FX.fxml"));
		primaryStage.setTitle("Binding");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
