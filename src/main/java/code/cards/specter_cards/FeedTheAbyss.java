package code.cards.specter_cards;

import code.actions.FeedTheAbyssAction;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class FeedTheAbyss extends AbstractEasyCard {
    public final static String ID = makeID(FeedTheAbyss.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.NONE;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public FeedTheAbyss() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new FeedTheAbyssAction(magicNumber));
    }
}
