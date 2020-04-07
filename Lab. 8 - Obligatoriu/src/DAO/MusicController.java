package DAO;

import app.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicController {
    Connection connection;

    public MusicController() {
        this.connection = Database.getInstance();
    }

    public void create(String name, int artistId, int releaseYear) {
        try {
            String query = "INSERT INTO albums (name, artist_id, release_year) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, artistId);
            preparedStatement.setInt(3, releaseYear);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<String> findByArtist(int artistId) {
        List<String> albums = new ArrayList<>();
        try {
            String query = "SELECT name FROM albums WHERE artist_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, artistId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                albums.add(rs.getString("name"));
        }
        catch (SQLException e) {
            e.getMessage();
        }
        return albums;
    }
}
