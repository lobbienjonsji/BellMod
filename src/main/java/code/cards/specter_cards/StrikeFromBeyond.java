package code.cards.specter_cards;

import code.ModFile;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class StrikeFromBeyond extends AbstractEasyCard {
    
    public final static String ID = makeID(StrikeFromBeyond.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 4;
    public static final int DAMAGE_UPGRADE = 2;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public StrikeFromBeyond() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_MAGIC;
        this.tags.add(CardTags.STRIKE);
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
    
    
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += ModFile.cardsEnteredDiscardPileThisTurn * magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += ModFile.cardsEnteredDiscardPileThisTurn * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
