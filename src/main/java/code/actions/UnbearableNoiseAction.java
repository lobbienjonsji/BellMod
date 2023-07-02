package code.actions;

import code.bells.AbstractBell;
import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class UnbearableNoiseAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magic;
    
    public UnbearableNoiseAction(int amount, boolean freeToPlayOnce, int magic) {
        this.amount = amount;
        this.magic = magic;
        this.freeToPlayOnce = freeToPlayOnce;
    }
    
    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.amount != -1) {
            effect = this.amount;
        }
        
        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            effect += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }
        
        if (effect > 0) {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HarmonyPower(AbstractDungeon.player, magic)));
            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
