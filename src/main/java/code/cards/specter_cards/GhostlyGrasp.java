package code.cards.specter_cards;

import code.ModFile;
import code.bells.FrozenBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static code.ModFile.makeID;

public class GhostlyGrasp extends AbstractEasyCard {
    
    public final static String ID = makeID(GhostlyGrasp.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 8;
    public static final int DAMAGE_UPGRADE = 3;
    public static final int BASE_MAGIC = 2;
    
    public GhostlyGrasp() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        tags.add(enums.GHOSTLY);
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof FrozenBell) {
                    addToTop(new ApplyPowerAction(abstractMonster, abstractPlayer, new WeakPower(abstractMonster, magicNumber, false)));
                }
                isDone = true;
            }
        });
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof FrozenBell ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
