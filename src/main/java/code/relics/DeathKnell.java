package code.relics;

import code.TheSpecter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;

public class DeathKnell extends AbstractEasyRelic {
    public static final String ID = makeID("DeathKnell");

    public DeathKnell() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheSpecter.Enums.SPECTER_LIGHT_BLUE);
    }
    
    public void atBattleStart() {
        this.counter = 0;
    }
    
    @Override
    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
        }
        if (this.counter == 2) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    for (int i = 0; i < 2; i++) {
                        if (AbstractDungeon.player.discardPile.isEmpty()) {
                            isDone = true;
                            return;
                        }
                        AbstractDungeon.player.discardPile.moveToHand(AbstractDungeon.player.discardPile.getBottomCard());
                    }
                    isDone = true;
                }
            });
            this.counter = -1;
            this.grayscale = true;
        }
    }
    
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }
}
