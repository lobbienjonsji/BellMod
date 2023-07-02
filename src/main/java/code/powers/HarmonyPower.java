package code.powers;

import code.ModFile;
import code.bells.AbstractBell;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static code.ModFile.makeID;

public class HarmonyPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(HarmonyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public HarmonyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ModFile.Bells.updateDescription();
                isDone = true;
            }
        });
        if (AbstractDungeon.player.hasPower(MelodramaPower.POWER_ID))
        {
            this.amount += AbstractDungeon.player.getPower(MelodramaPower.POWER_ID).amount;
        }
    }
    
    @Override
    public int modifyBellPowerFinal(AbstractBell bell, int power) {
        return (int)(power + Math.ceil((float) power * 0.5f));
    }
    
    @Override
    public void onToll(AbstractBell bell) {
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, HarmonyPower.POWER_ID, 1));
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
