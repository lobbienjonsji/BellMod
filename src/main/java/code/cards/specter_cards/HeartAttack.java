package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.bells.BellHandler.BellsTolledThisCombat;

public class HeartAttack extends AbstractEasyCard {
    
    public final static String ID = makeID(HeartAttack.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 8;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 2;
    
    public HeartAttack() {
        super(ID, COST, TYPE, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = BASE_MAGIC;
        this.baseDamage = BASE_DAMAGE;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                misc = 0;
                isDone = true;
            }
        });
    }
    
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += misc;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += misc;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    
    @Override
    public void onApplyDebuff() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                misc += magicNumber;
                applyPowers();
                isDone = true;
            }
        });
    }
}