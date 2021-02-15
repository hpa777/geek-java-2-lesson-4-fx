package calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public GridPane gridPane;
    public Label label;

    private boolean operatePress;
    private boolean dotPress;
    private boolean sqrPress;

    private void initFlags() {
        operatePress = true;
        dotPress = true;
        sqrPress = false;
    }

    @FXML
    public void initialize() {
        this.initFlags();
        String btnText = "7894561230";
        int count = 0;
        for (int rowIdx = 1; rowIdx < 5; rowIdx++) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                if (count < btnText.length()) {
                    Button button = new Button(String.valueOf(btnText.charAt(count)));
                    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    GridPane.setFillWidth(button, true);
                    GridPane.setFillHeight(button, true);
                    GridPane.setMargin(button, new Insets(5,5,5,5));
                    button.setOnAction(event -> {
                        if (sqrPress) {
                            return;
                        }
                        label.setText(label.getText().concat(((Button)event.getSource()).getText()));
                        if (operatePress) {
                            dotPress = false;
                        }
                        operatePress = false;
                    });
                    gridPane.add(button, colIdx, rowIdx);
                    count++;
                }
            }
        }
    }

    @FXML
    public void pressOperateButton(ActionEvent actionEvent) {
        String txt = ((Button)actionEvent.getSource()).getText();
        if (!operatePress) {
            if (!txt.equals("sqr")) {
                operatePress = true;
                sqrPress = false;
            } else {
                if (sqrPress) {
                    return;
                }
                sqrPress = true;
            }
            label.setText(label.getText().concat(txt));
        }
    }

    @FXML
    public void pressDotButton() {
        if (!dotPress) {
            label.setText(label.getText().concat("."));
            dotPress = true;
        }
    }
    @FXML
    public void pressEqualsButton() {
        Pattern pattern = Pattern.compile("(\\d+[.]?\\d*)(sqr)?(([\\+\\-\\*\\/]))?");
        Matcher matcher = pattern.matcher(label.getText());
        double result = 0;
        String operator = null;
        while (matcher.find()) {
            double operand = Double.parseDouble(matcher.group(1));
            if (matcher.group(2) != null) {
                operand *= operand;
            }
            if (operator == null) {
                result = operand;
            } else {
                switch (operator) {
                    case "+": {
                        result += operand;
                        break;
                    }
                    case "-": {
                        result -= operand;
                        break;
                    }
                    case "*": {
                        result *= operand;
                        break;
                    }
                    case "/": {
                        result /= operand;
                        break;
                    }
                }
            }
            if (matcher.group(3) != null) {
                operator = matcher.group(3);
            }
        }
        label.setText(String.valueOf(result));
        dotPress = true;
    }

    public void pressClearButton(ActionEvent actionEvent) {
        label.setText("");
    }
}
