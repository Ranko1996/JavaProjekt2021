package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Note;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class NotesContoller implements Initializable {

    @FXML
    Button newNoteBtn;
    @FXML
    Button logOutButton;
    @FXML
    Button closeBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Label nameLbl;
    @FXML
    Label textLbl;
    @FXML
    Label contactLbl;
    @FXML
    TextField nameTxt;
    @FXML
    TextArea noteTxt;
    @FXML
    Label successLbl;
    @FXML
    Label errorLbl;
    @FXML
    TableView <Note> allNotesTableView;
    @FXML
    TableColumn <Note, String> titleTblCol;
    @FXML
    TableColumn <Note, String> textTblCol;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.titleTblCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.textTblCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        this.populateAllNotesTableView();
        this.titleTblCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.textTblCol.setCellValueFactory(new PropertyValueFactory<>("text"));

        this.allNotesTableView.setEditable(true);
        this.textTblCol.setEditable(true);
        this.titleTblCol .setEditable(true);
        this.populateAllNotesTableView();
    }

    private void populateAllNotesTableView() {
        try {
            this.allNotesTableView.getItems().setAll((Collection<? extends Note>) Note.allNotes(Note.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }

    @FXML
    public void logOut(ActionEvent ev) throws IOException {
        Login.loggedInUser = null;
        Main.showWindow(
                getClass(), "../view/Login.fxml",
                "Login to system", 600, 215);
    }

    @FXML
    public void deleteNote (ActionEvent ev) throws Exception {
        Note n = allNotesTableView.getSelectionModel().getSelectedItem();
        n.delete();
        this.populateAllNotesTableView();
    }
    @FXML
    public void addNew(ActionEvent ev){
        String title = this.nameTxt.getText().toString();
        String note = this.noteTxt.getText().toString();
        if (title.equals("") || note.equals("")){
            errorLbl.setText ("Niste dodali poruku.");
            successLbl.setVisible(false);
            errorLbl.setVisible(true);
        } else {
            Note n= new Note();
            n.setTitle(this.nameTxt.getText());
            n.setText(this.noteTxt.getText());
            n.setUserFK(Login.loggedInUser.getId());
            nameTxt.setText("");
            noteTxt.setText("");
            successLbl.setVisible(true);
            errorLbl.setVisible(false);
            try {
                n.save();
                this.populateAllNotesTableView();
            } catch (Exception ex) {
                ex.printStackTrace();
                errorLbl.setText("Došlo je do pogreške: dodavanje nove poruke nije uspjelo.");
                successLbl.setVisible(false);
            }}}

}


