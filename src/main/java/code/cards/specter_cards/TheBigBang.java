package code.cards.specter_cards;

import code.ModFile;
import code.bells.AbstractBell;
import code.bells.AllBellTargetingHandler;
import code.bells.BigBang;
import code.bells.SteelBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class TheBigBang extends AbstractEasyCard {
    
    public final static String ID = makeID(TheBigBang.class.getSimpleName());
    public static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = AllBellTargetingHandler.BELL_INCLUDING_ON_COOLDOWN;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public TheBigBang() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell targetBell = AllBellTargetingHandler.getTarget(this);
        if (targetBell == null) {
            targetBell = ModFile.Bells.getRandomBell(false);
        }
        AbstractBell finalTargetBell = targetBell;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ModFile.Bells.replaceBell(finalTargetBell, new BigBang(magicNumber,finalTargetBell));
                isDone = true;
            }
        });
    }
}
