package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Event;
import model.Note;
import model.User;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class EventController  implements Initializable {
    @FXML
    Button newEventBtn;

    @FXML
    Button deleteBtn;

    @FXML
    TextField newEventInp;

    @FXML
    Label errorLbl;

    @FXML
    Label successLbl;

    @FXML
    TextField eventDescriptionInp;

    @FXML
    TextField eventPlaceInp;

    @FXML
    DatePicker dateInp ;

    @FXML
    TableView<Event> tableView;

    @FXML
    TableColumn<Event, String> nameTblClmn;

    @FXML
    TableColumn<Event, String> descriptionTblClmn;

    @FXML
    TableColumn<Event, String> placeTblClmn;

    @FXML
    TableColumn<Event, String> dateTblClmn;

    //postavljamo vrijednosti koje ćemo prikazati u initialize
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        this.nameTblClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.descriptionTblClmn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.placeTblClmn.setCellValueFactory(new PropertyValueFactory<>("place"));
        this.dateTblClmn.setCellValueFactory(new PropertyValueFactory<>("date"));

        this.populateTableView();

    }
    //Listu objekat pretvara u kolekciju evenata
    private void populateTableView() {
        try {
            this.tableView.getItems().setAll((Collection<?extends Event>) Event.usersList(Event.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }

    @FXML
    public void addEvent(ActionEvent ev){
        String event = this.newEventInp.getText().toString();
        String description = this.eventDescriptionInp.getText().toString();
        String place = this.eventPlaceInp.getText().toString();
        dateInp.setValue(LocalDate.now());
        LocalDate ld = dateInp.getValue();
        Calendar c =  Calendar.getInstance();

        c.set(ld.getYear(), ld.getMonthValue());
        Date date = c.getTime();

        if (event.equals("") || description.equals("") || place.equals((""))){
            errorLbl.setText ("Niste dodali događaj.");
            successLbl.setVisible(false);
            errorLbl.setVisible(true);
        } else {
            Event n= new Event();
            n.setName(this.newEventInp.getText());
            n.setDescription(this.eventDescriptionInp.getText());
            n.setPlace(this.eventPlaceInp.getText());
            n.setUserFK(Login.loggedInUser.getId());


            n.setDate(date);
            System.out.println(date);
            newEventInp.setText("");
            eventDescriptionInp.setText("");
            eventPlaceInp.setText("");
            successLbl.setVisible(true);
            errorLbl.setVisible(false);
            try {
                n.save();
                this.populateTableView();
            } catch (Exception ex) {
                ex.printStackTrace();
                errorLbl.setText("Došlo je do pogreške: dodavanje nove poruke nije uspjelo.");
                successLbl.setVisible(false);
            }
        }
    }

    @FXML
    public void deleteEvent (ActionEvent ev) throws Exception {
        Event n = tableView.getSelectionModel().getSelectedItem();
        n.delete();
        this.populateTableView();
    }


}