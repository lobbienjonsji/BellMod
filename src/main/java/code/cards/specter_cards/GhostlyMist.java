package code.cards.specter_cards;

import code.ModFile;
import code.bells.NimbusBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class GhostlyMist extends AbstractEasyCard {
    
    public final static String ID = makeID(GhostlyMist.class.getSimpleName());
    public static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.UNCOMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int BASE_BLOCK = 8;
    public static final int BLOCK_UPGRADE = 3;
    public static final int BASE_MAGIC = 2;
    
    public GhostlyMist() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = BASE_BLOCK;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        tags.add(enums.GHOSTLY);
    }
    
    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        blck();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof NimbusBell) {
                    addToTop(new DrawCardAction(magicNumber));
                }
                isDone = true;
            }
        });
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof NimbusBell ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
