package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.ReverbMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Spook extends AbstractEasyCard {
    
    public final static String ID = makeID(Spook.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 4;
    public static final int DAMAGE_UPGRADE = 1;
    
    public Spook() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        CardModifierManager.addModifier(this, new ReverbMod());
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
