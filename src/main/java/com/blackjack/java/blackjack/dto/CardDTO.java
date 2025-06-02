package com.blackjack.java.blackjack.dto;

import lombok.*;
import com.blackjack.java.blackjack.utils.CardSuit;
import com.blackjack.java.blackjack.utils.CardRank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private CardSuit suit;
    private CardRank rank;
}

