package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.ReverbMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Float extends AbstractEasyCard {
    
    public final static String ID = makeID(Float.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int BASE_BLOCK = 7;
    public static final int BLOCK_UPGRADE = 3;
    
    public Float() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BASE_BLOCK;
        CardModifierManager.addModifier(this, new ReverbMod());
    }
    
    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
