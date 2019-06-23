package client;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;


/*
* Controller Class to add functionality to the Gui
* */
public class FxController {
    @FXML
    public Button button;

    @FXML
    public TextField nameField;
    @FXML
    public TextField serverIPField;
    @FXML
    public TextField portField;

    /**
    * Adds functionality to the "connect" Button from the Login Gui
    */
    @FXML
    public void buttonClickHandler(){

        String port = portField.getText();
        // Checks if the input is valid
        if (port.matches("[0-9]+")  && nameField.getText().length()>0 && serverIPField.getText().length()>0) {
            int portValue = Integer.valueOf(port);
            System.out.println("user with the name: "+nameField.getText()+" is trying to connect to Ip: " +serverIPField.getText()+" on Port:" + portField.getText());
            System.out.println("Todo, create Client and Connect");



            //Create and connect Client to Server
            //Client c = new Client(nameField.getText(),portValue, serverIPField.getText());
        }
        else{
            System.out.println("Error: Check your input");

        }
    }
}
