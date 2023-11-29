package gui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static Long tryParseToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <T> void formatTableColumnDate(TableColumn<T, LocalDate> tableColumn, String format) {
	    tableColumn.setCellFactory(column -> {
	        TableCell<T, LocalDate> cell = new TableCell<T, LocalDate>() {
	            private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);

	            @Override
	            protected void updateItem(LocalDate item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || item == null) {
	                    setText(null);
	                } else {
	                    setText(dateFormatter.format(item));
	                }
	            }
	        };
	        return cell;
	    });
	}

	public static void formatDatePicker(DatePicker datePicker, String format) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);

		datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					try {
						return LocalDate.parse(string, dateFormatter);
					} catch (DateTimeParseException e) {
				    	Alerts.showAlert("Erro ao converter a data", null, e.getMessage(), AlertType.ERROR);
						return null;
					}
				} else {
					return null;
				}
			}
		});

		datePicker.setPromptText(format.toLowerCase());
	}

}
