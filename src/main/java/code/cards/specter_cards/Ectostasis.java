package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.cards.cardvars.ReverbMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Ectostasis extends OnEnterDiscardPileCard {
    public final static String ID = makeID(Ectostasis.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.NONE;
    public static final int BASE_MAGIC = 4;
    public static final int MAGIC_UPGRADE = 2;
    
    public Ectostasis() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BASE_MAGIC;
        CardModifierManager.addModifier(this, new ReverbMod());
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }
    
    @Override
    public void triggerOnEnterDiscard() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, this.magicNumber));
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
