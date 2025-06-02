package entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String name;
    private int totalWins;
    private int totalGames;

    // Constructor, getters y setters
    public Player(String name) {
        this.name = name;
        this.totalWins = 0;
        this.totalGames = 0;
    }
}