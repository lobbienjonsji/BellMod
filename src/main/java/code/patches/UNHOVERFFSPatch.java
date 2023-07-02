package code.patches;

import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.cards.cardvars.ReverbMod;
import code.cards.specter_cards.OnEnterDiscardPileCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class UNHOVERFFSPatch {
    @SpirePatch(clz = CardGroup.class, method = "getHoveredCard")
    public static class CallHook
    {
        public static SpireReturn<AbstractCard> Prefix(CardGroup __instance)
        {
            if(ModFile.isBellSelection) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
