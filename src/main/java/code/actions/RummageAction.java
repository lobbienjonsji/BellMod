package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static code.ModFile.makeID;

public class RummageAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private float startingDuration;
    
    public RummageAction(int numCards) {
        this.amount = numCards;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            Iterator var1;
            AbstractCard c;
            if (this.duration == this.startingDuration) {
                var1 = AbstractDungeon.player.powers.iterator();
                
                while(var1.hasNext()) {
                    AbstractPower p = (AbstractPower)var1.next();
                    p.onScry();
                }
                
                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }
                
                if(AbstractDungeon.player.drawPile.size() == 1)
                {
                    AbstractDungeon.player.drawPile.moveToHand(AbstractDungeon.player.drawPile.getTopCard());
                    isDone = true;
                    return;
                }
                
                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (this.amount != -1) {
                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                        tmpGroup.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                    }
                } else {
                    Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();
                    
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        tmpGroup.addToBottom(c);
                    }
                }
                
                AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, TEXT[0], false);
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                for (AbstractCard card:AbstractDungeon.gridSelectScreen.targetGroup.group) {
                    if(!AbstractDungeon.gridSelectScreen.selectedCards.contains(card))
                    {
                        AbstractDungeon.player.drawPile.moveToDiscardPile(card);
                        card.triggerOnManualDiscard();
                        GameActionManager.incrementDiscard(false);
                    }
                }
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    AbstractDungeon.player.drawPile.moveToHand(c);
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            
            this.tickDuration();
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(RummageAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
