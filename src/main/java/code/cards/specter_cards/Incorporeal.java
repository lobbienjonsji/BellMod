package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.DiscardEachTurnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import static code.ModFile.makeID;

public class Incorporeal extends AbstractEasyCard {
    
    public final static String ID = makeID(Incorporeal.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int BASE_MAGIC = 5;
    public static final int MAGIC_UPGRADE = 2;
    
    public Incorporeal() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DiscardEachTurnPower(abstractPlayer, 1)));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MetallicizePower(abstractPlayer, magicNumber)));
    }
}
