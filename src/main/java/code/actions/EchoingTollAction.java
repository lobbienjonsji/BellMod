package code.actions;

import code.bells.AbstractBell;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class EchoingTollAction extends AbstractGameAction {
    AbstractBell targetBell;
    private boolean freeToPlayOnce;
    
    public EchoingTollAction(AbstractBell targetBell, int amount, boolean freeToPlayOnce) {
        this.targetBell = targetBell;
        this.amount = amount;
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
            addToTop(new TollBellAction(targetBell, effect));
            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
