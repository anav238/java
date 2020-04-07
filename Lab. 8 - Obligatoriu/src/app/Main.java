package app;

import DAO.ArtistController;
import DAO.MusicController;
import app.Database;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Database.getInstance();
        ArtistController artistController = new ArtistController();
        artistController.create("Artist1", "Tara1");
        artistController.create("Artist2", "Tara2");
        artistController.create("Artist3", "Tara3");
        String artistToSearch = "Artist2";
        List<Integer> artistIds = artistController.findByName(artistToSearch);
        for (Integer artistId:artistIds)
            System.out.println(artistToSearch + " has id: " + artistId);

        MusicController musicController = new MusicController();
        musicController.create("Album1", 1, 2000);
        musicController.create("Album2", 2, 2005);
        musicController.create("Album3", 3, 2010);
        musicController.create("Album4", 1, 2015);

        int artistId = 1;
        List<String> albums = musicController.findByArtist(artistId);
        for (String album:albums)
            System.out.println("Artist with id " + 1 + " has album: " + album);
        try {
            Database.getInstance().close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
