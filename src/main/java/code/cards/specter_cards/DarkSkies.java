package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static code.ModFile.makeID;

public class DarkSkies extends AbstractEasyCard {
    public final static String ID = makeID(DarkSkies.class.getSimpleName());
    public static final int BASE_DAMAGE = 2;
    public static final int DAMAGE_UPGRADE = 1;
    
    public DarkSkies() {
        super(ID, 0, AbstractCard.CardType.ATTACK, CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = 0;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    public void applyPowers() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c != this) {
                ++count;
            }
        }
        magicNumber = baseMagicNumber = count;
        super.applyPowers();
        if (this.baseMagicNumber == 1) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        else if(baseMagicNumber > 1)
        {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }
    
}