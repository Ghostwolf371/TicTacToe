package service;

import model.PlayerData2;

import java.sql.*;
import java.util.Optional;

public class UserManager2 {
    //Database verbindingsinformatie
    private static final String URL = "jdbc:mysql://localhost:3307/tictactoe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    private int id; //Variabele om de gebruikers-id bij te houden



    // Methode om een nieuwe gebruiker te registreren
    public PlayerData2 register(String username, int code, String dateOfBirth) {

        //Controleer als de gebruikersnaam al in gebruik is
        if (isUsernameTaken(username)){
            //Geef een melding als de gebruikersnaam al in gebruik is
            System.out.println("Gebruikersnaam is al in gebruik. Kies alstublieft een andere gebruikersnaam.");
            return null; //Retourneer null als de gebruikersnaam al in gebruik is
        }

        //SQL statement om een nieuwe gebruiker in te voegen in de 'players' tabel
        String sql = "INSERT INTO players (username, code, Date_of_Birth) VALUES (?, ?, ?)";

        //Maak verbinding met de database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

             //Bereid het SQL statement voor en geef aan dat gegenereerde sleutels moeten worden teruggegeven
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //Stel de parameters van het SQL statement in
            statement.setString(1, username);
            statement.setInt(2, code);
            statement.setDate(3, Date.valueOf(dateOfBirth));

            //Voer het SQL statement uit en controleer of er rijen zijn beïnvloed
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                //Als er geen rijen zijn beïnvloed, zet een SQLException
                throw new SQLException("Gebruiker aanmaken mislukt. Probeer het opnieuw.");
            }


            //Haal de gegenereerde sleutels op (in dit geval de id van de gebruiker)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    //Haal de gegenereerde id op

                    int id = generatedKeys.getInt(1);
                    //Maak een nieuw PlayerData object en retourneer dit
                    return new PlayerData2(id, username, code, dateOfBirth);
                }

                else {
                    //Als er geen sleutels zijn gegenereerd, zet een SQLException
                    throw new SQLException("Gebruiker aanmaken mislukt, geen ID verkregen.");
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();  //Print de stacktrace van de SQLException
        }
        return null;  //Retourneer null als de registratie mislukt
    }



    // Methode om een gebruiker in te loggen
    public Optional<PlayerData2> login(String username, int code) {

        //SQL statement om de gebruiker op te halen uit de 'players' tabel
        String sql = "SELECT * FROM players WHERE username = ? AND code = ?";

        //Maak verbinding met de database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

             //Bereid het SQL statement voor
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Stel de parameters van het SQL statement in
            statement.setString(1, username);
            statement.setInt(2, code);

            //Voer het SQL statement uit en verwerk het resultaat
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //Haal de gegevens van de gebruiker op uit het resultaat

                    id = resultSet.getInt("player_id");  //Haal de gebruiker id op
                    String dateOfBirth = resultSet.getDate("Date_of_Birth").toString();  //Haal de geboortedatum op

                    //Maak een nieuw PlayerData object en retourneer dit als een Optional
                    PlayerData2 user = new PlayerData2(id, username, code, dateOfBirth);
                    return Optional.of(user);

                } else {
                    return Optional.empty();  //Retourneer een lege Optional als de gebruiker niet bestaat
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();  //Print de stacktrace van de SQLException
            return Optional.empty();  //Retourneer een lege Optional bij een SQL fout
        }
    }


    //Methode om te controleren of een gebruikersnaam al in gebruik is
    public boolean isUsernameTaken(String username) {

        //SQL statement om het aantal rijen te tellen met de opgegeven gebruikersnaam in de 'players' tabel
        String sql = "SELECT * FROM players WHERE username = ?";

        //Maak verbinding met de database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

             //Bereid het SQL statement voor
             PreparedStatement statement = connection.prepareStatement(sql)){

            //Stel de parameter van het SQL statement in
            statement.setString(1, username);

            //Voer het SQL statement uit en verwerk het resultaat
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {

                    //Haal het aantal rijen op die overeenkomen met de gebruikersnaam
                    int count = resultSet.getInt(1); //Haal de eerste kolomwaarde op
                    return count > 0; //Retourneer true als er minstens 1 rij is gevonden, anders false
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();  //Print de stacktrace van de SQLException
        }

        return false;  //Retourneer false als er een fout optreedt of geen rijen zijn gevonden
    }

    // Methode om de top scores op te halen
    public void getTopScores() {

        // Maak connectie met de database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String query = "SELECT u.username, MAX(s.score) AS score\n" +
                    "FROM scores s\n" +
                    "JOIN players u ON s.score_id = u.player_id\n" +
                    "GROUP BY u.username\n" +
                    "ORDER BY score DESC\n" +
                    "LIMIT 10"; //Query om de top scores op te halen
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Top 10 scores:");
            while (rs.next()) {
                //Toon de gebruikersnaam en bijbehorende score
                System.out.println(rs.getString("username") + ": " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            // Vang eventuele SQL-fouten op en geef een foutmelding
            System.out.println("Fout bij het ophalen van de top scores: " + e.getMessage());
        } finally {
            // Sluit de databaseverbinding om resourcelekken te voorkomen
            if (conn!= null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Fout bij het sluiten van de database verbinding: " + e.getMessage());
                }
            }
        }

    }

    //Methode om de score op te slaan
    public void saveScore(int score,String username) {

        // Maak connectie met de database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Query om de score op te slaan
            String query = "INSERT INTO scores (score_id, score,player) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setInt(2, score);
            pstmt.setString(3, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Vang eventuele SQL-fouten op en geef een foutmelding
            System.out.println("Fout bij het opslaan van de score: " + e.getMessage());
        } finally {
            // Sluit de databaseverbinding om resourcelekken te voorkomen
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Fout bij het sluiten van de database verbinding: " + e.getMessage());
                }
            }
        }
    }
}
