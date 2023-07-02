package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class SpectralBlast extends AbstractEasyCard {
    
    public final static String ID = makeID(SpectralBlast.class.getSimpleName());
    public static final int COST = 2;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final int BASE_DAMAGE = 12;
    public static final int DAMAGE_UPGRADE = 4;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public SpectralBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        isMultiDamage = true;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, abstractPlayer, new HauntedPower(mo, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
