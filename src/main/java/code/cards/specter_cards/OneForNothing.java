package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class OneForNothing extends AbstractEasyCard {
    public final static String ID = makeID(OneForNothing.class.getSimpleName());
    public static final int COST = 0;
    public static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    public static final AbstractCard.CardTarget TARGET = CardTarget.NONE;
    
    public OneForNothing() {
        super(ID, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }
    
    @Override
    public void upp() {
        isInnate = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new DiscardAction(abstractPlayer, abstractPlayer, abstractPlayer.hand.size(), true));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : abstractPlayer.drawPile.group) {
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            abstractPlayer.drawPile.moveToDiscardPile(c);
                            c.triggerOnManualDiscard();
                            GameActionManager.incrementDiscard(false);
                            isDone = true;
                        }
                    });
                }
                isDone = true;
            }
        });
    }
}