package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GateToHadesAction extends AbstractGameAction {
    private int hpLoss;
    private DamageInfo info;
    private AbstractCard card;
    private static final float DURATION = 0.1F;
    
    public GateToHadesAction(AbstractCreature target, DamageInfo info, int hpLoss, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.hpLoss = hpLoss;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }
    
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            for (AbstractCard c: AbstractDungeon.player.hand.group) {
                addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, hpLoss));
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        
        this.tickDuration();
    }
}
