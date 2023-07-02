package code.cards.specter_cards;

import code.ModFile;
import code.actions.CardTargetBellAction;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Nocturne extends AbstractEasyCard {
    public final static String ID = makeID(Nocturne.class.getSimpleName());
    public static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    public static final int BASE_BLOCK = 10;
    public static final int BLOCK_UPGRADE = 4;
    
    public Nocturne() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BASE_BLOCK;
    }
    
    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell target = BellTargetingHandler.getTarget(this);
        if (target == null) {
            target = ModFile.Bells.getActiveRandomBell();
        }
        addToBot(new TollBellAction(target));
        if(!isInAutoplay) {
            addToBot(new CardTargetBellAction(this));
        }
        else
        {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractBell target = BellTargetingHandler.getTarget(Nocturne.this);
                    if (target == null) {
                        target = ModFile.Bells.getActiveRandomBell();
                    }
                    addToBot(new TollBellAction(target));
                }
            });
        }
        blck();
    }
    
    @Override
    public void onSelectTarget(AbstractBell bell) {
        addToTop(new TollBellAction(bell));
    }
}
