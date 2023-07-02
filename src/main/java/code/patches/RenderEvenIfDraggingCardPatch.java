package code.patches;

import basemod.ReflectionHacks;
import code.ModFile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.util.ArrayList;

public class RenderEvenIfDraggingCardPatch {
    @SpirePatch(clz = TipHelper.class, method = "render")
    public static class CallHook {
        public static SpireReturn Prefix(SpriteBatch sb) {
            {
                if (!Settings.hidePopupDetails && (boolean) ReflectionHacks.getPrivateStatic(TipHelper.class,
                        "renderedTipThisFrame") && ModFile.Bells.getHovered() != null) {
                    ReflectionHacks.privateStaticMethod(TipHelper.class, "renderPowerTips",
                                    float.class, float.class, SpriteBatch.class, ArrayList.class)
                            .invoke(new Object[]{ReflectionHacks.getPrivateStatic(TipHelper.class, "drawX"),
                                    ReflectionHacks.getPrivateStatic(TipHelper.class, "drawY"),
                                    sb,
                                    ReflectionHacks.getPrivateStatic(TipHelper.class, "POWER_TIPS")});
                    ReflectionHacks.setPrivateStatic(TipHelper.class, "renderedTipThisFrame", false);
                    return SpireReturn.Return();
                }
                return SpireReturn.Continue();
            }
        }
    }
}
