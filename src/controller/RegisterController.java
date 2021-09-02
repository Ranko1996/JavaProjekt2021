package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import model.User;

import java.io.IOException;
import java.util.Collection;

public class RegisterController {
    @FXML
    Button backToMainBtn;
    @FXML
    Button registerBtn;
    @FXML
    TextField firstNameInput;
    @FXML
    TextField lastNameInput;
    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordInp;
    @FXML
    PasswordField password2Inp;
    @FXML
    Label notificationLbl;

    @FXML
    public void register(javafx.event.ActionEvent actionEvent) throws Exception {
        String email = this.emailInput.getText().toString();
        String lozinka = this.passwordInp.getText().toString();
        String ime = this.firstNameInput.getText().toString();
        String prezime = this.lastNameInput.getText().toString();

        if (email.equals("") || lozinka.equals("") || ime.equals("") || prezime.equals("")) {
            notificationLbl.setText("Morate popuniti sva polja.");
            notificationLbl.setVisible(true);
        } else {
            User u = new User();
                u.setFirstName(this.firstNameInput.getText());
                u.setLastName(this.lastNameInput.getText());
                u.setEmail(this.emailInput.getText());
                u.setPassword(this.passwordInp.getText());
                u.setRole("User");

                try {
                    u.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            notificationLbl.setVisible(false);
            firstNameInput.setText("");
            lastNameInput.setText("");
            emailInput.setText("");
            passwordInp.setText("");


        }

    @FXML
    public void backToLogin(ActionEvent ev) throws IOException {
        Main.showWindow(
                getClass(), "../view/Login.fxml",
                "Login to system", 600, 215);
    }

}
