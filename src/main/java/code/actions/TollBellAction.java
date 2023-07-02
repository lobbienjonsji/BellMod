package code.actions;

import code.bells.AbstractBell;
import code.bells.CrimsonBell;
import code.powers.PainAndSorrowPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static code.ModFile.makeID;

public class TollBellAction extends AbstractGameAction {
    private AbstractBell bell;
    private boolean consumeResonance;
    
    public TollBellAction(AbstractBell bell) {
        this.bell = bell;
        amount = 1;
        consumeResonance = true;
    }
    
    public TollBellAction(AbstractBell bell, int amount) {
        this.bell = bell;
        this.amount = amount;
        consumeResonance = true;
    }
    
    public TollBellAction(AbstractBell bell, boolean consumeResonance) {
        this.bell = bell;
        this.consumeResonance = consumeResonance;
    }
    
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    
    @Override
    public void update() {
        if(!bell.isOnCooldown()) {
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            bell.onToll();
            if(AbstractDungeon.player.hasPower(PainAndSorrowPower.POWER_ID) && bell instanceof CrimsonBell)
            {
               amount += AbstractDungeon.player.getPower(PainAndSorrowPower.POWER_ID).amount;
            }
            for(int i = 0; i < amount; i ++)
            {
                bell.toll();
            }
        }
        else
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(
                    AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY,
                    3.0F, TEXT[0], true));
        }
        isDone = true;
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("TollBellAction"));
        TEXT = uiStrings.TEXT;
    }
}
