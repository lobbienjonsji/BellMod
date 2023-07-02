package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Raze extends AbstractEasyCard {
    
    public final static String ID = makeID(Raze.class.getSimpleName());
    public static final int COST = 2;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    public static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final int BASE_DAMAGE = 5;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public Raze() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        isMultiDamage = true;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
}
