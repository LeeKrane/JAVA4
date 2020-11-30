package labor05.num2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class L05N2_Controller implements Initializable, ObservableTreeSetListener<Integer> {
	@FXML
	private ListView<ObservableTreeSetEvent<Integer>> lvEventLog;
	
	@FXML
	private ChoiceBox<EventTrigger> choiceOperation;
	
	@FXML
	private Button btnExecute;
	
	@FXML
	private TextField tfInput;
	
	@FXML
	private CheckBox cbLogObserver;
	
	@FXML
	private CheckBox cbGUIObserver;
	
	@FXML
	private ListView<Integer> lvTreeSet;
	
	private final ObservableTreeSet<Integer> treeSet = new ObservableTreeSet<>();
	private Logger logger;
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		choiceBoxSetup();
		createLogger();
		checkBoxSetup();
		treeSet.addObservableTreeSetListener(this);
		executionSetup();
	}
	
	private void createLogger () {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("src/main/resources/labor05/ObservableTreeSetEvents.log", false);
			logger = Logger.getGlobal();
			logger.setLevel(Level.INFO);
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
	}
	
	private void checkBoxSetup () {
		cbLogObserver.setSelected(true);
		cbGUIObserver.setSelected(true);
	}
	
	private void choiceBoxSetup () {
		choiceOperation.getItems().addAll(EventTrigger.values());
		choiceOperation.getSelectionModel().select(0);
	}
	
	private void executionSetup () {
		btnExecute.setOnAction(actionEvent -> {
			try {
				switch (choiceOperation.getSelectionModel().getSelectedItem()) {
					case ADD -> treeSet.add(checkSingleInput());
					case REMOVE -> treeSet.remove(checkSingleInput());
					case ADD_ALL -> treeSet.addAll(checkMultipleInput());
					case POLL_FIRST -> {
						checkEmptyInput();
						treeSet.pollFirst();
					}
					case POLL_LAST -> {
						checkEmptyInput();
						treeSet.pollLast();
					}
					case REMOVE_ALL -> treeSet.removeAll(checkMultipleInput());
					case RETAIN_ALL -> treeSet.retainAll(checkMultipleInput());
					default -> throw new IllegalArgumentException("This Error should not be achievable!");
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "NumberFormatException", ButtonType.OK);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			} catch (IllegalArgumentException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "IllegalArgumentException", ButtonType.OK);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			tfInput.clear();
		});
	}
	
	private int checkSingleInput () {
		return Integer.parseInt(tfInput.getText());
	}
	
	private List<Integer> checkMultipleInput () {
		String[] split = tfInput.getText().split(";");
		if (split.length < 1)
			throw new IllegalArgumentException("Too few arguments!");
		List<Integer> list = new ArrayList<>();
		for (String s : split)
			list.add(Integer.parseInt(s));
		return list;
	}
	
	private void checkEmptyInput () {
		if (!tfInput.getText().equals(""))
			throw new IllegalArgumentException("Too many arguments!");
	}
	
	@Override
	public void observableTreeSetChanged (ObservableTreeSetEvent<Integer> e) {
		lvTreeSet.getItems().clear();
		lvTreeSet.getItems().addAll(treeSet);
		if (cbGUIObserver.isSelected())
			lvEventLog.getItems().add(e);
		if (cbLogObserver.isSelected())
			logToFile(e);
	}
	
	private void logToFile (ObservableTreeSetEvent<Integer> e) {
		logger.info(e.toString());
	}
}
