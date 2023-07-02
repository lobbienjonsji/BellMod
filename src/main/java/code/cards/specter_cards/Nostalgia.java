package code.cards.specter_cards;

import basemod.helpers.CardModifierManager;
import code.bells.BellTargetingHandler;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.ReverbMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class Nostalgia extends AbstractEasyCard {
    public final static String ID = makeID(Nostalgia.class.getSimpleName());
    public static final int COST = 0;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    public static final AbstractCard.CardTarget TARGET = BellTargetingHandler.BELL;
    public static final int BASE_MAGIC = 1;
    
    public Nostalgia() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        CardModifierManager.addModifier(this, new ReverbMod());
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new FetchAction(AbstractDungeon.player.drawPile, (c) ->
                !AbstractDungeon.actionManager.cardsPlayedThisTurn.contains(c), magicNumber));
    }
}
