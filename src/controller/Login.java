package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {
    public static User loggedInUser;

    @FXML
    Button prijaviSeBtn;

    @FXML
    TextField korisnickoImeTxt;

    @FXML
    TextField lozinkaTxt;

    @FXML
    Label greskaLbl;

    @FXML
    Label uspjehLbl;

    @FXML
    Button registerBtn;


    @FXML
    public void register(ActionEvent ev) throws IOException {
        Main.showWindow(
                getClass(), "../view/Register.fxml", "Registracija na sustav",
                600, 215);
    }

    @FXML
    public void prijaviSe(ActionEvent ev){
        String korisnickoIme = this.korisnickoImeTxt.getText().toString();
        String lozinka = this.lozinkaTxt.getText().toString();
        if(korisnickoIme.equals("") || lozinka.equals("")){
            greskaLbl.setVisible(true);
            uspjehLbl.setVisible(false);
        }else{
            greskaLbl.setVisible(false);
            uspjehLbl.setVisible(true);

            try {
                Login.loggedInUser = User.login(korisnickoIme, lozinka);
                if(korisnickoIme != null){
                    if(Login.loggedInUser.getRole().equals("Admin")){
                    Main.showWindow(
                            getClass(), "../view/Admin.fxml",
                            "Dobrodošli u administraciju", 600, 400);}
                    else{
                        Main.showWindow(
                                getClass(),
                                "../view/Notes.fxml", "Dobrodošli", 1200, 1200);
                    }
                } else {
                    greskaLbl.setText("Unesite ispravne podatke");
                    greskaLbl.setVisible(false);
                    uspjehLbl.setVisible(true);
                }
            } catch (Exception e) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
                System.out.println("Došlo je do pogreške");
            }
        }
    }
}
