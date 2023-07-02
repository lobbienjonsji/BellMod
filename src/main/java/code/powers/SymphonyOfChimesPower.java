package code.powers;

import code.actions.PowerTargetBellAction;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static code.ModFile.makeID;

public class SymphonyOfChimesPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(SymphonyOfChimesPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public SymphonyOfChimesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, AbstractPower.PowerType.BUFF, false, owner, amount);
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
    
    public void atStartOfTurnPostDraw() {
        addToBot(new PowerTargetBellAction(this));
    }
    
    @Override
    public void onSelectTarget(AbstractBell bell) {
        addToBot(new TollBellAction(bell));
    }
}
