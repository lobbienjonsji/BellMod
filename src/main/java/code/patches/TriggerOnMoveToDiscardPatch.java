package code.patches;

import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.bells.NimbusBell;
import code.cards.cardvars.ReverbMod;
import code.cards.specter_cards.OnEnterDiscardPileCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class TriggerOnMoveToDiscardPatch {
    @SpirePatch(clz = CardGroup.class, method = "addToTop")
    public static class CallHook
    {
        public static void Prefix(CardGroup __instance, AbstractCard card)
        {
            if(__instance.type == CardGroup.CardGroupType.DISCARD_PILE) {
                if (CardModifierManager.hasModifier(card, ReverbMod.ID)) {
                    ((ReverbMod) (CardModifierManager.getModifiers(card, ReverbMod.ID).get(0))).fetch(card);
                }
                if (card instanceof OnEnterDiscardPileCard) {
                    ((OnEnterDiscardPileCard) card).triggerOnEnterDiscard();
                }
                ModFile.cardsEnteredDiscardPileThisTurn += 1;
                if(ModFile.Bells.getBell(NimbusBell.BELL_ID) != null)
                {
                    ModFile.Bells.getBell(NimbusBell.BELL_ID).addCharges(1);
                }
            }
        }
    }
}
