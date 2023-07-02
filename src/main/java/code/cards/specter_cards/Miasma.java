package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static code.ModFile.makeID;

public class Miasma extends AbstractEasyCard {
    public final static String ID = makeID(Miasma.class.getSimpleName());
    public static final int COST = 1;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 6;
    public static final int DAMAGE_UPGRADE = 2;
    
    public Miasma() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractPower power : abstractMonster.powers) {
                    if (power.type == AbstractPower.PowerType.DEBUFF) {
                        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                    }
                }
                isDone = true;
            }
        });
    }
}
