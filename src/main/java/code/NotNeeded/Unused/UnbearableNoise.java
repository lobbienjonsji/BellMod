package code.NotNeeded.Unused;

import code.actions.UnbearableNoiseAction;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class UnbearableNoise  extends AbstractEasyCard {
    public final static String ID = makeID(UnbearableNoise.class.getSimpleName());
    public static final int COST = -1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int MAGIC_NUMBER = 4;
    public static final int MAGIC_UPGRADE = 1;
    
    public UnbearableNoise() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new UnbearableNoiseAction(energyOnUse, freeToPlayOnce, magicNumber));
    }
}
