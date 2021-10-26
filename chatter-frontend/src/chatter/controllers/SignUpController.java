package chatter.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class SignUpController implements Initializable
{
    private static final Logger LOGGER = Logger.getLogger( SignUpController.class.getName() );

    @FXML
    private Button btnLeaveSignUp;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle )
    {

    }

    public void close( ActionEvent actionEvent )
    {
        LOGGER.info( () -> "Exiting chatter." );
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
