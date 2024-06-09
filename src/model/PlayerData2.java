package model;

public class PlayerData2 {

    //Prive velden voor de eigenschappen van de speler
    private int id;
    private String username;
    private int code;
    private String dateOfBirth;

    //Constructor om een nieuw PlayerData object te initialiseren
    public PlayerData2(int id, String username, int code, String dateOfBirth){
        this.id = id; //Initialiseer het id veld
        this.username = username; //Initialiseer het username veld
        this.code = code; //Initialiseer het code veld
        this.dateOfBirth = dateOfBirth; //Initialiseer het dateOfBirth veld
    }


    //Getter en setter methoden voor het username veld
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}



}
