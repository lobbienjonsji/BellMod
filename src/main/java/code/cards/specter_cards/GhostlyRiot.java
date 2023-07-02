package code.cards.specter_cards;

import code.ModFile;
import code.bells.SteelBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class GhostlyRiot extends AbstractEasyCard {
    public final static String ID = makeID(GhostlyRiot.class.getSimpleName());
    public static final int COST = 2;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 5;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public GhostlyRiot() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        tags.add(enums.GHOSTLY);
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0; i < magicNumber; i++) {
                    dmgTop(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                }
                if (ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof SteelBell) {
                   ModFile.Bells.getRinging().increaseBaseEffect(2);
                }
            }
        });
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof SteelBell ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
