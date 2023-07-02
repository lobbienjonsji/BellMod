package code.bells;

import code.TheSpecter;
import code.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.PrismaticShard;

import java.util.ArrayList;

public class BellHandler {
    public static boolean TolledBellThisTurn = false;
    public static int BellsTolledThisTurn = 0;
    public static int BellsTolledThisCombat = 0;
    public ArrayList<AbstractBell> Bells = new ArrayList<>();
    private Vector2[] points = new Vector2[20];
    private Vector2 controlPoint;
    private float arrowScale;
    private float arrowScaleTimer = 0.0F;
    public boolean targeting = false;
    public boolean targetPlayer = true;
    public Hitbox hb;
    
    public BellHandler()
    {
        for (int i = 0; i < this.points.length; i++) {
            this.points[i] = new Vector2();
        }
    }
    
    public void resetAllBells()
    {
        for (AbstractBell bell: Bells) {
            bell.onCooldown = false;
        }
    }
    
    public void update()
    {
        for (AbstractBell bell: Bells)
        {
            bell.update();
        }
    }
    
    public void boostAllBells(int amount)
    {
        for (AbstractBell bell: Bells)
        {
            bell.increaseBaseEffect(amount);
        }
    }
    
    public AbstractBell getHovered(boolean onlyActive)
    {
        for (AbstractBell bell:Bells) {
            if (bell.hb.hovered && (!bell.onCooldown || !onlyActive))
            {
                return bell;
            }
        }
        return null;
    }
    
    public AbstractBell getHovered()
    {
        return getHovered(true);
    }
    
    public void render(SpriteBatch sb)
    {
        for (AbstractBell bell: Bells) {
           bell.render(sb);
        }
        if (this.targeting)
        {
            if(this.getHovered() != null)
            {
                getHovered().renderReticle(sb);
            }
        }
    }
    
    public void clear()
    {
        Bells.clear();
    }
    
    public void setup()
    {
        BellsTolledThisCombat = 0;
        if(AbstractDungeon.player instanceof TheSpecter || AbstractDungeon.player.hasRelic(PrismaticShard.ID)) {
            Bells.add(new FrozenBell());
            Bells.add(new CrimsonBell());
            Bells.add(new SteelBell());
            Bells.add(new NimbusBell());
        }
    }
    
    public AbstractBell getRinging()
    {
        for (AbstractBell bell:Bells) {
            if (bell.onCooldown)
            {
                return bell;
            }
        }
        return null;
    }
    
    public AbstractBell getRandomBell(boolean activeOnly)
    {
        ArrayList<AbstractBell> active = new ArrayList();
        for (AbstractBell bell : Bells) {
            if(!bell.onCooldown || !activeOnly)
            {
                active.add(bell);
            }
        }
        return Wiz.getRandomItem(active);
    }
    
    public AbstractBell getActiveRandomBell()
    {
        return getRandomBell(true);
    }
    
    public void replaceBell(AbstractBell checkIfPresent, AbstractBell Bell)
    {
        for(int i = 0; i < Bells.size(); i++) {
            if (checkIfPresent.id == Bells.get(i).id) {
                Bells.set(i, Bell);
            }
        }
    }
    
    public AbstractBell getBell(int index)
    {
        return Bells.get(index);
    }
    
    public AbstractBell getBell(String id)
    {
    
        for(int i = 0; i < Bells.size(); i++) {
            if (id == Bells.get(i).id) {
                return Bells.get(i);
            }
        }
        return null;
    }
    
    public void updateDescription()
    {
        for (AbstractBell bell: Bells) {
            bell.updateDescription();
        }
    }
    
    public void renderTargetingUi(SpriteBatch sb)
    {
        float x = InputHelper.mX;float y = InputHelper.mY;
        if(targetPlayer)
        {
            this.hb = AbstractDungeon.player.hb;
        }
        this.controlPoint = new Vector2(hb.cX - (x - hb.cX) / 4.0F, y + (y - hb.cY - 40.0F * Settings.scale) / 2.0F);
        if (this.getHovered() == null)
        {
            this.arrowScale = Settings.scale;
            this.arrowScaleTimer = 0.0F;
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
        }
        else
        {
            this.arrowScaleTimer += Gdx.graphics.getDeltaTime();
            if (this.arrowScaleTimer > 1.0F) {
                this.arrowScaleTimer = 1.0F;
            }
            this.arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, this.arrowScaleTimer);
            
            sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));
        }
        Vector2 tmp = new Vector2(this.controlPoint.x - x, this.controlPoint.y - y);
        tmp.nor();
        
        drawCurvedLine(sb, new Vector2(hb.cX, hb.cY - 40.0F * Settings.scale), new Vector2(x, y), this.controlPoint);
        
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.arrowScale, this.arrowScale, tmp
                
                .angle() + 90.0F, 0, 0, 256, 256, false, false);
    }
    
    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control)
    {
        float radius = 7.0F * Settings.scale;
        for (int i = 0; i < this.points.length - 1; i++)
        {
            this.points[i] = (Bezier.quadratic(this.points[i], i / 20.0F, start, control, end, new Vector2()));
            radius += 0.4F * Settings.scale;
            float angle;
            if (i != 0)
            {
                Vector2 tmp = new Vector2(this.points[(i - 1)].x - this.points[i].x, this.points[(i - 1)].y - this.points[i].y);
                angle = tmp.nor().angle() + 90.0F;
            }
            else
            {
                Vector2 tmp = new Vector2(this.controlPoint.x - this.points[i].x, this.controlPoint.y - this.points[i].y);
                angle = tmp.nor().angle() + 270.0F;
            }
            sb.draw(ImageMaster.TARGET_UI_CIRCLE, this.points[i].x - 64.0F, this.points[i].y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }
    }
}
