package code.cards.cardvars;

import basemod.abstracts.AbstractCardModifier;
import code.ModFile;
import code.powers.EldritchWhispersPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;

public class ReverbMod extends AbstractCardModifier {
    public static final String ID = makeID(ReverbMod.class.getSimpleName());
    private boolean isActive = true;
    
    public boolean isActive() {
        return isActive;
    }
    
    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
    
    @Override
    public AbstractCardModifier makeCopy() {
        return new ReverbMod();
    }
    
    public void fetch(AbstractCard card)
    {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                if(AbstractDungeon.player.discardPile.contains(card) &&
                        !AbstractDungeon.actionManager.turnHasEnded &&
                        !AbstractDungeon.player.isEndingTurn &&
                        !AbstractDungeon.player.endTurnQueued &&
                        !isActive && ModFile.ReverbActive) {
                    AbstractDungeon.player.discardPile.moveToHand(card);
                    if(AbstractDungeon.player.hasPower(EldritchWhispersPower.POWER_ID) && card.cost > 0)
                    {
                        card.freeToPlayOnce = true;
                    }
                    isActive = false;
                }
                isDone = true;
            }
        });
    }
    
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
    
    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isActive = true;
                isDone = true;
            }
        });
        super.atEndOfTurn(card, group);
    }
}
