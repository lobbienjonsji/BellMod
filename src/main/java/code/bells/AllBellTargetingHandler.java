package code.bells;

import code.ModFile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AllBellTargetingHandler extends TargetingHandler {
    @SpireEnum
    public static AbstractCard.CardTarget BELL_INCLUDING_ON_COOLDOWN;
    
    private AbstractBell hovered;
    
    @Override
    public void updateHovered() {
        hovered = ModFile.Bells.getHovered(false);
    }
    
    @Override
    public AbstractBell getHovered() {
        return hovered;
    }
    
    @Override
    public void clearHovered() {
        hovered = null;
    }
    
    @Override
    public void renderReticle(SpriteBatch sb) {
        if(hovered != null)
        {
            hovered.renderReticle(sb);
        }
    }
    
    @Override
    public boolean hasTarget() {
        return hovered != null;
    }
    
    public static AbstractBell getTarget(AbstractCard card) {
        return CustomTargeting.getCardTarget(card);
    }
}
