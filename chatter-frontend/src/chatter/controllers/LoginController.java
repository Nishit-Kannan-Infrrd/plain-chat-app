package chatter.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class LoginController implements Initializable
{

    private static final Logger LOGGER = Logger.getLogger( LoginController.class.getName() );

    @FXML private TextField txtEmailAddressLogin;
    @FXML private PasswordField pwdFldPasswordLogin;
    @FXML private Button btnEnterLogin;
    @FXML private Button btnLeaveLogin;
    @FXML private Label lblErrorMessageLogin;


    @Override
    public void initialize( URL url, ResourceBundle resourceBundle )
    {

    }


    public void login( ActionEvent actionEvent ) throws IOException
    {
        LOGGER.info( () -> "Logging in." );
        Parent root = FXMLLoader.load( getClass().getResource( "../chat.fxml" ) );
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.show();
        // lblErrorMessageLogin.setText( "Oops! Wrong email address/ password." );

    }


    public void close( ActionEvent actionEvent )
    {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void signUp(ActionEvent actionEvent) throws Exception{
        LOGGER.info( () -> "Signing up." );
        Parent root = FXMLLoader.load( getClass().getResource( "../signup.fxml" ) );
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.show();
    }


}
