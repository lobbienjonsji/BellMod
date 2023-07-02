package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class HammerBash extends AbstractEasyCard {
    
    public final static String ID = makeID(HammerBash.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 14;
    public static final int DAMAGE_UPGRADE = 4;
    
    public HammerBash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        exhaust = true;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
}
