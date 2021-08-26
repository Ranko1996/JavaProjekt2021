package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Table.create(User.class);
        //Table.create(Event.class);

        /*User ranko = new User();
        ranko.setFirstName("Ranko");
        ranko.setLastName("Koturic");
        ranko.setEmail("ranko@mail.com");
        ranko.setPassword("1234");
        ranko.setRole("Admin");
        ranko.save(); */

        Main.primaryStage = primaryStage;
        Main.showWindow(getClass(), "../view/Login.fxml", "Prijavite se na sustav",600, 215);
}

    public static void showWindow(Class windowClass, String viewName, String Title, int w, int h) throws IOException {
        Parent root = FXMLLoader.load(windowClass.getResource(viewName));
        primaryStage.setTitle(Title);
        primaryStage.setScene(new Scene(root, w, h));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

       /*User.add(new User("Ranko","Koturic","koturic@mail.com","Ranko"));

        List<User> users = User.dohvatiUsere();
        for(User user : users)
            System.out.println(user); */

    }
}
