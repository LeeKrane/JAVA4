package labor01.num3;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class L01N3_Controller implements Initializable {
	@FXML
	private Slider slider;
	
	@FXML
	private ChoiceBox<Integer> cb;
	
	@FXML
	private TextField tf;
	
	@FXML
	private ToggleGroup rb;
	
	@Override
	public void initialize (URL location, ResourceBundle resources) {
		initiateChoiceBox();
		initiateBindings();
		refreshResult();
	}
	
	private void initiateBindings () {
		StringBinding binding = Bindings.createStringBinding(this::refreshResult,
															 rb.selectedToggleProperty(),
															 slider.valueProperty(),
															 cb.valueProperty());
		tf.textProperty().bind(binding);
	}
	
	private String refreshResult () {
		int sValue = slider.valueProperty().intValue();
		int cbValue = cb.getValue();
		String result;
		
		try {
			switch (((RadioButton) rb.getSelectedToggle()).getText()) {
				case "Addition" -> result = sValue + " + " + cbValue + " = " + (sValue + cbValue);
				case "Subtraction" -> result = sValue + " - " + cbValue + " = " + (sValue - cbValue);
				case "Multiplication" -> result = sValue + " * " + cbValue + " = " + (sValue * cbValue);
				case "Division" -> result = sValue + " / " + cbValue + " = " + (sValue / cbValue);
				default -> result = "Fehler - Ungueltige Operation.";
			}
		} catch (ArithmeticException e) {
			result = "Fehler - Division durch 0 nicht möglich!";
		}
		return result;
	}
	
	private void initiateChoiceBox () {
		List<Integer> ints = new ArrayList<>();
		IntStream.rangeClosed(0, 10).forEach(ints::add);
		cb.getItems().addAll(ints);
		cb.getSelectionModel().select(1);
	}
}
