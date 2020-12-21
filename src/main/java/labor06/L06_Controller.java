package labor06;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import labor06.model.Schueler;
import labor06.model.SchuelerTools;
import labor06.persistence.JdbcSchuelerRepository;
import labor06.persistence.SchuelerRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class L06_Controller implements Initializable {
	@FXML
	private TextField tfInput;
	
	@FXML
	private Button btnFindSchuelerByKlasse;
	
	@FXML
	private Button btnFindSchuelerByGeschlecht;
	
	@FXML
	private Button btnGetKlassen;
	
	@FXML
	private ListView<String> lvContent;
	
	private Connection connection;
	private SchuelerRepository repository;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		initializeRepositoryWithExceptionHandling();
		btnFindSchuelerByKlasse.setOnAction(actionEvent -> findSchuelerByKlasse());
		btnFindSchuelerByGeschlecht.setOnAction(actionEvent -> findSchuelerByGeschlecht());
		btnGetKlassen.setOnAction(actionEvent -> getKlassen());
	}
	
	private void initializeRepositoryWithExceptionHandling () {
		try {
			initializeRepository();
			Platform.runLater(() ->tfInput.getScene().getWindow().setOnCloseRequest(windowEvent -> {
				try {
					connection.close();
				} catch (SQLException e) {
					exceptionAlert(e, "SQLException");
				}
			}));
		} catch (SQLException e) {
			exceptionAlert(e, "SQLException");
		} catch (IOException e) {
			exceptionAlert(e, "IOException");
		}
	}
	
	private void initializeRepository () throws IOException, SQLException {
		InputStream inputStream = L06_Controller.class.getResourceAsStream("/labor06/connection.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		final String jdbc_url = properties.getProperty("jdbc_url");
		final String jdbc_user = properties.getProperty("jdbc_user");
		final String jdbc_pwd = properties.getProperty("jdbc_pwd");
		connection = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_pwd);
		repository = new JdbcSchuelerRepository(connection);
		repository.persistSchueler(SchuelerTools.readFromCSV("schueler.csv"));
	}
	
	private void findSchuelerByKlasse () {
		String klasse = tfInput.getText();
		tfInput.clear();
		
		if (!klasse.isEmpty()) {
			List<Schueler> schuelerList = new ArrayList<>();
			try {
				schuelerList = repository.findSchuelerByKlasse(klasse);
			} catch (SQLException e) {
				alert(Alert.AlertType.ERROR, "Invalid class name!", "The input text field mut contain classes in the pattern [1-5][A-C]HIF for this operation!", ButtonType.OK);
			}
			
			lvContent.getItems().clear();
			lvContent.getItems().addAll(schuelerList.stream()
												.map(Schueler::toString)
												.collect(Collectors.toList()));
		} else {
			alert(Alert.AlertType.ERROR, "No input!", "The input text field must not be empty for this operation!", ButtonType.OK);
		}
	}
	
	private void findSchuelerByGeschlecht () {
		String geschlecht = tfInput.getText();
		tfInput.clear();
		
		if (geschlecht.length() == 1) {
			List<Schueler> schuelerList = new ArrayList<>();
			try {
				schuelerList = repository.findSchuelerByGeschlecht(geschlecht.charAt(0));
			} catch (SQLException e) {
				alert(Alert.AlertType.ERROR, "Invalid gender!", "The input text field must contain either m or w for this operation!");
			}
			
			lvContent.getItems().clear();
			lvContent.getItems().addAll(schuelerList.stream()
												.map(Schueler::toString)
												.collect(Collectors.toList()));
		} else {
			alert(Alert.AlertType.ERROR, "Invalid input!", "The input text field must contain 1 letter (m or w) for this operation!!", ButtonType.OK);
		}
	}
	
	private void getKlassen () {
		if (tfInput.getText().isEmpty()) {
			Map<String, Integer> klassen = new HashMap<>();
			try {
				klassen = repository.getKlassen();
			} catch (SQLException e) {
				exceptionAlert(e, "SQLException");
			}
			
			lvContent.getItems().clear();
			for (Map.Entry<String, Integer> entry : klassen.entrySet())
				lvContent.getItems().add(entry.getKey() + "=" + entry.getValue());
			Collections.sort(lvContent.getItems());
		} else {
			tfInput.clear();
			alert(Alert.AlertType.ERROR, "Invalid input!", "The input text field must be empty for this operation!", ButtonType.OK);
		}
	}
	
	private void exceptionAlert (Exception e, String header) {
		alert(Alert.AlertType.ERROR, header, e.getMessage(), ButtonType.OK);
	}
	
	private void alert (Alert.AlertType type, String header, String content, ButtonType... buttonTypes) {
		Alert alert = new Alert(type, header, buttonTypes);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
