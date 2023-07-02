package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.ModFile.makeID;

public class Jumpscare extends AbstractEasyCard {
    
    public final static String ID = makeID(Jumpscare.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public Jumpscare() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToTop(new ApplyPowerAction(abstractMonster, abstractPlayer, new WeakPower(abstractMonster, magicNumber, false)));
        addToTop(new ApplyPowerAction(abstractMonster, abstractPlayer, new HauntedPower(abstractMonster, magicNumber)));
        this.addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new StrengthPower(abstractMonster, -this.magicNumber), -this.magicNumber));
        if (abstractMonster != null && !abstractMonster.hasPower("Artifact")) {
            this.addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new GainStrengthPower(abstractMonster, this.magicNumber), this.magicNumber));
        }
    }
}
