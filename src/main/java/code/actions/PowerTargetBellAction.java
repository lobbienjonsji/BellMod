package code.actions;

import code.ModFile;
import code.powers.AbstractEasyPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class PowerTargetBellAction extends AbstractGameAction {
    private float startingDuration;
    private AbstractEasyPower power;
    
    public PowerTargetBellAction(AbstractEasyPower power) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.power = power;
    }
    
    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            ModFile.Bells.targeting = false;
            ModFile.isBellSelection = false;
        }
        else {
            AbstractDungeon.player.toHover = null;
            if(duration == startingDuration) {
                ModFile.Bells.targeting = true;
                ModFile.isBellSelection = true;
                ModFile.Bells.targetPlayer = true;
            }
            else if(ModFile.Bells.getHovered() != null && !ModFile.Bells.getHovered().isOnCooldown() && InputHelper.justReleasedClickLeft)
            {
                power.onSelectTarget(ModFile.Bells.getHovered());
                ModFile.Bells.targeting = false;
                ModFile.isBellSelection = false;
                isDone = true;
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
    }
}
