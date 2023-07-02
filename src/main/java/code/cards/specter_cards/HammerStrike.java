package code.cards.specter_cards;

import code.ModFile;
import code.actions.CardTargetBellAction;
import code.actions.TollBellAction;
import code.bells.AbstractBell;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class HammerStrike extends AbstractEasyCard {
    
    public final static String ID = makeID(HammerStrike.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 8;
    public static final int DAMAGE_UPGRADE = 3;
    
    public HammerStrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        tags.add(CardTags.STRIKE);
    }
    
    @Override
    public void upp() {
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        dmg(abstractMonster, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if(!isInAutoplay) {
            addToBot(new CardTargetBellAction(this));
        }
        else
        {
            AbstractBell target = BellTargetingHandler.getTarget(this);
            if (target == null) {
                target = ModFile.Bells.getActiveRandomBell();
            }
            addToBot(new TollBellAction(target));
        }
    }
    
    @Override
    public void onSelectTarget(AbstractBell bell) {
        addToTop(new TollBellAction(bell));
    }
}
