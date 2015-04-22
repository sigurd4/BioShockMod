package com.sigurd4.bioshock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;

import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.item.EnumArmorType;
import com.sigurd4.bioshock.item.ItemAmmo;
import com.sigurd4.bioshock.item.ItemAudioLog;
import com.sigurd4.bioshock.item.ItemConsumable;
import com.sigurd4.bioshock.item.ItemConsumable.EnumConsumableType;
import com.sigurd4.bioshock.item.ItemConsumableAlcohol;
import com.sigurd4.bioshock.item.ItemCrafting;
import com.sigurd4.bioshock.item.ItemEveHypo;
import com.sigurd4.bioshock.item.ItemInfusionHealth;
import com.sigurd4.bioshock.item.ItemInfusionQuantumSuperposition;
import com.sigurd4.bioshock.item.ItemInfusionSalts;
import com.sigurd4.bioshock.item.ItemInfusionShields;
import com.sigurd4.bioshock.item.ItemMoney;
import com.sigurd4.bioshock.item.ItemPassiveGear;
import com.sigurd4.bioshock.item.ItemPassiveTonic;
import com.sigurd4.bioshock.item.ItemPlasmidDrinkable;
import com.sigurd4.bioshock.item.ItemPlasmidInjectable;
import com.sigurd4.bioshock.item.ItemRubbingAlcohol;
import com.sigurd4.bioshock.item.ItemValuable;
import com.sigurd4.bioshock.item.ItemWeaponMelee;
import com.sigurd4.bioshock.item.ItemWeaponShotgun;
import com.sigurd4.bioshock.item.ItemWeaponSkyHook;
import com.sigurd4.bioshock.item.ItemWeaponWrench;
import com.sigurd4.bioshock.passives.Passive;
import com.sigurd4.bioshock.passives.Passive.Type;
import com.sigurd4.bioshock.passives.PassiveAmmoCap;
import com.sigurd4.bioshock.passives.PassiveAngryStompers;
import com.sigurd4.bioshock.passives.PassiveArmor;
import com.sigurd4.bioshock.passives.PassiveBloodToSalts;
import com.sigurd4.bioshock.passives.PassiveBloodlust;
import com.sigurd4.bioshock.passives.PassiveBoozeHound;
import com.sigurd4.bioshock.passives.PassiveBrittleSkinned;
import com.sigurd4.bioshock.passives.PassiveBullRush;
import com.sigurd4.bioshock.passives.PassiveBurningHalo;
import com.sigurd4.bioshock.passives.PassiveCoatOfHarms;
import com.sigurd4.bioshock.passives.PassiveDeathBenefit;
import com.sigurd4.bioshock.passives.PassiveDropCloth;
import com.sigurd4.bioshock.passives.PassiveElementalBoost;
import com.sigurd4.bioshock.passives.PassiveEveLink;
import com.sigurd4.bioshock.passives.PassiveEveSaver;
import com.sigurd4.bioshock.passives.PassiveExecutioner;
import com.sigurd4.bioshock.passives.PassiveExtraExtra;
import com.sigurd4.bioshock.passives.PassiveExtraNutrition;
import com.sigurd4.bioshock.passives.PassiveFilthyLeech;
import com.sigurd4.bioshock.passives.PassiveFireBird;
import com.sigurd4.bioshock.passives.PassiveFrozenField;
import com.sigurd4.bioshock.passives.PassiveHighAndMighty;
import com.sigurd4.bioshock.passives.PassiveHillRunnersHat;
import com.sigurd4.bioshock.passives.PassiveKillToLive;
import com.sigurd4.bioshock.passives.PassiveLastManStanding;
import com.sigurd4.bioshock.passives.PassiveMedicalExpert;
import com.sigurd4.bioshock.passives.PassiveNaturalCamouflage;
import com.sigurd4.bioshock.passives.PassiveNewtonsLaw;
import com.sigurd4.bioshock.passives.PassiveOverkill;
import com.sigurd4.bioshock.passives.PassiveRoarToLife;
import com.sigurd4.bioshock.passives.PassiveScavengersVest;
import com.sigurd4.bioshock.passives.PassiveShelteredLife;
import com.sigurd4.bioshock.passives.PassiveSkyLineReloader;
import com.sigurd4.bioshock.passives.PassiveSportsBoost;
import com.sigurd4.bioshock.passives.PassiveStaticDischarge;
import com.sigurd4.bioshock.passives.PassiveSugarRush;
import com.sigurd4.bioshock.passives.PassiveSurpriseElement;
import com.sigurd4.bioshock.passives.PassiveTicketPuncher;
import com.sigurd4.bioshock.passives.PassiveTunnelVision;
import com.sigurd4.bioshock.passives.PassiveWinterShield;
import com.sigurd4.bioshock.passives.PassiveWrenchJockey;
import com.sigurd4.bioshock.passives.PassiveWrenchLurker;
import com.sigurd4.bioshock.plasmids.PlasmidCycloneTrap;
import com.sigurd4.bioshock.plasmids.PlasmidDevilsKiss;
import com.sigurd4.bioshock.plasmids.PlasmidElectroBolt;
import com.sigurd4.bioshock.plasmids.PlasmidEnrage;
import com.sigurd4.bioshock.plasmids.PlasmidHypnotizeBigDaddy;
import com.sigurd4.bioshock.plasmids.PlasmidIncinerate;
import com.sigurd4.bioshock.plasmids.PlasmidIronsides;
import com.sigurd4.bioshock.plasmids.PlasmidMurderOfCrows;
import com.sigurd4.bioshock.plasmids.PlasmidOldManWinter;
import com.sigurd4.bioshock.plasmids.PlasmidPeepingTom;
import com.sigurd4.bioshock.plasmids.PlasmidReturnToSender;
import com.sigurd4.bioshock.plasmids.PlasmidShockJockey;
import com.sigurd4.bioshock.plasmids.PlasmidUndertow;
import com.sigurd4.bioshock.plasmids.PlasmidUnfinished;
import com.sigurd4.bioshock.plasmids.PlasmidWinterBlast;
import com.sigurd4.bioshock.reference.RefMod;

public class MItems
{
	public Audiologs audiologs = new Audiologs();
	
	public class Audiologs
	{
		public ItemAudioLog audiolog_audio_diary = M.registerItem("audio_diary", (ItemAudioLog)new ItemAudioLog(true).setUnlocalizedName("audiologAudioDiary"), false, new String[]{});
		public ItemAudioLog audiolog_voxophone = M.registerItem("voxophone", (ItemAudioLog)new ItemAudioLog(false).setUnlocalizedName("audiologVoxophone"), false, new String[]{});
	}
	public Infusions infusions = new Infusions();
	
	public class Infusions
	{
		public ItemInfusionHealth health = M.registerItem("infusion_health", new ItemInfusionHealth(), false, new String[]{});
		public ItemInfusionSalts salts = M.registerItem("infusion_shields", new ItemInfusionSalts(), false, new String[]{});
		public ItemInfusionShields shields = M.registerItem("infusion_shields", new ItemInfusionShields(), false, new String[]{});
		public ItemInfusionQuantumSuperposition quantum_superposition = M.registerItem("infusion_quantum_superposition", new ItemInfusionQuantumSuperposition(this.health, this.salts, this.shields), false, new String[]{});
	}
	
	public Money money = new Money();
	
	public class Money
	{
		public ItemMoney dollars = M.registerItem("dollars", (ItemMoney)new ItemMoney().setUnlocalizedName("moneyDollars"), false, new String[]{});
		public ItemMoney silver_eagles = M.registerItem("silver_eagles", (ItemMoney)new ItemMoney().setUnlocalizedName("moneySilverEagles"), false, new String[]{});
	}
	
	public Valuables valuables = new Valuables();
	
	public class Valuables
	{
		public Rapture rapture = new Rapture();
		
		public class Rapture
		{
			public ItemValuable teddy_bear = M.registerItem("valuable_rapture_teddy_bear", (ItemValuable)new ItemValuable(1, 2, M.items.money.dollars).setUnlocalizedName("valuableTeddyBear"), false, new String[]{});
			
			public Ring ring = new Ring();
			
			public class Ring
			{
				public ItemValuable diamond = M.registerItem("valuable_rapture_ring_diamond", (ItemValuable)new ItemValuable(5, 15, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable emerald = M.registerItem("valuable_rapture_ring_emerald", (ItemValuable)new ItemValuable(5, 14, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable enderpearl = M.registerItem("valuable_rapture_ring_enderpearl", (ItemValuable)new ItemValuable(10, 14, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable glowstone = M.registerItem("valuable_rapture_ring_glowstone", (ItemValuable)new ItemValuable(2, 13, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable gold = M.registerItem("valuable_rapture_ring_gold", (ItemValuable)new ItemValuable(4, 10, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable pearl = M.registerItem("valuable_rapture_ring_pearl", (ItemValuable)new ItemValuable(3, 7, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable prismarine = M.registerItem("valuable_rapture_ring_prismarine", (ItemValuable)new ItemValuable(7, 13, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable quartz = M.registerItem("valuable_rapture_ring_quartx", (ItemValuable)new ItemValuable(3, 8, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable ruby = M.registerItem("valuable_rapture_ring_ruby", (ItemValuable)new ItemValuable(4, 14, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
				public ItemValuable silver = M.registerItem("valuable_rapture_ring_silver", (ItemValuable)new ItemValuable(7, 11, M.items.money.dollars).setUnlocalizedName("valuableRing"), false, new String[]{});
			}
			
			public Watch watch = new Watch();
			
			public class Watch
			{
				public ItemValuable gold_leather_black_strip = M.registerItem("valuable_rapture_watch_leather_black_strip", (ItemValuable)new ItemValuable(6, 14, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable gold_leather_brown = M.registerItem("valuable_rapture_watch_gold_leather_brown", (ItemValuable)new ItemValuable(6, 13, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable gold_leather_dark = M.registerItem("valuable_rapture_watch_gold_leather_dark", (ItemValuable)new ItemValuable(6, 14, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable gold_leather_dark_with_steel = M.registerItem("valuable_rapture_watch_gold_leather_dark_with_steel", (ItemValuable)new ItemValuable(6, 13, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable gold_leather_red = M.registerItem("valuable_rapture_watch_gold_leather_red", (ItemValuable)new ItemValuable(6, 12, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable pocket_gold = M.registerItem("valuable_rapture_watch_pocket_gold", (ItemValuable)new ItemValuable(2, 16, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable pocket_steelDark = M.registerItem("valuable_rapture_watch_pocket_steel_dark", (ItemValuable)new ItemValuable(1, 16, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable steel_dark = M.registerItem("valuable_rapture_watch_steel_dark", (ItemValuable)new ItemValuable(6, 11, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable steel_leatherDarkWithGold = M.registerItem("valuable_rapture_watch_steel_leather_dark_with_gold", (ItemValuable)new ItemValuable(6, 10, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
				public ItemValuable steel_light = M.registerItem("valuable_rapture_watch_steel_light", (ItemValuable)new ItemValuable(6, 10, M.items.money.dollars).setUnlocalizedName("valuableWatch"), false, new String[]{});
			}
			
			public Bracelet bracelet = new Bracelet();
			
			public class Bracelet
			{
				public ItemValuable diamond = M.registerItem("valuable_rapture_bracelet_diamond", (ItemValuable)new ItemValuable(5, 19, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable emerald = M.registerItem("valuable_rapture_bracelet_emerald", (ItemValuable)new ItemValuable(5, 17, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable emerald_gold = M.registerItem("valuable_rapture_bracelet_emerald_gold", (ItemValuable)new ItemValuable(5, 18, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable glowstone = M.registerItem("valuable_rapture_bracelet_glowstone", (ItemValuable)new ItemValuable(4, 16, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable obsidian = M.registerItem("valuable_rapture_bracelet_obsidian", (ItemValuable)new ItemValuable(6, 17, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable pearl = M.registerItem("valuable_rapture_bracelet_pearl", (ItemValuable)new ItemValuable(3, 13, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable pearl_blue = M.registerItem("valuable_rapture_bracelet_pearl_blue", (ItemValuable)new ItemValuable(3, 6, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable pearl_green = M.registerItem("valuable_rapture_bracelet_pearl_green", (ItemValuable)new ItemValuable(3, 6, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable pearl_pink = M.registerItem("valuable_rapture_bracelet_pearl_pink", (ItemValuable)new ItemValuable(3, 5, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable prismarine = M.registerItem("valuable_rapture_bracelet_prismarine", (ItemValuable)new ItemValuable(4, 16, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
				public ItemValuable ruby = M.registerItem("valuable_rapture_bracelet_ruby", (ItemValuable)new ItemValuable(5, 18, M.items.money.dollars).setUnlocalizedName("valuableBracelet"), false, new String[]{});
			}
			
			public LadiesShoe ladiesShoe = new LadiesShoe();
			
			public class LadiesShoe
			{
				public ItemValuable beige = M.registerItem("valuable_rapture_ladies_shoe_beige", (ItemValuable)new ItemValuable(2, 5, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable black = M.registerItem("valuable_rapture_ladies_shoe_black", (ItemValuable)new ItemValuable(3, 5, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable blue = M.registerItem("valuable_rapture_ladies_shoe_blue", (ItemValuable)new ItemValuable(2, 4, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable green = M.registerItem("valuable_rapture_ladies_shoe_green", (ItemValuable)new ItemValuable(2, 4, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable pink = M.registerItem("valuable_rapture_ladies_shoe_pink", (ItemValuable)new ItemValuable(2, 4, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable red = M.registerItem("valuable_rapture_ladies_shoe_red", (ItemValuable)new ItemValuable(3, 5, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
				public ItemValuable white = M.registerItem("valuable_rapture_ladies_shoe_white", (ItemValuable)new ItemValuable(3, 5, M.items.money.dollars).setUnlocalizedName("valuableLadiesShoe"), false, new String[]{});
			}
			
			public Necklace necklace = new Necklace();
			
			public class Necklace
			{
				public ItemValuable diamond = M.registerItem("valuable_rapture_necklace_diamond", (ItemValuable)new ItemValuable(5, 25, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable emerald = M.registerItem("valuable_rapture_necklace_emerald", (ItemValuable)new ItemValuable(5, 21, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable glowstone = M.registerItem("valuable_rapture_necklace_glowstone", (ItemValuable)new ItemValuable(3, 19, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable enderpearl = M.registerItem("valuable_rapture_necklace_enderpearl", (ItemValuable)new ItemValuable(11, 18, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable obsidian = M.registerItem("valuable_rapture_necklace_obsidian", (ItemValuable)new ItemValuable(7, 23, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable pearl = M.registerItem("valuable_rapture_necklace_pearl", (ItemValuable)new ItemValuable(2, 12, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable prismarine = M.registerItem("valuable_rapture_necklace_prismarine", (ItemValuable)new ItemValuable(4, 22, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
				public ItemValuable ruby = M.registerItem("valuable_rapture_necklace_ruby", (ItemValuable)new ItemValuable(4, 24, M.items.money.dollars).setUnlocalizedName("valuableNecklace"), false, new String[]{});
			}
			
			public Doll doll = new Doll();
			
			public class Doll
			{
				public ItemValuable blue = M.registerItem("valuable_rapture_doll_blue", (ItemValuable)new ItemValuable(1, 8, M.items.money.dollars).setUnlocalizedName("valuableDoll"), false, new String[]{});
				public ItemValuable green = M.registerItem("valuable_rapture_doll_green", (ItemValuable)new ItemValuable(1, 8, M.items.money.dollars).setUnlocalizedName("valuableDoll"), false, new String[]{});
				public ItemValuable red = M.registerItem("valuable_rapture_doll_red", (ItemValuable)new ItemValuable(1, 8, M.items.money.dollars).setUnlocalizedName("valuableDoll"), false, new String[]{});
				public ItemValuable yellow = M.registerItem("valuable_rapture_doll_yellow", (ItemValuable)new ItemValuable(1, 8, M.items.money.dollars).setUnlocalizedName("valuableDoll"), false, new String[]{});
			}
			
			public Bar Bar = new Bar();
			
			public class Bar
			{
				public ItemValuable gold_large = M.registerItem("valuable_rapture_gold_bar_large", (ItemValuable)new ItemValuable(100, 100, M.items.money.dollars).setUnlocalizedName("valuableGoldBar"), false, new String[]{});
				public ItemValuable gold_small = M.registerItem("valuable_rapture_gold_bar_small", (ItemValuable)new ItemValuable(50, 50, M.items.money.dollars).setUnlocalizedName("valuableGoldBar"), false, new String[]{});
			}
			
			public Wallet wallet = new Wallet();
			
			public class Wallet
			{
				public ItemValuable beige = M.registerItem("valuable_rapture_wallet_beige", (ItemValuable)new ItemValuable(8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable black = M.registerItem("valuable_rapture_wallet_black", (ItemValuable)new ItemValuable(8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable brown = M.registerItem("valuable_rapture_wallet_brown", (ItemValuable)new ItemValuable(8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable gray = M.registerItem("valuable_rapture_wallet_gray", (ItemValuable)new ItemValuable(8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable red = M.registerItem("valuable_rapture_wallet_red", (ItemValuable)new ItemValuable(8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable beige_open = M.registerItem("valuable_rapture_wallet_beige_open", (ItemValuable)new ItemValuable(-8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable black_open = M.registerItem("valuable_rapture_wallet_black_open", (ItemValuable)new ItemValuable(-8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable brown_open = M.registerItem("valuable_rapture_wallet_brown_open", (ItemValuable)new ItemValuable(-8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable gray_open = M.registerItem("valuable_rapture_wallet_gray_open", (ItemValuable)new ItemValuable(-8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable red_open = M.registerItem("valuable_rapture_wallet_red_open", (ItemValuable)new ItemValuable(-8, 30, M.items.money.dollars).setUnlocalizedName("valuableWallet"), false, new String[]{});
			}
			
			public Purse purse = new Purse();
			
			public class Purse
			{
				public ItemValuable beige = M.registerItem("valuable_rapture_purse_beige", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable black = M.registerItem("valuable_rapture_purse_black", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable blue = M.registerItem("valuable_rapture_purse_blue", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable brown = M.registerItem("valuable_rapture_purse_brown", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable gray = M.registerItem("valuable_rapture_purse_gray", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable green = M.registerItem("valuable_rapture_purse_green", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable orange = M.registerItem("valuable_rapture_purse_orange", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable red = M.registerItem("valuable_rapture_purse_red", (ItemValuable)new ItemValuable(1, 26, M.items.money.dollars).setUnlocalizedName("valuablePurse"), false, new String[]{});
			}
		}
		
		public Columbia columbia = new Columbia();
		
		public class Columbia
		{
			public Wallet wallet = new Wallet();
			
			public class Wallet
			{
				public ItemValuable black = M.registerItem("valuable_columbia_wallet_black", (ItemValuable)new ItemValuable(1, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable brown = M.registerItem("valuable_columbia_wallet_brown", (ItemValuable)new ItemValuable(1, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable red = M.registerItem("valuable_columbia_wallet_red", (ItemValuable)new ItemValuable(1, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable black_open = M.registerItem("valuable_columbia_wallet_black_open", (ItemValuable)new ItemValuable(-5, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable brown_open = M.registerItem("valuable_columbia_wallet_brown_open", (ItemValuable)new ItemValuable(-5, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
				public ItemValuable red_open = M.registerItem("valuable_columbia_wallet_red_open", (ItemValuable)new ItemValuable(-5, 25, M.items.money.silver_eagles).setUnlocalizedName("valuableWallet"), false, new String[]{});
			}
			
			public Purse purse = new Purse();
			
			public class Purse
			{
				public ItemValuable beige = M.registerItem("valuable_columbia_purse_beige", (ItemValuable)new ItemValuable(1, 24, M.items.money.silver_eagles).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable black = M.registerItem("valuable_columbia_purse_black", (ItemValuable)new ItemValuable(2, 26, M.items.money.silver_eagles).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable brown = M.registerItem("valuable_columbia_purse_brown", (ItemValuable)new ItemValuable(1, 23, M.items.money.silver_eagles).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable gray = M.registerItem("valuable_columbia_purse_gray", (ItemValuable)new ItemValuable(4, 25, M.items.money.silver_eagles).setUnlocalizedName("valuablePurse"), false, new String[]{});
				public ItemValuable orange = M.registerItem("valuable_columbia_purse_orange", (ItemValuable)new ItemValuable(3, 23, M.items.money.silver_eagles).setUnlocalizedName("valuablePurse"), false, new String[]{});
			}
			
			public ItemValuable cash_bag = M.registerItem("valuable_columbia_cash_bag", (ItemValuable)new ItemValuable(13, 61, M.items.money.silver_eagles).setUnlocalizedName("valuableCashBag"), false, new String[]{});
			
			public Bar bar = new Bar();
			
			public class Bar
			{
				public ItemValuable silver = M.registerItem("valuable_columbia_silver_bar", (ItemValuable)new ItemValuable(100, 100, M.items.money.silver_eagles).setUnlocalizedName("valuableSilverBar"), false, new String[]{});
				public ItemValuable gold = M.registerItem("valuable_columbia_gold_bar", (ItemValuable)new ItemValuable(500, 500, M.items.money.silver_eagles).setUnlocalizedName("valuableGoldBar"), false, new String[]{});
			}
		}
	}
	
	public CraftingItems crafting = new CraftingItems();
	
	public class CraftingItems
	{
		public ItemCrafting battery = M.registerItem("battery", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBattery"), false, new String[]{});
		public ItemCrafting brackets = M.registerItem("brackets", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBrackets"), false, new String[]{});
		public ItemCrafting brass_tube = M.registerItem("brass_tube", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBrassTube"), false, new String[]{});
		public ItemCrafting bullet_standard = M.registerItem("bullet_standard", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBulletStandard"), false, new String[]{});
		public ItemCrafting bullet_armor_piercing = M.registerItem("bullet_armor_piercing", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBulletArmorPiercing"), false, new String[]{});
		public ItemCrafting bullet_antipersonnel = M.registerItem("bullet_antipersonnel", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemBulletAntipersonnel"), false, new String[]{});
		public ItemCrafting chlorophyll_solution = M.registerItem("vial_chlorophyll_solution", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemChlorophyllSolution").setContainerItem(M.items.crafting.empty_vial), false, new String[]{});
		public ItemCrafting cup = M.registerItem("cup", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemCup"), false, new String[]{});
		public ItemCrafting cup_white = M.registerItem("cup2", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemCup"), false, new String[]{});
		public ItemCrafting empty_auto_round = M.registerItem("empty_auto_round", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyAutoRound"), false, new String[]{});
		public ItemCrafting empty_bullet_cartridge = M.registerItem("empty_bullet_cartridge", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyBulletCartridge"), false, new String[]{});
		public ItemCrafting empty_hypo = M.registerItem("hypo_empty", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyHypo"), false, new String[]{});
		public ItemCrafting empty_pipette = M.registerItem("pipette_empty", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyPipette"), false, new String[]{});
		public ItemCrafting empty_pistol_round = M.registerItem("empty_pistol_round", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyPistolRound"), false, new String[]{});
		public ItemCrafting empty_shell_casing = M.registerItem("empty_shell_casing", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyShellCasing"), false, new String[]{});
		public ItemCrafting empty_tank = M.registerItem("tank_empty", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyTank"), false, new String[]{});
		public ItemCrafting empty_vial = M.registerItem("vial_empty", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEmptyVial"), false, new String[]{});
		public ItemCrafting enzyme_sample = M.registerItem("pipette_enzyme_sample", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemEnzymeSample").setContainerItem(M.items.crafting.empty_pipette), false, new String[]{});
		public ItemConsumable fresh_water = M.items.consumables.drink.fresh_water;
		public ItemCrafting gear_steel = M.registerItem("gear_steel", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemGear"), false, new String[]{});
		public ItemCrafting glue = M.registerItem("glue", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemGlue"), false, new String[]{});
		public ItemCrafting kerosene = M.registerItem("kerosene_can", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemKerosene"), false, new String[]{});
		public ItemCrafting opened_vacuum_cleaner = M.registerItem("opened_vacuum_cleaner", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemOpenedVacuumCleaner"), false, new String[]{});
		public ItemCrafting pneumo_hooks = M.registerItem("pneumo_hooks", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemPneumoHooks"), false, new String[]{});
		public ItemCrafting rubber_hose = M.registerItem("rubber_hose", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemRubberHose"), false, new String[]{});
		public ItemRubbingAlcohol rubbing_alcohol = M.registerItem("rubbing_alcohol", (ItemRubbingAlcohol)new ItemRubbingAlcohol(400).setUnlocalizedName("craftingItemRubbingAlcohol"), false, new String[]{});
		public ItemCrafting screw = M.registerItem("screw", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemScrew"), false, new String[]{});
		public ItemCrafting sea_slug_carcass = M.registerItem("sea_slug_carcass", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemSeaSlugCarcass").setMaxStackSize(1), false, new String[]{});
		public ItemCrafting shotgun_shots = M.registerItem("shotgun_shots", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemShotgunShots"), false, new String[]{});
		public ItemCrafting wires = M.registerItem("wires", (ItemCrafting)new ItemCrafting().setUnlocalizedName("craftingItemWires"), false, new String[]{});
	}
	
	public Consumables consumables = new Consumables();
	
	public class Consumables
	{
		public Misc misc = new Misc();
		
		public class Misc
		{
			public ItemConsumable AdamInjectable = M.registerItem("adam_hypo", (ItemConsumable)new ItemConsumable(EnumConsumableType.INJECTABLE, "adam", 0, 0, 0, 0.0F, 24, 0, false, null).setUnlocalizedName("adamInjectable").setCreativeTab(M.tabs.core).setContainerItem(M.items.crafting.empty_hypo), false, new String[]{});
			public ItemEveHypo EveInjectable = M.registerItem("eve_hypo", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.INJECTABLE, "eve", 0, 100, 0, 0.0F, 24, 0, null).setUnlocalizedName("eveInjectable").setCreativeTab(M.tabs.core).setContainerItem(M.items.crafting.empty_hypo), false, new String[]{});
			public ItemEveHypo EveDrinkableSmall = M.registerItem("eve_drinkable_small", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 25, 0, 0.0F, 16, 0, "cork").setUnlocalizedName("eveDrinkableSmall").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemEveHypo EveDrinkableMedium = M.registerItem("eve_drinkable_medium", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 50, 0, 0.0F, 24, 0, "cork").setUnlocalizedName("eveDrinkableMedium").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemEveHypo EveDrinkableLarge = M.registerItem("eve_drinkable_large", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 100, 0, 0.0F, 32, 0, "cork").setUnlocalizedName("eveDrinkableLarge").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemEveHypo EveSaltsSmall = M.registerItem("eve_salts_small", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 25, 0, 0.0F, 16, 0, "cork").setUnlocalizedName("eveSaltsSmall").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemEveHypo EveSaltsMedium = M.registerItem("eve_salts_medium", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 50, 0, 0.0F, 24, 0, "cork").setUnlocalizedName("eveSaltsMedium").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemEveHypo EveSaltsLarge = M.registerItem("eve_salts_large", (ItemEveHypo)new ItemEveHypo(EnumConsumableType.DRINK, "eve", 0, 100, 0, 0.0F, 32, 0, "cork").setUnlocalizedName("eveSaltsLarge").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemConsumable FirstAidKit = M.registerItem("first_aid_kit", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "medkit", 20, 0, 0, 0.0F, 24, 0, false, "metal_box").setUnlocalizedName("firstAidKit").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemConsumable SpiderSplicerOrgan = M.registerItem("spider_splicer_organ", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "spiderSplicerOrgan", 20, 0, 3, 0.1F, 32, 0, false, null).setUnlocalizedName("spiderSplicerOrgan").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemConsumable MedicalKitSmall = M.registerItem("medical_kit_small", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "medkit", 9, 0, 0, 0.0F, 16, 0, false, "zip").setUnlocalizedName("medicalKitSmall").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemConsumable MedicalKitMedium = M.registerItem("medical_kit_medium", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "medkit", 12, 0, 0, 0.0F, 24, 0, false, "zip").setUnlocalizedName("medicalKitMedium").setCreativeTab(M.tabs.core), false, new String[]{});
			public ItemConsumable MedicalKitLarge = M.registerItem("medical_kit_large", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "medkit", 16, 0, 0, 0.0F, 32, 0, false, "zip").setUnlocalizedName("medicalKitLarge").setCreativeTab(M.tabs.core), false, new String[]{});
		}
		
		public Alcohol alcohol = new Alcohol();
		
		public class Alcohol
		{
			//public Item Consumable = new Consumable(EnumConsumableType.ALCOHOL,"",4,-10,0,1.5F,20,90).setUnlocalizedName("consumable");
			public ItemConsumableAlcohol ConsumableArcadiaMerlot = M.registerItem("consumable_arcadia_merlot", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "arcadiaMerlot", 4, -10, 0, 2.2F, 32, 220, false, "cork").setUnlocalizedName("consumableArcadiaMerlot"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableChecknyaVodka = M.registerItem("consumable_checknya_vodka", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "checknyaVodka", 4, -10, 0, 1.5F, 30, 240, false, "cork").setUnlocalizedName("consumableChecknyaVodka"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableFineGin = M.registerItem("consumable_fine_gin", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "fineGin", 4, -10, 0, 0.5F, 23, 160, false, "cork").setUnlocalizedName("consumableFineGin"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableLacanScotch = M.registerItem("consumable_lacan_scotch", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "lacanScotch", 4, -10, 0, 0.5F, 29, 220, false, "cork").setUnlocalizedName("consumableLacanScotch"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableMoonbeamAbsinthe = M.registerItem("consumable_moonbeam_absinthe", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "moonbeamAbsinthe", -1000, 100, 0, 0.5F, 29, ItemConsumable.nauseaTrigger, false, "cork").setUnlocalizedName("consumableMoonbeamAbsinthe"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableMoonshine = M.registerItem("consumable_moonshine", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "moonshine", 1000, -100, 0, 8.9F, 21, ItemConsumable.nauseaTrigger, false, "cork").setUnlocalizedName("consumableMoonshine"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableOldHarbingerBeer = M.registerItem("consumable_old_harbinger_beer", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "oldHarbingerBeer", 4, -10, 0, 1.5F, 20, 140, false, "beer").setUnlocalizedName("consumableOldHarbingerBeer"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableOldTomWhiskey = M.registerItem("consumable_old_tom_whiskey", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "oldTomWhiskey", 4, -10, 0, 0.5F, 20, 180, false, "cork").setUnlocalizedName("consumableOldTomWhiskey"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableRedRibbonBrandy = M.registerItem("consumable_red_ribbon_brandy", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "redRibbonBrandy", 4, -10, 0, 0.5F, 32, 190, false, "cork").setUnlocalizedName("consumableRedRibbonBrandy"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableTateMerlot = M.registerItem("consumable_tate_merlot", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "tateMerlot", 4, -10, 0, 1.5F, 32, 220, false, "cork").setUnlocalizedName("consumableTateMerlot"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableKingAbsinthe = M.registerItem("consumable_king_absinthe", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "kingAbsinthe", -1000, 100, 0, 0.5F, 29, ItemConsumable.nauseaTrigger, false, "cork").setUnlocalizedName("consumableKingAbsinthe"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableColumbiaLagerBeer = M.registerItem("consumable_columbia_lager_beer", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "columbiaLagerBeer", 4, -10, 0, 1.5F, 20, 180, false, "beer").setUnlocalizedName("consumableColumbiaLagerBeer"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableEmporianShippingBourbon = M.registerItem("consumable_emporian_shipping_bourbon", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "emporianShippingBourbon", 4, -10, 0, 0.9F, 33, 220, false, "cork").setUnlocalizedName("consumableEmporianShippingBourbon"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableSplendidChampionBrandy = M.registerItem("consumable_splendid_champion_brandy", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "splendidChampionBrandy", 4, -10, 0, 1.6F, 23, 200, false, "cork").setUnlocalizedName("consumableSplendidChampionBrandy"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableJeanDePickartChampagne = M.registerItem("consumable_jean_de_pickart_champagne", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "jeanDePickartChampagne", 4, -10, 0, 1.0F, 32, 240, false, "beer").setUnlocalizedName("consumableJeanDePickartChampagne"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableFinktonsSpecialDryGin = M.registerItem("consumable_finktons_special_dry_gin", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "finktonsSpecialDryGin", 4, -10, 0, 1.1F, 20, 170, false, "cork").setUnlocalizedName("consumableFinktonsSpecialDryGin"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableUnknownBooze = M.registerItem("consumable_unknown_booze", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "unknownBooze", 1000, -100, 0, 8.9F, 21, ItemConsumable.nauseaTrigger, false, "lid").setUnlocalizedName("consumableUnknownBooze"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableVillavicencioVermouth = M.registerItem("consumable_villavicencio_vermouth", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "villavicencioVermouth", 4, -10, 0, 1.3F, 30, 220, false, "cork").setUnlocalizedName("consumableVillavicencioVermouth"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableColumbia1830RyeWhiskey = M.registerItem("consumable_columbia_1830_rye_whiskey", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "columbia1830RyeWhiskey", 4, -10, 0, 1.8F, 33, 240, false, "cork").setUnlocalizedName("consumableColumbia1830RyeWhiskey"), false, new String[]{});
			public ItemConsumableAlcohol ConsumableVictorValleyWine = M.registerItem("consumable_victor_valley_wine", (ItemConsumableAlcohol)new ItemConsumableAlcohol(EnumConsumableType.ALCOHOL, "victorValleyWine", 4, -10, 0, 2.0F, 32, 210, false, "cork").setUnlocalizedName("consumableVictorValleyWine"), false, new String[]{});
		}
		
		public Drink drink = new Drink();
		
		public class Drink
		{
			//public Item Consumable = new Consumable(EnumConsumableType.DRINK,"",0,10,0,0.3F,20,0).setUnlocalizedName("consumable");
			public ItemConsumable coffee_thermos = M.registerItem("consumable_coffee_thermos", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "coffeeThermos", 0, 12, 0, 0.3F, 29, 0, false, "thermos").setUnlocalizedName("consumableCoffeeThermos").setContainerItem(M.items.consumables.drink.coffee_thermos), false, new String[]{});
			public ItemConsumable coffee_rapture_tin = M.registerItem("consumable_rapture_coffee_tin", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "raptureCoffeeTin", 0, 12, 0, 0.3F, 28, 0, false, "tin").setUnlocalizedName("consumableRaptureCoffeeTin"), false, new String[]{});
			public ItemConsumable coffee_cup_white = M.registerItem("consumable_coffee_cup2", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "coffeeCupPorcelain", 0, 12, 0, 0.3F, 15, 0, false, null).setUnlocalizedName("consumableCoffeeCupPorcelain").setContainerItem(M.items.crafting.cup_white), false, new String[]{});
			public ItemConsumable tea_thermos = M.registerItem("consumable_tea_thermos", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "teaThermos", 0, 9, 0, 0.5F, 29, 0, false, "thermos").setUnlocalizedName("consumableTeaThermos").setContainerItem(M.items.consumables.drink.tea_thermos), false, new String[]{});
			public ItemConsumable tea_cup_white = M.registerItem("consumable_tea_cup2", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "teaCupPorcelain", 0, 9, 0, 0.5F, 15, 0, false, null).setUnlocalizedName("consumableTeaCupPorcelain").setContainerItem(M.items.crafting.cup_white), false, new String[]{});
			public ItemConsumable fresh_water = M.registerItem("consumable_fresh_water", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "freshWater", 3, 0, 0, 0.8F, 20, 0, false, "cork")
			{
				@Override
				public CreativeTabs[] getCreativeTabs()
				{
					return new CreativeTabs[]{M.tabs.consumables, M.tabs.craftingItems};
				}
			}.setUnlocalizedName("consumableFreshWater").setContainerItem(Items.glass_bottle), false, new String[]{});
			public ItemConsumable hop_up_cola = M.registerItem("consumable_hop_up_cola", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "hopUpCola", 0, 10, 0, 0.1F, 20, 0, true, "soda").setUnlocalizedName("consumableHopUpCola"), false, new String[]{});
			public ItemConsumable milk_bottle = M.registerItem("consumable_milk_bottle", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "milkBottle", 3, 0, 0, 0.8F, 20, 0, false, "cork").setUnlocalizedName("consumableMilkBottle").setContainerItem(Items.glass_bottle), false, new String[]{});
			public ItemConsumable coffee_columbia_tin = M.registerItem("consumable_columbia_coffee_tin", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "columbiaCoffeeTin", 0, 12, 0, 0.3F, 28, 0, false, "tin").setUnlocalizedName("consumableColumbiaCoffeeTin"), false, new String[]{});
			public ItemConsumable coffee_cup = M.registerItem("consumable_coffee_cup", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "coffeeCup", 0, 12, 0, 0.3F, 15, 0, false, null).setUnlocalizedName("consumableCoffeeCup").setContainerItem(M.items.crafting.cup), false, new String[]{});
			public ItemConsumable pap_drink_soda = M.registerItem("consumable_pap_drink_soda", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "papDrinkSoda", 0, 10, 0, 0.1F, 20, 0, true, "soda").setUnlocalizedName("consumablePapDrinkSoda"), false, new String[]{});
			public ItemConsumable tea_cup = M.registerItem("consumable_tea_cup", (ItemConsumable)new ItemConsumable(EnumConsumableType.DRINK, "teaCup", 0, 9, 0, 0.5F, 15, 0, false, null).setUnlocalizedName("consumableTeaCup").setContainerItem(M.items.crafting.cup), false, new String[]{});
		}
		
		public Food food = new Food();
		
		public class Food
		{
			//public Item Consumable = new Consumable(EnumConsumableType.FOOD,"",4,0,4,0.0F,32,0).setUnlocalizedName("consumable");
			public ItemConsumable crackers = M.registerItem("consumable_crackers", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "crackers", 3, 0, 0, 0.3F, 12, 0, false, null).setUnlocalizedName("consumableCrackers"), false, new String[]{});
			public ItemConsumable creme_filled_cake = M.registerItem("consumable_creme_filled_cake", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cake", 4, 0, 2, 0.3F, 24, 0, true, "paper").setUnlocalizedName("consumableCremeFilledCake"), false, new String[]{});
			public ItemConsumable canned_beans = M.registerItem("consumable_canned_beans", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cannedBeans", 4, 0, 5, 4.8F, 32, 0, false, "tin").setUnlocalizedName("consumableCannedBeans"), false, new String[]{});
			public ItemConsumable canned_fruit = M.registerItem("consumable_canned_fruits", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cannedFruit", 4, 0, 3, 0.9F, 32, 0, false, "tin").setUnlocalizedName("consumableCannedFruit"), false, new String[]{});
			public ItemConsumable fontaine_sardines = M.registerItem("consumable_fontaine_sardines", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "Sardines", 4, 0, 4, 3.6F, 26, 0, false, "tin").setUnlocalizedName("consumableSardines"), false, new String[]{});
			public ItemConsumable pep_bar = M.registerItem("consumable_pep_bar", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pepBar", 4, 8, 2, 0.1F, 14, 0, true, "paper").setUnlocalizedName("consumablePepBar"), false, new String[]{});
			public ItemConsumable saltys_potato_chips = M.registerItem("consumable_saltys_potato_chips", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "SaltysPotatoChips", 4, 0, 2, 0.2F, 26, 0, false, "paper").setUnlocalizedName("consumableSaltysPotatoChips"), false, new String[]{});
			public ItemConsumable potted_meat = M.registerItem("consumable_potted_meat", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "PottedMeat", 4, 0, 8, 6.2F, 32, 0, false, "tin").setUnlocalizedName("consumablePottedMeat"), false, new String[]{});
			public ItemConsumable bread = M.registerItem("consumable_bread", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "bread", 4, 0, 5, 0.7F, 32, 0, false, null).setUnlocalizedName("consumableBread"), false, new String[]{});
			public ItemConsumable bread_2 = M.registerItem("consumable_bread2", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "bread", 4, 0, 5, 0.6F, 32, 0, false, null).setUnlocalizedName("consumableBread"), false, new String[]{});
			public ItemConsumable apple = M.registerItem("consumable_apple", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "apple", 3, 0, 4, 0.3F, 24, 0, false, null).setUnlocalizedName("consumableApple"), false, new String[]{});
			public ItemConsumable apple_rotten = M.registerItem("consumable_apple_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "appleRotten", -3, 0, 4, 0.3F, 24, 0, false, null).setUnlocalizedName("consumableAppleRotten"), false, new String[]{});
			public ItemConsumable banana = M.registerItem("consumable_banana", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "banana", 3, 0, 3, 0.4F, 20, 0, false, "peel_banana").setUnlocalizedName("consumableBanana"), false, new String[]{});
			public ItemConsumable banana_rotten = M.registerItem("consumable_banana_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "bananaRotten", -3, 0, 3, 0.4F, 20, 0, false, "peel_banana").setUnlocalizedName("consumableBananaRotten"), false, new String[]{});
			public ItemConsumable finktons_baked_beans = M.registerItem("consumable_finktons_baked_beans", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "finktonsBakedBeans", 4, 0, 5, 4.8F, 32, 0, false, "tin").setUnlocalizedName("consumableFinktonsBakedBeans"), false, new String[]{});
			public ItemConsumable chocos_milk_chocolate = M.registerItem("consumable_chocos_milk_chocolate", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "chocos", 4, 0, 2, 0.1F, 14, 0, true, "paper").setUnlocalizedName("consumableChocos"), false, new String[]{});
			public ItemConsumable chocolate_cake = M.registerItem("consumable_chocolate_cake", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cake", 5, 0, 4, 0.2F, 64, 0, true, null).setUnlocalizedName("consumableChocolateCake"), false, new String[]{});
			public ItemConsumable columbia_wheat_cereal = M.registerItem("consumable_columbia_what_cereal", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "columbiaWheatCereal", 4, 0, 4, 0.1F, 25, 0, false, null).setUnlocalizedName("consumableColumbiaWheatCereal"), false, new String[]{});
			public ItemConsumable cheese_wheel = M.registerItem("consumable_cheese_wheel", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cheese", 3, 0, 2, 2.3F, 64, 0, false, null).setUnlocalizedName("consumableCheeseWheel"), false, new String[]{});
			public ItemConsumable maize = M.registerItem("consumable_maize", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "maize", 3, 0, 1, 0.9F, 28, 0, false, null).setUnlocalizedName("consumableMaize"), false, new String[]{});
			public ItemConsumable maize_rotten = M.registerItem("consumable_maize_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "maizeRotten", -3, 0, 1, 0.9F, 28, 0, false, null).setUnlocalizedName("consumableMaizeRotten"), false, new String[]{});
			public ItemConsumable cotton_candy = M.registerItem("consumable_cotton_candy", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "cottonCandy", 3, 0, 1, 0.0F, 29, 0, true, null).setUnlocalizedName("consumableCottonCandy"), false, new String[]{});
			public ItemConsumable confect_box = M.registerItem("consumable_confect_box", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "confectBox", 4, 0, 1, 0.1F, 25, 0, true, null).setUnlocalizedName("consumableConfectBox"), false, new String[]{});
			public ItemConsumable hot_dog = M.registerItem("consumable_hot_dog", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "hotDog", 3, 0, 5, 5.6F, 26, 0, false, null).setUnlocalizedName("consumableHotDog"), false, new String[]{});
			public ItemConsumable pickles = M.registerItem("consumable_pickles", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pickles", 3, 0, 2, 2.1F, 23, 0, false, "cork").setUnlocalizedName("consumablePickles"), false, new String[]{});
			public ItemConsumable orange = M.registerItem("consumable_orange", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "orange", 3, 0, 4, 0.3F, 24, 0, false, "peel_orange").setUnlocalizedName("consumableOrange"), false, new String[]{});
			public ItemConsumable orange_rotten = M.registerItem("consumable_orange_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "orangeRotten", -3, 0, 4, 0.3F, 24, 0, false, "peel_orange").setUnlocalizedName("consumableOrangeRotten"), false, new String[]{});
			public ItemConsumable peanuts = M.registerItem("consumable_peanuts", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "peanuts", 3, 0, 2, 1.5F, 19, 0, false, "paper").setUnlocalizedName("consumablePeanuts"), false, new String[]{});
			public ItemConsumable pear = M.registerItem("consumable_pear", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pear", 3, 0, 4, 0.3F, 23, 0, false, null).setUnlocalizedName("consumablePear"), false, new String[]{});
			public ItemConsumable pear_rotten = M.registerItem("consumable_pear_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pearRotten", -3, 0, 4, 0.3F, 23, 0, false, null).setUnlocalizedName("consumablePearRotten"), false, new String[]{});
			public ItemConsumable pineapple = M.registerItem("consumable_pineapple", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pineapple", 3, 0, 4, 0.3F, 25, 0, false, null).setUnlocalizedName("consumablePineapple"), false, new String[]{});
			public ItemConsumable pineapple_rotten = M.registerItem("consumable_pineapple_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "pineappleRotten", -3, 0, 4, 0.3F, 25, 0, false, null).setUnlocalizedName("consumablePineappleRotten"), false, new String[]{});
			public ItemConsumable columbias_best_popcorn = M.registerItem("consumable_columbias_best_popcorn", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "columbiasBestPopcorn", 3, 0, 1, 0.8F, 24, 0, false, null).setUnlocalizedName("consumableColumbiasBestPopcorn"), false, new String[]{});
			public ItemConsumable potato = M.registerItem("consumable_potato", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "potato", 1, 0, 4, 2.8F, 18, 0, false, null).setUnlocalizedName("consumablePotato"), false, new String[]{});
			public ItemConsumable harvays_potato_chips = M.registerItem("consumable_harvays_potato_chips", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "harvaysPotatoChips", 4, 0, 2, 0.2F, 26, 0, false, "paper").setUnlocalizedName("consumableHarvaysPotatoChips"), false, new String[]{});
			public ItemConsumable finkton_rations = M.registerItem("consumable_finkton_rations", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "finktonRations", 3, 0, 5, 5.1F, 32, 0, false, "metal_box").setUnlocalizedName("consumableFinktonRations"), false, new String[]{});
			public ItemConsumable sandwich = M.registerItem("consumable_sandwitch", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "sandwich", 4, 0, 2, 7.3F, 23, 0, false, null).setUnlocalizedName("consumableSandwich"), false, new String[]{});
			public ItemConsumable columbia_brand_sardines = M.registerItem("consumable_columbia_brand_sardines", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "columbiaBrandSardines", 4, 0, 4, 3.2F, 23, 0, false, "tin").setUnlocalizedName("consumableColumbiaBrandSardines"), false, new String[]{});
			public ItemConsumable finkton_canned_spinach = M.registerItem("consumable_finkton_canned_spinach", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "finktonCannedSpinach", 4, 0, 3, 1.3F, 26, 0, false, "tin").setUnlocalizedName("consumableFinktonCannedSpinach"), false, new String[]{});
			public ItemConsumable finktons_tomato_soup = M.registerItem("consumable_finktons_tomato_soup", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "finktonsTomatoSoup", 4, 0, 4, 0.5F, 25, 0, false, "tin").setUnlocalizedName("consumableFinktonsTomatoSoup"), false, new String[]{});
			public ItemConsumable watermelon = M.registerItem("consumable_watermelon", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "watermelon", 4, 0, 4, 0.2F, 35, 0, false, null).setUnlocalizedName("consumableWatermelon"), false, new String[]{});
			public ItemConsumable watermelon_rotten = M.registerItem("consumable_watermelon_rotten", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "watermelonRotten", -4, 0, 4, 0.2F, 35, 0, false, null).setUnlocalizedName("consumableWatermelonRotten"), false, new String[]{});
			public ItemConsumable white_oatsCereal = M.registerItem("consumable_white_oats_cereal", (ItemConsumable)new ItemConsumable(EnumConsumableType.FOOD, "whiteOatsCereal", 4, 0, 4, 0.1F, 26, 0, false, null).setUnlocalizedName("consumableWhiteOatsCereal"), false, new String[]{});
		}
		
		public Tobacco tobacco = new Tobacco();
		
		public class Tobacco
		{
			//tobacco //public Item Consumable = new Consumable(EnumConsumableType.TOBACCO,"",-1,4,0,0.0F,32,0).setUnlocalizedName("consumable");
			public ItemConsumable nico_time_cigarettes = M.registerItem("consumable_nico_time_cigarettes", (ItemConsumable)new ItemConsumable(EnumConsumableType.TOBACCO, "nicoTimeCigarettes", -2, 4, 0, 0.0F, 12, 0, false, "lighter").setUnlocalizedName("consumableNicoTimeCigarettes"), false, new String[]{});
			public ItemConsumable oxford_club_cigarettes = M.registerItem("consumable_oxford_club_cigarettes", (ItemConsumable)new ItemConsumable(EnumConsumableType.TOBACCO, "oxfordClubCigarettes", -2, 4, 0, 0.0F, 12, 0, false, "lighter").setUnlocalizedName("consumableOxfordClubCigarettes"), false, new String[]{});
			public ItemConsumable pipe = M.registerItem("consumable_pipe", (ItemConsumable)new ItemConsumable(EnumConsumableType.TOBACCO, "pipe", -2, 5, 0, 0.0F, 24, 0, false, "lighter").setUnlocalizedName("consumablePipe"), false, new String[]{});
			public ItemConsumable habana_especial_cigars = M.registerItem("consumable_habana_especial_cigars", (ItemConsumable)new ItemConsumable(EnumConsumableType.TOBACCO, "habanaEspecialCigars", -2, 5, 0, 0.0F, 18, 0, false, "lighter").setUnlocalizedName("consumableHabanaEspecialCigars"), false, new String[]{});
			public ItemConsumable columbia_cigarettes = M.registerItem("consumable_columbia_cigarettes", (ItemConsumable)new ItemConsumable(EnumConsumableType.TOBACCO, "columbiaCigarettes", -2, 4, 0, 0.0F, 12, 0, false, "lighter").setUnlocalizedName("consumableColumbiaCigarettes"), false, new String[]{});
		}
		
		public Medical medical = new Medical();
		
		public class Medical
		{
			//medical //public Item Consumable = new Consumable(EnumConsumableType.MEDICAL,"",2,0,0,0.0F,64,0).setUnlocalizedName("consumable");
			public ItemConsumable aspirin = M.registerItem("consumable_aspirin", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "aspirin", 0, 10, 0, 0.0F, 10, 100, false, "cork").setUnlocalizedName("consumableAspirin"), false, new String[]{});
			public ItemConsumable bandages = M.registerItem("consumable_bandages", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "bandages", 4, 0, 0, 0.0F, 1, 0, false, null).setUnlocalizedName("consumableBandages"), false, new String[]{});
			public ItemConsumable cure_all = M.registerItem("consumable_doc_hollcrofts_cure_all", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "cureAll", 1, 8, 0, 0.0F, 25, 0, false, "lid").setUnlocalizedName("consumableCureAll"), false, new String[]{});
			public ItemConsumable vitamins = M.registerItem("consumable_vitamins", (ItemConsumable)new ItemConsumable(EnumConsumableType.MEDICAL, "vitamins", 2, 0, 0, 0.2F, 9, 0, false, "cork").setUnlocalizedName("consumableVitamins"), false, new String[]{});
		}
	}
}

