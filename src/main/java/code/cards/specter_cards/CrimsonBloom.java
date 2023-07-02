package code.cards.specter_cards;

import code.ModFile;
import code.bells.CrimsonBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class CrimsonBloom extends AbstractEasyCard {
    
    public final static String ID = makeID(CrimsonBloom.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.NONE;
    public static final int MAGIC_UPGRADE = 2;
    public static final int BASE_MAGIC = 3;
    
    public CrimsonBloom() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ModFile.Bells.getBell(CrimsonBell.BELL_ID).increaseBaseEffect(magicNumber);
                isDone = true;
            }
        });
    }
}
