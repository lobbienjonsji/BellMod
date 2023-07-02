package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.cards.cardvars.ReverbMod;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class ShroudInDarkness extends OnEnterDiscardPileCard {
    public final static String ID = makeID(ShroudInDarkness.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.NONE;
    public static final int BASE_MAGIC = 1;
    public static final int MAGIC_UPGRADE = 1;
    public static final int BASE_BLOCK = 6;
    public static final int BLOCK_UPGRADE = 2;
    
    public ShroudInDarkness() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BASE_MAGIC;
        CardModifierManager.addModifier(this, new ReverbMod());
        this.baseBlock = BASE_BLOCK;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
    }
    
    @Override
    public void triggerOnEnterDiscard() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new HauntedPower(mo, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
