package code.powers;

import code.actions.FastLoseHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static code.ModFile.makeID;

public class NecrosisPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(NecrosisPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public NecrosisPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }
    
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.addToTop(new FastLoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
