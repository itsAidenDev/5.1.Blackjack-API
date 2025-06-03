package com.blackjack.java.blackjack.model;

import com.blackjack.java.blackjack.actions.DrawCard;
import com.blackjack.java.blackjack.model.cards.Deck;
import com.blackjack.java.blackjack.exceptions.InvalidActionException;
import com.blackjack.java.blackjack.exceptions.InvalidPlayException;
import com.blackjack.java.blackjack.repository.GameRepository;
import com.blackjack.java.blackjack.utils.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.UUID;

import lombok.*;
import reactor.core.publisher.Mono;

@Data
@Getter
@Setter
@Document(collection = "games")
public class Game {

    @Autowired
    private GameRepository gameRepository;

    @Id
    private Long gameId;
    private String playerId;
    private Deck deck;
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
    }

    public Game(Long playerId, int initialBet) {
        if(initialBet <= 0) {
            throw new InvalidActionException("The bet cannot be less than 0");
        }
        this.gameId = Long.valueOf(UUID.randomUUID().toString());
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.playerId = String.valueOf(playerId);
        this.bet = initialBet;
        this.status = GameStatus.IN_PROGRESS;
        this.playerTurn = true;
    }

    public Long getGameId() {
        return gameId;
    }

    public void dealInitialCards() {
        DrawCard drawCard = new DrawCard();
        playerHand.addCard(drawCard.drawCard());
        dealerHand.addCard(drawCard.drawCard());
        playerHand.addCard(drawCard.drawCard());
        dealerHand.addCard(drawCard.drawCard());
    }

    public Mono<Game> settleBet(Game game) {
        game.setStatus(GameStatus.SETTLED);
        return gameRepository.save(game);
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
                    "The game is not in progress or it is not the player's turn. Status= " + status + ". PlayerTurn=" + playerTurn
            );
        }
    }
    public void playerHit() {
        ensurePlayerCanPlay();
        if(!playerHand.canHit()){
            throw new InvalidActionException("The player cannot hit. PlayerHand=" + playerHand);
        }
        playerHand.addCard(deck.drawCard());
        if(playerHand.isBusted()) {
            status = GameStatus.BUSTED;
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
