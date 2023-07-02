package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class SoulHarvest extends AbstractEasyCard {
    
    public final static String ID = makeID(SoulHarvest.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 8;
    public static final int DAMAGE_UPGRADE = 3;
    public static final int BASE_MAGIC = 2;
    
    public SoulHarvest() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                dmgTop(abstractMonster, AttackEffect.SLASH_DIAGONAL);
                if (this.target != null && this.target.hasPower(HauntedPower.POWER_ID)) {
                    this.addToTop(new DrawCardAction(AbstractDungeon.player, magicNumber));
                }
                isDone = true;
            }
        });
    }
}
