package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.DiscardEachTurnPower;
import code.powers.ParanormalPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import static code.ModFile.makeID;

public class Paranormal  extends AbstractEasyCard {
    
    public final static String ID = makeID(Paranormal.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int GAIN = 1;
    public static final int DISCARD = 1;
    
    public Paranormal() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }
    
    @Override
    public void upp() {
        isInnate = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DiscardEachTurnPower(abstractPlayer, GAIN)));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ParanormalPower(abstractPlayer, DISCARD)));
    }
}
