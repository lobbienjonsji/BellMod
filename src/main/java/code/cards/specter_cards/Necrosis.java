package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import code.powers.NecrosisPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Necrosis extends AbstractEasyCard {
    
    public final static String ID = makeID(Necrosis.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_MAGIC = 4;
    public static final int MAGIC_UPGRADE = 2;
    
    public Necrosis() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
        this.exhaust = true;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new NecrosisPower(abstractMonster, magicNumber)));
    }
}
