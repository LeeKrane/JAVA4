package labor05.num2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class L05N2_Application extends Application {
	@Override
	public void start (Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/labor05/ObservableTreeSetFX.fxml"));
		primaryStage.setTitle("ObservableTreeSet GUI");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
