package code.cards.specter_cards;

import code.ModFile;
import code.bells.CrimsonBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class BloodMoon extends AbstractEasyCard {
    
    public final static String ID = makeID(BloodMoon.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 10;
    public static final int MAGIC_UPGRADE = 2;
    public static final int BASE_MAGIC = 2;
    
    public BloodMoon() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ModFile.Bells.getBell(CrimsonBell.BELL_ID).increaseBaseEffect(magicNumber);
                isDone = true;
            }
        });
    }
}
