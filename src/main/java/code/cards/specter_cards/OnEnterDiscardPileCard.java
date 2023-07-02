package code.cards.specter_cards;

import code.cards.AbstractEasyCard;

public abstract class OnEnterDiscardPileCard extends AbstractEasyCard {
    public OnEnterDiscardPileCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }
    
    public void triggerOnEnterDiscard() {
    
    }
}
