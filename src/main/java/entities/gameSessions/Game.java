package entities.gameSessions;

import utils.GameStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@Getter
@Setter
@Document(collection = "games")
public class Game {
    @Id
    private Long gameId;
    private String playerId;
    //private Date createdAt;
    private GameStatus status;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean playerTurn;

    private int playerScore;
    private int dealerScore;
    private List<String> playerCards;
    private List<String> dealerCards;
    private int bet;

    public Game() {
     //   this.createdAt = new Date();
    }

    public Game(Long playerId, int initialBet) {
        if(initialBet <= 0) {
            throw new InvalidActionException("La apuesta no puede ser 0");
        }
        this.gameId = UUID.randomUUID().toString();
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.playerId = playerId;
        this.bet = initialBet;
        this.status = GameStatus.IN_PROGRESS;
        this.playerTurn = true;
    }

    public void dealInitialCards() {
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    public void checkInitialBlackjack() {
        boolean playerBlackjack = playerHand.isBlackjack();
        boolean dealerBlackjack = dealerHand.isBlackjack();

        if (playerBlackjack){
            this.playerTurn = false;
            if(dealerBlackjack) {
                this.status = GameStatus.DRAW;
            } else {
                this.status = GameStatus.WON;
            }
        }
    }

    private void ensurePlayerCanPlay() {
        if (status != GameStatus.IN_PROGRESS || !playerTurn) {
            throw new InvalidPlayException(
                    "No puede jugar " + status + ", playerTurn=" + playerTurn
            );
        }
    }
    public void playerHit() {
        ensurePlayerCanPlay();
        if(!playerHand.canHit()){
            throw new InvalidActionException("No puede pedir carta" + playerHand);
        }
        playerHand.addCard(deck.drawCard());
        if(playerHand.isBusted()) {
            status = GameStatus.PLAYER_BUSTED;
            playerTurn = false;
        } else if (playerHand.hasTwentyOne()) {
            playerTurn = false;
            dealerPlay();
        }
    }

    public void playerStand() {
        ensurePlayerCanPlay();
        playerHand.stand();
        playerTurn = false;
        dealerPlay();
    }

    private void dealerPlay() {
        if(status != GameStatus.IN_PROGRESS) return;

        while (dealerHand.calculateValue() < 17) {
            dealerHand.addCard(deck.drawCard());
        }
        if(dealerHand.isBusted()) {
            status = GameStatus.WON;
            return;
        }
        int dealerValue = dealerHand.calculateValue();
        int playerValue = playerHand.calculateValue();

        if(dealerValue > playerValue) {
            status = GameStatus.LOST;
        }else if (dealerValue < playerValue) {
            status = GameStatus.WON;
        }else {
            status = GameStatus.DRAW;
        }
    }

    public boolean isBlackjack() {
        return playerHand.isBlackjack();
    }
}
