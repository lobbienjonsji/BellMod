package code.powers;

import code.bells.AbstractBell;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class LamentPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(LamentPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public LamentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player,
                    AbstractDungeon.player, this));
        }
    }
    
    @Override
    public int modifyBellPower(AbstractBell bell, int power) {
        return power + amount;
    }
    
    @Override
    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
