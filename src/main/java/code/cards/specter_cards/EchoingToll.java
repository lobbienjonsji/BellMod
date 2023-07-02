package code.cards.specter_cards;

import code.ModFile;
import code.actions.EchoingTollAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class EchoingToll extends AbstractEasyCard {
    public final static String ID = makeID(EchoingToll.class.getSimpleName());
    public static final int COST = -1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    
    public EchoingToll() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }
    
    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell BellTarget = BellTargetingHandler.getTarget(this);
        if (BellTarget == null) {
            BellTarget = ModFile.Bells.getActiveRandomBell();
        }
        int effect = upgraded ? energyOnUse + 2 : energyOnUse + 1;
        addToBot(new EchoingTollAction(BellTarget, effect, freeToPlayOnce));
    }
}
