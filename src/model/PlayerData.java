package model;

public class PlayerData {

    //Prive velden voor de eigenschappen van de speler
    private int id;
    private String username;
    private int code;
    private String dateOfBirth;

    //Constructor om een nieuw PlayerData object te initialiseren
    public PlayerData(int id, String username, int code, String dateOfBirth){
        this.id = id; //Initialiseer het id veld
        this.username = username; //Initialiseer het username veld
        this.code = code; //Initialiseer het code veld
        this.dateOfBirth = dateOfBirth; //Initialiseer het dateOfBirth veld
    }


    //Getter en setter methoden voor het id veld
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    //Getter en setter methoden voor het username veld
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    //Getter en setter methoden voor het code veld
    public int getCode() {return code;}
    public void setCode(int code) {this.code = code;}

    //Getter en setter methoden voor het dateOfBirth veld
    public String getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}

}
