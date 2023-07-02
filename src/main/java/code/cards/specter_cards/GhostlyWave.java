package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.bells.BellHandler.TolledBellThisTurn;

public class GhostlyWave extends AbstractEasyCard {
    
    public final static String ID = makeID(GhostlyWave.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 7;
    public static final int DAMAGE_UPGRADE = 2;
    public static final int BASE_BLOCK = 7;
    public static final int BLOCK_UPGRADE = 2;
    
    public GhostlyWave() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        baseBlock = BASE_BLOCK;
        tags.add(enums.GHOSTLY);
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (TolledBellThisTurn) {
                    blck();
                }
                isDone = true;
            }
        });
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = TolledBellThisTurn ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
