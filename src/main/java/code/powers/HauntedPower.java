package code.powers;

import code.actions.FastLoseHPAction;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class HauntedPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(HauntedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int BASE_HP_LOSS = 4;
    
    public HauntedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.DEBUFF, false, owner, amount);
    }
    
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.HP_LOSS) {
            int triggers = 1;
            if(AbstractDungeon.player.hasPower(WildHuntPower.POWER_ID))
            {
                triggers += AbstractDungeon.player.getPower(WildHuntPower.POWER_ID).amount;
            }
            for(int i = 0; i < Math.min(amount, triggers); i++) {
                this.flash();
                this.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
                int finalAmount = owner.hasPower(GrimDirgePower.POWER_ID) ? BASE_HP_LOSS + owner.getPower(GrimDirgePower.POWER_ID).amount : BASE_HP_LOSS;
                this.addToTop(new FastLoseHPAction(this.owner, this.owner, finalAmount, AbstractGameAction.AttackEffect.FIRE));
            }
        }
        return damageAmount;
    }
    
    public void onTrigger(int TriggerAmount)
    {
        for(int i = 0; i < TriggerAmount; i++) {
            this.flash();
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
            int finalAmount = owner.hasPower(GrimDirgePower.POWER_ID) ? BASE_HP_LOSS + owner.getPower(GrimDirgePower.POWER_ID).amount : BASE_HP_LOSS;
            this.addToTop(new FastLoseHPAction(this.owner, this.owner, finalAmount, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + BASE_HP_LOSS + DESCRIPTIONS[1];
    }
}
