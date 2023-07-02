package code.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class ParanormalPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(ParanormalPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public ParanormalPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
    }
    
    @Override
    public void atStartOfTurn() {
        this.addToBot(new GainEnergyAction(this.amount));
        this.flash();
    }
    
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        
        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }
        
        sb.append(LocalizedStrings.PERIOD);
        this.description = sb.toString();
    }
}
