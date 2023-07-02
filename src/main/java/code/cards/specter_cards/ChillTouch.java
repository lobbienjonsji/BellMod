package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.ChillTouchPower;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class ChillTouch  extends AbstractEasyCard {
    
    public final static String ID = makeID(ChillTouch.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_MAGIC = 6;
    public static final int BASE_DAMAGE = 5;
    public static final int DAMAGE_UPGRADE = 3;
    public static final int MAGIC_UPGRADE = 2;
    public ChillTouch() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToTop(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ChillTouchPower(abstractPlayer, magicNumber)));
    }
}
