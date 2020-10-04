package labor01.num2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class L01N2_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/labor01/L01N2.fxml"));
		primaryStage.setTitle("Scatterplot");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
