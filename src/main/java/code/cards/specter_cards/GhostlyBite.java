package code.cards.specter_cards;

import code.ModFile;
import code.bells.CrimsonBell;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

import static code.ModFile.makeID;

public class GhostlyBite extends AbstractEasyCard {
    public final static String ID = makeID(GhostlyBite.class.getSimpleName());
    public static final int COST = 1;
    public static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    public static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final int BASE_DAMAGE = 9;
    public static final int DAMAGE_UPGRADE = 1;
    public static final int BASE_MAGIC = 5;
    public static final int MAGIC_UPGRADE = 1;
    
    public GhostlyBite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MAGIC;
        this.exhaust = true;
        tags.add(enums.GHOSTLY);
        tags.add(CardTags.HEALING);
    }
    
    @Override
    public void upp() {
        upgradeMagicNumber(MAGIC_UPGRADE);
        upgradeDamage(DAMAGE_UPGRADE);
    }
    
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if (Settings.FAST_MODE) {
                    this.addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.1F));
                } else {
                    this.addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.3F));
                }
                dmg(m, AttackEffect.NONE);
                if (ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof CrimsonBell) {
                    addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
                }
                isDone = true;
            }
        });
    }
    
    public void triggerOnGlowCheck() {
        this.glowColor = ModFile.Bells.getRinging() != null && ModFile.Bells.getRinging() instanceof CrimsonBell ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
