package labor04.num2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class L04N2_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/labor04/num2/L04N2.fxml"));
		primaryStage.setTitle("Warehouse Simulation");
		primaryStage.setScene(new Scene(root, 720, 120));
		//primaryStage.setResizable(false);
		primaryStage.show();
	}
}
