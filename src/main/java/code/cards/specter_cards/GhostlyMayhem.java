package code.cards.specter_cards;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.bells.BellHandler.BellsTolledThisTurn;

public class GhostlyMayhem extends AbstractEasyCard {
    
    public final static String ID = makeID(GhostlyMayhem.class.getSimpleName());
    public static final int COST = 1;
    public static final int UPGRADED_COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.RARE;
    public static final CardTarget TARGET = CardTarget.NONE;
    
    public GhostlyMayhem() {
        super(ID, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        tags.add(enums.GHOSTLY);
    }
    
    @Override
    public void upp() {
        upgradeBaseCost(UPGRADED_COST);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0; i < BellsTolledThisTurn + 1; i++) {
                    this.addToBot(new AbstractGameAction() {
                        public void update() {
                            this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                            this.isDone = true;
                        }
                    });
                }
                isDone = true;
            }
        });
    }
    
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber == 1) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        else if(baseMagicNumber > 1)
        {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        
        this.initializeDescription();
    }
    
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = BellsTolledThisTurn;
        this.magicNumber = 0;
        if (this.baseMagicNumber == 1) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
        else if(baseMagicNumber > 1)
        {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
        
    }
    
}
