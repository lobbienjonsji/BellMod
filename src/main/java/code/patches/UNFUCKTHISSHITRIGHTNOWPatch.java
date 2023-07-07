package code.patches;

import code.ModFile;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UNFUCKTHISSHITRIGHTNOWPatch  {
    @SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
    public static class CallHook
    {
        public static void Prefix(DiscardAtEndOfTurnAction __instance)
        {
            ModFile.ReverbActive = false;
        }
    }
}
