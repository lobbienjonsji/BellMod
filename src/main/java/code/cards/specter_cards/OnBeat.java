package code.cards.specter_cards;

import code.ModFile;
import code.actions.CardTargetBellAction;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class OnBeat extends AbstractEasyCard {
    public final static String ID = makeID(OnBeat.class.getSimpleName());
    public static final int COST = 1;
    public static final int UPGRADE_COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    public static final int MAGIC_NUMBER = 1;
    public static final int MAGIC_UPGRADE = 1;
    
    public OnBeat() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }
    
    @Override
    public void upp() {
        upgradeBaseCost(UPGRADE_COST);
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell bellTarget = BellTargetingHandler.getTarget(this);
        boolean noTarget = false;
        if (target == null) {
            bellTarget = ModFile.Bells.getActiveRandomBell();
            noTarget = true;
        }
        AbstractBell finalBellTarget = bellTarget;
        boolean finalNoTarget = noTarget;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                
                if(AbstractDungeon.player.hasPower(HarmonyPower.POWER_ID))
                {
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if(finalNoTarget)
                            {
                                AbstractBell bellTarget = ModFile.Bells.getActiveRandomBell();
                                addToTop(new TollBellAction(bellTarget));
                            }
                            else
                            {
                                addToTop(new CardTargetBellAction(OnBeat.this));
                            }
                            isDone = true;
                        }
                    });
                }
                addToTop(new TollBellAction(finalBellTarget));
                isDone = true;
            }
        });
    }
    
    @Override
    public void onSelectTarget(AbstractBell bell) {
        addToTop(new TollBellAction(bell));
    }
}
