package code.cards.specter_cards;

import code.actions.GateToHadesAction;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static code.ModFile.makeID;

public class GateToHades extends AbstractEasyCard {
    
    public final static String ID = makeID(GateToHades.class.getSimpleName());
    public static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 50;
    public static final int DAMAGE_UPGRADE = 15;
    public static final int BASE_MAGIC = 3;
    
    public GateToHades() {
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
        if (abstractMonster != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(abstractMonster.hb.cX, abstractMonster.hb.cY)));
        }
        this.addToBot(new WaitAction(0.8F));
        addToBot(new GateToHadesAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), this.magicNumber, this));
    }
    
    public void applyPowers() {
        super.applyPowers();
        int count = 0;
    
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != this) {
                ++count;
            }
        }
        
        this.rawDescription = cardStrings.DESCRIPTION;
        if (count >= 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        
        this.initializeDescription();
    }
    
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
