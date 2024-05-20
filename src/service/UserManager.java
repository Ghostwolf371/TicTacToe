package service;

import model.PlayerData;
import java.sql.*;
import java.util.Optional;

public class UserManager {
    //Database verbindingsinformatie
    private static final String URL = "jdbc:mysql://localhost:3306/tictactoe";
    private static final String USER = "root";
    private static final String PASSWORD = "riecha2005";



    // Methode om een nieuwe gebruiker te registreren
    public PlayerData register(String username, int code, String dateOfBirth) {
        //Controleer als de gebruikersnaam al in gebruik is

        if (isUsernameTaken(username)){
            //Geef een melding als de gebruikersnaam al in gebruik is
            System.out.println("Username is already taken. Please choose a different username.");
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
                throw new SQLException("Creating user failed. Please try again.");
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
                    throw new SQLException("Creating user failed, no ID obtained.");
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

                    int id = resultSet.getInt("player_id");  //Haal de gebruiker id op
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
}
