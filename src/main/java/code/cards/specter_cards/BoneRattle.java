package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class BoneRattle extends AbstractEasyCard {
    
    public final static String ID = makeID(BoneRattle.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 4;
    public static final int DAMAGE_UPGRADE = 1;
    public static final int BASE_MAGIC = 1;
    public static final int MAGIC_UPGRADE = 1;
    
    public BoneRattle() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new HarmonyPower(abstractMonster, magicNumber)));
    }
}

