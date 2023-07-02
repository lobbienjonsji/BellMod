package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

import static code.ModFile.makeID;

public class FeedTheAbyssAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    
    public FeedTheAbyssAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.drawPile.group.iterator();
            
            AbstractCard card;
            while(var5.hasNext()) {
                card = (AbstractCard)var5.next();
                tmp.addToRandomSpot(card);
            }
            
            if (tmp.size() == 0) {
                this.isDone = true;
            }
            else if (tmp.size() <= this.amount) {
                for(int i = 0; i < tmp.size(); ++i) {
                    card = tmp.getNCardFromTop(i);
                    this.p.drawPile.moveToDiscardPile(card);
                    if (card.cost > 0) {
                        card.freeToPlayOnce = true;
                    }
                }
                this.isDone = true;
            } else {
                if (this.amount == 1) {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
                }
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                AbstractCard card;
                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    card.unhover();
                    this.p.drawPile.moveToDiscardPile(card);
                    if (card.cost > 0) {
                        card.freeToPlayOnce = true;
                    }
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.tickDuration();
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(FeedTheAbyssAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
