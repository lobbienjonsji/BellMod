package code.powers;

import code.ModFile;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.Iterator;

import static code.ModFile.makeID;

public class TormentPower extends AbstractEasyPower{
    public static final String POWER_ID = makeID(TormentPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public TormentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amount);
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ModFile.Bells.updateDescription();
                isDone = true;
            }
        });
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    
    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
            
            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDead && !m.isDying) {
                    this.addToBot(new ApplyPowerAction(m, this.owner, new HauntedPower(m, this.amount), this.amount));
                }
            }
        }
        
    }
}
