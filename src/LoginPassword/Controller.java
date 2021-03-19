package LoginPassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controller {
    //Access variable from FXML:
    //<Text fx:id="actiontarget"
    //   GridPane.columnIndex="1" GridPane.rowIndex="6"/>
    @FXML private Text actiontarget;

    //Implements Event in a Button from FXML:
    //<Button text="Sign In"
    //   onAction="#handleSubmitButtonAction"/>
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

}