package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User extends Table {


    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String firstName;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String lastName;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String email;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String password;

    @Entity(type = "VARCHAR", size = 5, primary = false, isnull = true)
    String role;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static User login(String email, String password) throws Exception {
        String sql = "SELECT id FROM user WHERE email =? AND password = ?";
        PreparedStatement query = Database.CONNECTION.prepareStatement(sql);
        query.setString(1, email);
        query.setString(2, password);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            return(User)  User.get(User.class, rs.getInt(1));
        }else {
            return null;
        }
    }

}