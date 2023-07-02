package code.cards.specter_cards;

import code.ModFile;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.red.GhostlyArmor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class MidnightToll extends AbstractEasyCard {
    public final static String ID = makeID(MidnightToll.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    public static final int BASE_BLOCK = 3;
    public static final int BASE_MAGIC = 1;
    public static final int MAGIC_UPGRADE = 1;
    
    public MidnightToll() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BASE_BLOCK;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(magicNumber);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell target = BellTargetingHandler.getTarget(this);
        if (target == null) {
            target = ModFile.Bells.getActiveRandomBell();
        }
        addToBot(new TollBellAction(target));
        addToBot(new FetchAction(AbstractDungeon.player.drawPile, (c) -> c.tags.contains(enums.GHOSTLY) || c instanceof GhostlyArmor, magicNumber));
    }
}
