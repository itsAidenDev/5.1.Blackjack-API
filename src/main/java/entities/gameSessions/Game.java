package entities.gameSessions;

import entities.Player;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Game {

    @Setter
    @Getter
    @Id
    private String id;
    @Getter
    private List<Player> players = new ArrayList<>();
    private List<Turn> turnsPlayed = new ArrayList<>();
    @Getter
    @Setter
    private Turn activeTurn;
    @Setter
    private boolean gameOn;

    public List<Turn> getTurnsPlayed() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(List<Turn> turnsPlayed) {
        this.turnsPlayed = new ArrayList<>(turnsPlayed);
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public boolean isGameOn() {
        return (getActiveTurn() != null);
    }

    @Override
    public String toString() {
        return "Game{ id = " + id + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameOn == game.gameOn && Objects.equals(id, game.id) && Objects.equals(players, game.players) && Objects.equals(turnsPlayed, game.turnsPlayed) && Objects.equals(activeTurn, game.activeTurn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, players, turnsPlayed, activeTurn, gameOn);
    }

}