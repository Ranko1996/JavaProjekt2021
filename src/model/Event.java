package model;

import controller.Login;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;

public class Event extends Table {

    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String name;

    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String description;

    @Entity(type = "DATETIME")
    Date date;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String place;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public User getUserFK() throws Exception { return (User)Table.get(User.class, userFK); }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public static List<?> allEvents(Class cls) throws Exception {
        int fk = Login.loggedInUser.getId();
        String SQL = "SELECT * FROM event WHERE userFK = "+fk+";";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> usersEvents = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            usersEvents.add(obj);
        }
        return usersEvents;
    }


}