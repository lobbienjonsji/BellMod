package code.bells;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class CrimsonBell extends AbstractBell {
    private static final int BASE_EFFECT = 6;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/BloodBell.png"));
    public static final String BELL_ID = makeID(CrimsonBell.class.getSimpleName());
    public CrimsonBell() {
        super(AbstractDungeon.player.hb.x + 230.0f * Settings.scale, AbstractDungeon.player.hb.y + 192.0f * Settings.scale, TEXTURE, BASE_EFFECT, BELL_ID, -1);
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(
                new DamageInfo(AbstractDungeon.player, effect, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
