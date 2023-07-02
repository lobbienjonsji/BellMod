package code.NotNeeded.Unused;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class HammerDown extends AbstractEasyCard {
    
    public final static String ID = makeID(HammerDown.class.getSimpleName());
    public static final int COST = 0;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 2;
    public static final int DAMAGE_UPGRADE = 1;
    
    public HammerDown() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }
}
