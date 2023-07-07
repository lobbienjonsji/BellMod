package code.bells;

import code.ModFile;
import code.powers.AbstractEasyPower;
import code.powers.ChillTouchPower;
import code.powers.HarmonyPower;
import code.powers.LamentPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static code.bells.BellHandler.BellsTolledThisCombat;
import static code.bells.BellHandler.BellsTolledThisTurn;

public abstract class AbstractBell {
    public String[] bellStrings;
    public String name;
    public String description;
    public Hitbox hb;
    public float reticleAlpha;
    private static Color reticleColor;
    private static Color reticleShadowColor;
    public boolean reticleRendered;
    private float reticleOffset;
    private float reticleAnimTimer;
    private static final float RETICLE_OFFSET_DIST;
    protected Texture img;
    protected float scale;
    protected float target_scale;
    protected int effect;
    protected int base_effect;
    protected int init_base_effect;
    public float cX = 0.0F;
    public float cY = 0.0F;
    private float ringTimer = 0.0F;
    protected boolean onCooldown = false;
    protected String id;
    protected Color c;
    private ArrayList<PowerTip> tips = new ArrayList<>();
    private ArrayList<PowerTip> keywordTips = new ArrayList<>();
    protected int charges;
    
    public AbstractBell(float cX, float cY, Texture img, int base_effect, String ID, int charges)
    {
        this.c = Settings.CREAM_COLOR.cpy();
        this.reticleAlpha = 0.0F;
        this.reticleRendered = false;
        this.reticleOffset = 0.0F;
        this.reticleAnimTimer = 0.0F;
        this.cX = cX;
        this.cY = cY;
        this.img = img;
        this.base_effect = base_effect;
        init_base_effect = base_effect;
        effect = base_effect;
        this.id = ID;
        this.hb = new Hitbox(cX - 64.0F * Settings.scale, cY - 64.0F * Settings.scale,128.0F * Settings.scale, 128.0F * Settings.scale);
        this.charges = charges;
        bellStrings = CardCrawlGame.languagePack.getUIString(ID).TEXT;
        updateDescription();
    }
    public AbstractBell(float cX, float cY, Texture img, int base_effect, String ID, int charges, String[] keywordIds)
    {
       this(cX, cY, img, base_effect, ID, charges);
        for (String keyID: keywordIds) {
            String[] keyText = CardCrawlGame.languagePack.getUIString(keyID).TEXT;
            keywordTips.add(new PowerTip(keyText[0], keyText[1]));
        }
    }
    
    public boolean isOnCooldown() {
        return onCooldown;
    }
    
    public void onToll()
    {
        ModFile.Bells.resetAllBells();
        BellsTolledThisCombat += 1;
        BellsTolledThisTurn += 1;
        onCooldown = true;
        this.effect = base_effect;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if(p instanceof AbstractEasyPower)
            {
                ((AbstractEasyPower) p).onToll(this);
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if(p instanceof AbstractEasyPower)
            {
                effect = ((AbstractEasyPower) p).modifyBellPower(this, effect);
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if(p instanceof AbstractEasyPower)
            {
                effect = ((AbstractEasyPower) p).modifyBellPowerFinal(this, effect);
            }
        }
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped() && !m.halfDead) {
                for (AbstractPower p : m.powers) {
                    if (p instanceof AbstractEasyPower) {
                        ((AbstractEasyPower) p).onToll(this);
                    }
                }
            }
        }
        
        BellHandler.TolledBellThisTurn = true;
        
    }
    
    public abstract void toll();
    
    public void renderReticle(SpriteBatch sb) {
        this.reticleRendered = true;
        this.renderReticleCorner(sb, -this.hb.width / 2.0F + this.reticleOffset, this.hb.height / 2.0F - this.reticleOffset, false, false);
        this.renderReticleCorner(sb, this.hb.width / 2.0F - this.reticleOffset, this.hb.height / 2.0F - this.reticleOffset, true, false);
        this.renderReticleCorner(sb, -this.hb.width / 2.0F + this.reticleOffset, -this.hb.height / 2.0F + this.reticleOffset, false, true);
        this.renderReticleCorner(sb, this.hb.width / 2.0F - this.reticleOffset, -this.hb.height / 2.0F + this.reticleOffset, true, true);
    }
    
    private void renderReticleCorner(SpriteBatch sb, float x, float y, boolean flipX, boolean flipY) {
        reticleShadowColor.a = this.reticleAlpha / 4.0F;
        sb.setColor(reticleShadowColor);
        sb.draw(ImageMaster.RETICLE_CORNER, this.hb.cX + x - 18.0F + 4.0F * Settings.scale, this.hb.cY + y - 18.0F - 4.0F * Settings.scale, 18.0F, 18.0F, 36.0F, 36.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
        reticleColor.a = this.reticleAlpha;
        sb.setColor(reticleColor);
        sb.draw(ImageMaster.RETICLE_CORNER, this.hb.cX + x - 18.0F, this.hb.cY + y - 18.0F, 18.0F, 18.0F, 36.0F, 36.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }
    
    public void update()
    {
        this.hb.update();
        this.effect = base_effect;
        
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if(p instanceof AbstractEasyPower)
            {
                effect = ((AbstractEasyPower) p).modifyBellPower(this, effect);
                effect = ((AbstractEasyPower) p).modifyBellPowerFinal(this, effect);
            }
        }
        
        if (this.hb.hovered) {
            TipHelper.queuePowerTips(this.cX + 128.0F * Settings.scale, this.cY + 64.0F * Settings.scale, tips);
            updateDescription();
        }
        if(!this.onCooldown)
        {
            ringTimer = 0.0f;
        }
        else
        {
            ringTimer += Gdx.graphics.getDeltaTime() * 2;
        }
        this.updateReticle();
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.cX - 64.0F, this.cY - 64.0F + (Settings.scale - 1) * 64.0f, 64.0F, 128.0F, 128.0F,
                128.0F, Settings.scale, Settings.scale, (float) Math.sin(ringTimer) * 45, 0, 0, 128, 128,
                false, false);
        if(charges >= 0)
        {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(charges), this.cX + 48.0f * Settings.scale, this.cY - 58.0f * Settings.scale, Color.MAGENTA,0.7F);
        }

        this.hb.render(sb);
    
        if(init_base_effect < effect)
        {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(effect), this.cX - 48.0f * Settings.scale, this.cY - 58.0f * Settings.scale, Color.GREEN,0.7F);
        }
        else
        {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(effect), this.cX - 48.0f * Settings.scale, this.cY - 58.0f * Settings.scale, this.c,0.7F);
        }
    
    }
    
    public void updateDescription()
    {
       
        this.name = bellStrings[0];
        this.description = bellStrings[1] + effect + bellStrings[2];
        if(bellStrings.length == 4)
        {
            this.description += effect + bellStrings[3];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.addAll(keywordTips);
    }
    
    protected void updateReticle() {
        if (this.reticleRendered) {
            this.reticleRendered = false;
            TipHelper.renderGenericTip(this.cX + 128.0F * Settings.scale, this.cY + 64.0F * Settings.scale, this.name, this.description);
            this.reticleAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
            if (this.reticleAlpha > 1.0F) {
                this.reticleAlpha = 1.0F;
            }
            
            this.reticleAnimTimer += Gdx.graphics.getDeltaTime();
            if (this.reticleAnimTimer > 1.0F) {
                this.reticleAnimTimer = 1.0F;
            }
            
            this.reticleOffset = Interpolation.elasticOut.apply(RETICLE_OFFSET_DIST, 0.0F, this.reticleAnimTimer);
        } else {
            this.reticleAlpha = 0.0F;
            this.reticleAnimTimer = 0.0F;
            this.reticleOffset = RETICLE_OFFSET_DIST;
        }
    }
    
    public void increaseBaseEffect(int amount)
    {
        this.base_effect += amount;
    }
    
    static {
        RETICLE_OFFSET_DIST = 15.0F * Settings.scale;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        reticleShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    }
    
    public void setCharges(int charges) {
        this.charges = charges;
    }
    
    public void addCharges(int charges)
    {
        this.charges += charges;
    }
}
