package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.bells.BellHandler.BellsTolledThisCombat;

public class BellsOfDoom extends AbstractEasyCard {
    
    public final static String ID = makeID(BellsOfDoom.class.getSimpleName());
    public static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 15;
    public static final int BASE_MAGIC = 5;
    public static final int MAGIC_UPGRADE = 2;
    
    public BellsOfDoom() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = magicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
                isDone = true;
            }
        });
    }
    
    
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += BellsTolledThisCombat * magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += BellsTolledThisCombat * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
