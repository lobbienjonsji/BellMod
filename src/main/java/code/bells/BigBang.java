package code.bells;

import code.ModFile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import static code.ModFile.makeID;
import static code.ModFile.makeImagePath;
import static code.util.TexLoader.getTexture;

public class BigBang  extends AbstractBell {
    private static final int BASE_EFFECT = 22;
    private static final Texture TEXTURE = getTexture(makeImagePath("ui/BigBang.png"));
    public static final String BELL_ID = makeID(BigBang.class.getSimpleName());
    private AbstractBell bell;
    
    public BigBang(int uses, AbstractBell bell) {
        super(bell.cX, bell.cY, TEXTURE, BASE_EFFECT, BELL_ID, uses);
        this.bell = bell;
    }
    
    @Override
    public void toll() {
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(effect, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    
    @Override
    public void onToll()
    {
        super.onToll();
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                charges -= 1;
                if(charges == 0)
                {
                    BigBang.this.bell.onCooldown = false;
                    ModFile.Bells.replaceBell(BigBang.this, BigBang.this.bell);
                }
                isDone = true;
            }
        });
    }
    
    @Override
    public void render(SpriteBatch spriteBatch)
    {
        super.render(spriteBatch);
    }
    
}
