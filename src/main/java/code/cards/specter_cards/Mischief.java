package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.ModFile.makeID;

public class Mischief extends AbstractEasyCard {
    public final static String ID = makeID(Mischief.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public static final int BASE_MAGIC = 1;
    public static final int MAGIC_UPGRADE = 1;
    
    public Mischief() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (mo.hasPower(HauntedPower.POWER_ID)) {
                        this.addToBot(new ApplyPowerAction(mo, abstractPlayer, new VulnerablePower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                        this.addToBot(new ApplyPowerAction(mo, abstractPlayer, new WeakPower(mo, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
                isDone = true;
            }
        });
    }
}
