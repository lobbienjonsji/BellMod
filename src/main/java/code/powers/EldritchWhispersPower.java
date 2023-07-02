package code.powers;

import code.actions.FastLoseHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.ModFile.makeID;

public class EldritchWhispersPower  extends AbstractEasyPower{
    public static final String POWER_ID = makeID(EldritchWhispersPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public EldritchWhispersPower(AbstractCreature owner) {
        super(POWER_ID, NAME, AbstractPower.PowerType.DEBUFF, false, owner, -1);
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 0 + DESCRIPTIONS[1];
    }
}
