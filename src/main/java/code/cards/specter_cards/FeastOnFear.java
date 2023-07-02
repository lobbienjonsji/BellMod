package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import code.powers.HauntedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class FeastOnFear extends AbstractEasyCard {
    
    public final static String ID = makeID(FeastOnFear.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.ENEMY;
    public static final int BASE_MAGIC = 3;
    public static final int MAGIC_UPGRADE = 1;
    
    public FeastOnFear() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (abstractMonster.hasPower(HauntedPower.POWER_ID)) {
                    addToTop(new GainEnergyAction(Math.min(magicNumber,
                            abstractMonster.getPower(HauntedPower.POWER_ID).amount)));
                    if(!upgraded) {
                        addToTop(new ReducePowerAction(abstractMonster, abstractPlayer, HauntedPower.POWER_ID, Math.min(magicNumber,
                                abstractMonster.getPower(HauntedPower.POWER_ID).amount)));
                    }
                    else
                    {
                        ((HauntedPower)abstractMonster.getPower(HauntedPower.POWER_ID)).onTrigger(Math.min(magicNumber,
                                abstractMonster.getPower(HauntedPower.POWER_ID).amount));
                    }
                }
                isDone = true;
            }
        });
    }
}
