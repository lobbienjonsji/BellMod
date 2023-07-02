package code.cards.specter_cards;

import code.ModFile;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class HarmonicChime extends AbstractEasyCard {
    public final static String ID = makeID(HarmonicChime.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = BellTargetingHandler.BELL;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public HarmonicChime() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractBell target = BellTargetingHandler.getTarget(this);
        if (target == null) {
            target = ModFile.Bells.getActiveRandomBell();
        }
        addToBot(new TollBellAction(target));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new HarmonyPower(abstractMonster, magicNumber)));
    }
}
