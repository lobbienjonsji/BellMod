package code.bells;

import code.ModFile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class FireBell extends AbstractBell {
    private static final int BASE_EFFECT = 4;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/Firebell.png"));
    public static final String BELL_ID = makeID(SteelBell.class.getSimpleName());
    private CrimsonBell bellOfBlood;
    private int uses;
    public FireBell(int uses, CrimsonBell bellOfBlood) {
        super(AbstractDungeon.player.hb.x + 230.0f * Settings.scale, AbstractDungeon.player.hb.y + 192.0f * Settings.scale, TEXTURE, BASE_EFFECT, BELL_ID, -1);
        this.uses = uses;
        this.bellOfBlood = bellOfBlood;
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, effect));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, effect)));
    }
    
    @Override
    public void onToll()
    {
        super.onToll();
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                uses -= 1;
                if(uses == 0)
                {
                    FireBell.this.bellOfBlood.onCooldown = false;
                    ModFile.Bells.replaceBell(FireBell.this, FireBell.this.bellOfBlood);
                }
                isDone = true;
            }
        });
    }
    
    @Override
    public void render(SpriteBatch spriteBatch)
    {
        super.render(spriteBatch);
        FontHelper.renderFontCentered(spriteBatch, FontHelper.cardEnergyFont_L, Integer.toString(uses), this.cX + 58.0f * Settings.scale, this.cY - 58.0f * Settings.scale, this.c,0.7F);
    }
    
    public void setCharges(int uses) {
        this.uses = uses;
    }
}
