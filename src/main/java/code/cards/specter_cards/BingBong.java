package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.ReverbMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class BingBong extends AbstractEasyCard {
    
    public final static String ID = makeID(BingBong.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    
    public BingBong() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new ReverbMod());
    }
    
    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell target = BellTargetingHandler.getTarget(this);
        if (target == null) {
            target = ModFile.Bells.getActiveRandomBell();
        }
        addToBot(new TollBellAction(target));
        addToBot(new DiscardAction(abstractPlayer, abstractPlayer, 1, !upgraded));
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
