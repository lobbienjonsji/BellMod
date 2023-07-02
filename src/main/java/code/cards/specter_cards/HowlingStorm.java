package code.cards.specter_cards;

import code.bells.NimbusBell;
import code.cards.AbstractEasyCard;
import code.powers.HarmonyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.Bells;
import static code.ModFile.makeID;

public class HowlingStorm extends AbstractEasyCard {
    
    public final static String ID = makeID(HowlingStorm.class.getSimpleName());
    public static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    public static final CardRarity RARITY = CardRarity.COMMON;
    public static final CardTarget TARGET = CardTarget.SELF;
    public static final int BASE_MAGIC = 2;
    public static final int MAGIC_UPGRADE = 1;
    
    public HowlingStorm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BASE_MAGIC;
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new HarmonyPower(abstractPlayer, magicNumber)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(Bells.getBell(NimbusBell.BELL_ID) != null) {
                    Bells.getBell(NimbusBell.BELL_ID).addCharges(magicNumber);
                }
                isDone = true;
            }
        });
    }
}
