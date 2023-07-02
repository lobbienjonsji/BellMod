package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.EldritchWhispersPower;
import code.powers.SymphonyOfChimesPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class EldritchWhispers extends AbstractEasyCard {
    
    public final static String ID = makeID(EldritchWhispers.class.getSimpleName());
    public static final int COST = 2;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    public static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    public static final int BASE_MAGIC = 1;
    public static final int COST_UPGRADE = 1;
    
    public EldritchWhispers() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeBaseCost(COST_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EldritchWhispersPower(abstractPlayer)));
    }
}
