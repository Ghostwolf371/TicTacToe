package service;

import model.PlayerData;

import java.sql.*;
import java.util.Optional;

public class UserManager {
    //Database verbindingsinformatie
    private static final String URL = "jdbc:mysql://localhost:3307/tictactoe";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    private int id; //Variabele om de gebruikers-id bij te houden



    // Methode om een nieuwe gebruiker te registreren
    public PlayerData register(String username, int code, String dateOfBirth) {

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
                    return new PlayerData(id, username, code, dateOfBirth);
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
    public Optional<PlayerData> login(String username, int code) {

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
                    PlayerData user = new PlayerData(id, username, code, dateOfBirth);
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
    public void saveScore(int score) {
        // Maak verbinding met de database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);


            // Bereid SQL-statements voor om uit te voeren
            Statement stmt = conn.createStatement();

            // Controleer of er al een score bestaat voor het gegeven ID
            String checkIfExistsQuery = "SELECT COUNT(*) AS count FROM scores WHERE score_id =?";
            PreparedStatement checkIfExistsPstmt = conn.prepareStatement(checkIfExistsQuery);
            checkIfExistsPstmt.setInt(1, id);
            ResultSet rs = checkIfExistsPstmt.executeQuery();

            if (rs.next() && rs.getInt("count") > 0) {
                // Score bestaat al, update deze
                String updateQuery = "UPDATE scores SET score = score +? WHERE score_id =?";
                PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
                updatePstmt.setInt(1, score);
                updatePstmt.setInt(2, id);
                int rowsUpdated = updatePstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Score succesvol bijgewerkt.");  // Geef een melding als de score succesvol is bijgewerkt
                } else {
                    System.out.println("Geen record gevonden met het opgegeven ID.");  // Geef een melding als er geen record is gevonden met het opgegeven ID
                }
            } else {
                // Indien geen bestaande score, voeg een nieuwe toe
                String insertQuery = "INSERT INTO scores (score_id, score) VALUES (?,?)";
                PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
                insertPstmt.setInt(1, id);
                insertPstmt.setInt(2, score);
                int rowsInserted = insertPstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Nieuwe score succesvol toegevoegd.");  // Geef een melding als de nieuwe score succesvol is toegevoegd
                } else {
                    System.out.println("Toevoegen van nieuwe score is mislukt.");  // Geef een melding als het toevoegen van de nieuwe score is mislukt
                }
            }

        } catch (SQLException e) {
            // Log de foutmelding of handel deze op de juiste manier af
            System.err.println("Fout bij het beheren van de score: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for further investigation
        } finally {
            // Zorg ervoor dat de verbinding wordt gesloten om resourcelekken te voorkomen
            if (conn!= null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Fout bij het sluiten van de databaseverbinding: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}
