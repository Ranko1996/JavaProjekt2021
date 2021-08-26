package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    @FXML
    Label loggedUserLbl;

    @FXML
    TextField nameInp;

    @FXML
    TextField lastNameInp;

    @FXML
    TextField emailInp;

    @FXML
    PasswordField passwordInp;

    @FXML
    Button addUserBtn;

    @FXML
    Button logOutBtn;

    @FXML
    CheckBox adminChbox;

    @FXML
    Label errorLbl;

    @FXML
    Label succesLbl;

    @FXML
    TableView<User> tableView;

    @FXML
    TableColumn<User, String> firstnameTabCol;

    @FXML
    TableColumn<User, String> lastnameTabCol;

    @FXML
    TableColumn<User, String> emailTabCol;

    @FXML
    public void addUserToDB(javafx.event.ActionEvent actionEvent) throws Exception {
        String email = this.emailInp.getText().toString();
        String lozinka = this.passwordInp.getText().toString();
        String ime = this.nameInp.getText().toString();
        String prezime = this.lastNameInp.getText().toString();

        if (email.equals("") || lozinka.equals("") || ime.equals("") || prezime.equals("")) {
            errorLbl.setText("Morate popuniti sva polja.");
            errorLbl.setVisible(true);
            succesLbl.setVisible(false);
        } else {
            User u = new User();
            if (adminChbox.isSelected()) {
                u.setFirstName(this.nameInp.getText());
                u.setLastName(this.lastNameInp.getText());
                u.setEmail(this.emailInp.getText());
                u.setPassword(this.passwordInp.getText());
                u.setRole("Admin");
            } else {
                u.setFirstName(this.nameInp.getText());
                u.setLastName(this.lastNameInp.getText());
                u.setEmail(this.emailInp.getText());
                u.setPassword(this.passwordInp.getText());
                u.setRole("User");
            }

            try {
                u.save();
                this.populateTableView();
            } catch (Exception e) {
                e.printStackTrace();
            }

            errorLbl.setVisible(false);
            succesLbl.setVisible(true);
            nameInp.setText("");
            lastNameInp.setText("");
            emailInp.setText("");
            passwordInp.setText("");


        }
    }
     @FXML
    public void deleteUserFromDatabase(ActionEvent evt) throws Exception {
        User u = tableView.getSelectionModel().getSelectedItem();
        u.delete();
        this.populateTableView();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loggedUserLbl.setText(
        Login.loggedInUser.getFirstName() + " " +
                Login.loggedInUser.getLastName());
        this.firstnameTabCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastnameTabCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailTabCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        this.tableView.setEditable(true);
        this.firstnameTabCol.setEditable(true);
        this.lastnameTabCol.setEditable(true);

         this.populateTableView();
    }

    @FXML
    public void editUserFirstName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        User u = evt.getRowValue();
        u.setFirstName(evt.getNewValue());
        u.save();
    }
    @FXML
    public void editUserLastName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        User u = evt.getRowValue();
        u.setLastName(evt.getNewValue());
        u.save();
    }


    private void populateTableView(){
            try {
                this.tableView.getItems().setAll((Collection<? extends User>) User.list(User.class));
            } catch (Exception e) {
                System.out.println("Nismo uspjeli dohvatiti podake");
            }
        }

    @FXML
    public void logout(ActionEvent ev) throws IOException {
        Login.loggedInUser = null;
        Main.showWindow(
                getClass(), "../view/Login.fxml",
                "Login to system", 600, 215);
    }
}
