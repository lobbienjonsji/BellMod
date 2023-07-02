package code.bells;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class FrozenBell extends AbstractBell {
    private static final int BASE_EFFECT = 4;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/FrostBell.png"));
    public static final String BELL_ID = makeID(FrozenBell.class.getSimpleName());
    public static final String[] KEYWORD_IDS = {makeID("Block")};
    
    public FrozenBell() {
        super(AbstractDungeon.player.hb.x + 230.0f * Settings.scale, AbstractDungeon.player.hb.y + 64.0f * Settings.scale, TEXTURE, BASE_EFFECT, BELL_ID, -1, KEYWORD_IDS);
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, effect));
    }
}
