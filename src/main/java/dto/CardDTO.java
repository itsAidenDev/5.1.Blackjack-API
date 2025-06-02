package dto;

import lombok.*;
import utils.CardSuit;
import utils.CardValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private CardSuit suit;
    private CardValue value;
}

