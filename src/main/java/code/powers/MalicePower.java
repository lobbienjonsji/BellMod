package code.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class MalicePower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(MalicePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public MalicePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }
    
    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(damageAmount > 0) {
            return damageAmount + amount;
        }
        return damageAmount;
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
