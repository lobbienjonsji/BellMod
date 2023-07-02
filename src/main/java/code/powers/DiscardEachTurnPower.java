package code.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class DiscardEachTurnPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(DiscardEachTurnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public DiscardEachTurnPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }
    
    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, amount, false));
    }
    
    @Override
    public void updateDescription() {
        this.description = amount == 1 ?  DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] :  DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }
}
