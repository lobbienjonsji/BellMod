package code.powers;

import code.bells.AbstractBell;
import code.bells.FrozenBell;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.ModFile.makeID;

public class ChillTouchPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(ChillTouchPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public ChillTouchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    
    @Override
    public int modifyBellPower(AbstractBell bell, int power) {
        if(bell instanceof FrozenBell)
        {
            return power + this.amount;
        }
        return power;
    }
    
    @Override
    public void onToll(AbstractBell bell) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
