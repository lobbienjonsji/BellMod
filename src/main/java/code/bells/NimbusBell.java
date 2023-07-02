package code.bells;

import code.actions.BellOfTheWindsAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class NimbusBell extends AbstractBell{
    private static final int BASE_EFFECT = 6;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/WindBell.png"));
    public static final String BELL_ID = makeID(NimbusBell.class.getSimpleName());
    
    public NimbusBell() {
        super(AbstractDungeon.player.hb.x - 64.0f * Settings.scale, AbstractDungeon.player.hb.y + 64.0f * Settings.scale, TEXTURE, BASE_EFFECT, BELL_ID, 0);
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                if(charges >= 12)
                {
                    charges -= 12;
                    this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(effect, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
                    this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(effect, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
                }
                isDone = true;
            }
        });
    }
}
