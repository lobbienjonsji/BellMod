package code;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import code.bells.AllBellTargetingHandler;
import code.bells.BellHandler;
import code.bells.BellTargetingHandler;
import code.cards.specter_cards.HeartAttack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.SecondDamage;
import code.cards.cardvars.SecondMagicNumber;
import code.relics.AbstractEasyRelic;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModFile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostPlayerUpdateSubscriber,
        PreRoomRenderSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        PostDeathSubscriber,
        StartGameSubscriber,
        OnPlayerTurnStartSubscriber,
        PostInitializeSubscriber,
        PostRenderSubscriber,
        PostPowerApplySubscriber{

    public static final String modID = "thespecter";
    public static BellHandler Bells = new BellHandler();
    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This should be changed eventually
    
    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/attack.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/skill.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/power.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/attack.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/skill.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/power.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";
    public static boolean isBellSelection = false;
    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };
    public static int cardsEnteredDiscardPileThisTurn = 0;
    
    
    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public ModFile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheSpecter.Enums.SPECTER_LIGHT_BLUE, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        ModFile thismod = new ModFile();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSpecter(TheSpecter.characterStrings.NAMES[1], TheSpecter.Enums.THE_SPECTER),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheSpecter.Enums.THE_SPECTER);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
    
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    
    @Override
    public void receivePostPlayerUpdate() {
        if(AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            Bells.update();
        }
    }
    
    @Override
    public void receivePreRoomRender(SpriteBatch sb) {
        if(AbstractDungeon.player != null && !AbstractDungeon.isScreenUp) {
            sb.setColor(Color.WHITE);
            Bells.render(sb);
        }
    }
    
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        Bells.setup();
    }
    
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        Bells.clear();
    }
    
    @Override
    public void receivePostDeath()
    {
        Bells.clear();
    }
    
    @Override
    public void receiveStartGame() {
        Bells.clear();
    }
    
    @Override
    public void receivePostInitialize() {
        CustomTargeting.registerCustomTargeting(BellTargetingHandler.BELL, new BellTargetingHandler());
        CustomTargeting.registerCustomTargeting(AllBellTargetingHandler.BELL_INCLUDING_ON_COOLDOWN, new AllBellTargetingHandler());
    }
    
    @Override
    public void receiveOnPlayerTurnStart() {
        BellHandler.TolledBellThisTurn = false;
        BellHandler.BellsTolledThisTurn = 0;
        cardsEnteredDiscardPileThisTurn = 0;
    }
    
    @Override
    public void receivePostRender(SpriteBatch spriteBatch) {
        if(isBellSelection)
        {
            Bells.renderTargetingUi(spriteBatch);
        }
    }
    
    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(abstractCreature != AbstractDungeon.player && abstractPower.type == AbstractPower.PowerType.DEBUFF)
        {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();
    
                    AbstractCard c;
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        if (c instanceof AbstractEasyCard) {
                            ((AbstractEasyCard) c).onApplyDebuff();
                        }
                    }
    
                    var1 = AbstractDungeon.player.drawPile.group.iterator();
    
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        if (c instanceof AbstractEasyCard) {
                            ((AbstractEasyCard) c).onApplyDebuff();
                        }
                    }
    
                    var1 = AbstractDungeon.player.hand.group.iterator();
    
                    while(var1.hasNext()) {
                        c = (AbstractCard)var1.next();
                        if (c instanceof AbstractEasyCard) {
                            ((AbstractEasyCard) c).onApplyDebuff();
                        }
                    }
    
                    this.isDone = true;
                }
            });
        }
    }
}
