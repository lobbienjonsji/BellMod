package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.cards.cardvars.ReverbMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class SpitefulBlow extends OnEnterDiscardPileCard {
    public final static String ID = makeID(SpitefulBlow.class.getSimpleName());
    public static final int COST = 0;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 18;
    public static final int DAMAGE_UPGRADE = 6;
    private boolean canPlay = false;
    
    public SpitefulBlow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        CardModifierManager.addModifier(this, new ReverbMod());
    }
    
    public void triggerOnEnterDiscard() {
        canPlay = true;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
    
    @Override
    public void triggerAtStartOfTurn() {
        canPlay = false;
    }
    
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!canPlay) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        return canPlay;
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ((ReverbMod) (CardModifierManager.getModifiers(this, ReverbMod.ID).get(0))).isActive() ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
