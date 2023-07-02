package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Horror extends AbstractEasyCard {
    
    public final static String ID = makeID(Horror.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 2;
    public static final int DRAW = 1;
    
    public Horror() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new ApplyPowerAction(abstractMonster, abstractPlayer, new HauntedPower(abstractMonster, magicNumber)));
        addToTop(new DrawCardAction(DRAW));
    }
}
