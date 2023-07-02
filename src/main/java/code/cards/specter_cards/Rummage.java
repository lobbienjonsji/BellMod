package code.cards.specter_cards;

import code.actions.RummageAction;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Rummage extends AbstractEasyCard {
    public final static String ID = makeID(Rummage.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final int BASE_BLOCK = 6;
    public static final int BLOCK_UPGRADE = 3;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public Rummage() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BASE_BLOCK;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
        addToBot(new RummageAction(magicNumber));
    }
}
