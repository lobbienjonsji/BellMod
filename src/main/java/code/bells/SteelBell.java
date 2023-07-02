package code.bells;

import code.NotNeeded.Unused.HammerDown;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class SteelBell extends AbstractBell {
    private static final int BASE_EFFECT = 4;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/SteelBell.png"));
    public static final String BELL_ID = makeID(SteelBell.class.getSimpleName());
    public static final String[] KEYWORD_IDS = {makeID("Haunted")};

    public SteelBell() {
        super(AbstractDungeon.player.hb.x - 64.0f * Settings.scale, AbstractDungeon.player.hb.y + 192.0f * Settings.scale, TEXTURE, BASE_EFFECT, BELL_ID, -1);
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, effect)));
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c:AbstractDungeon.player.discardPile.group) {
                    if(c instanceof HammerDown)
                    {
                        addToTop(new DiscardToHandAction(c));
                    }
                }
                isDone = true;
            }
        });
    }
}
