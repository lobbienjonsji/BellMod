package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.GrimDirgePower;
import code.powers.HarmonyPower;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static code.ModFile.makeID;

public class GrimDirge extends AbstractEasyCard {
    
    public final static String ID = makeID(GrimDirge.class.getSimpleName());
    public static final int COST = 1;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    public static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public GrimDirge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, abstractPlayer, new GrimDirgePower(mo, magicNumber)));
            this.addToBot(new ApplyPowerAction(mo, abstractPlayer, new HauntedPower(mo, magicNumber)));
        }
    }
}
