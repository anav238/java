package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Database;

public class ArtistController {
    Connection connection;

    public ArtistController() {
        this.connection = Database.getInstance();
    }

    public void create(String name, String country) {
        try {
            String query = "INSERT INTO artists (name, country) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<Integer> findByName(String name) {
        List<Integer> ids = new ArrayList<>();
        try {
            String query = "SELECT id FROM artists WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        }
        catch (SQLException e) {
            e.getMessage();
        }
        return ids;
    }
}
