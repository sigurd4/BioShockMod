package com.sigurd4.bioshock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.sigurd4.bioshock.element.Element;
import com.sigurd4.bioshock.item.EnumArmorType;
import com.sigurd4.bioshock.item.ItemAmmo;
import com.sigurd4.bioshock.item.ItemArmorDivingSuit;
import com.sigurd4.bioshock.item.ItemArmorDivingSuitTank;
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
import com.sigurd4.bioshock.item.ItemTank;
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
	
	public Plasmids plasmids = new Plasmids();
	
	public class Plasmids
	{
		public Injectable injectable = new Injectable();
		
		public class Injectable
		{
			public ItemPlasmidInjectable cyclone_trap = M.registerItem(new ItemPlasmidInjectable(new PlasmidCycloneTrap("cyclone_trap", 20, -1, false, new String[]{"Teach your enemies a lesson", "they'll never forget"})), false, new String[]{});
			public ItemPlasmidInjectable electro_bolt = M.registerItem(new ItemPlasmidInjectable(new PlasmidElectroBolt("electro_bolt", 13, 42, false, new String[]{"Don't be a dolt - use Electro Bolt!"})), false, new String[]{});
			public ItemPlasmidInjectable enrage = M.registerItem(new ItemPlasmidInjectable(new PlasmidEnrage("enrage", 14, 35, false, new String[]{"Make your foes fight each other!"})), false, new String[]{});
			public ItemPlasmidInjectable hypnotizeBigDaddy = M.registerItem(new ItemPlasmidInjectable(new PlasmidHypnotizeBigDaddy("hypnotize_big_daddy", 200, -1, false, new String[]{"Watch as he fights to protect you."})), false, new String[]{});
			public ItemPlasmidInjectable incinerate = M.registerItem(new ItemPlasmidInjectable(new PlasmidIncinerate("incinerate", 18, 37, false, new String[]{"Fire at your fingertips!"})), false, new String[]{});
			public ItemPlasmidInjectable insect_swarm = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("insect_swarm", 24, 41, false, new String[]{"Nothing clears a room like", "swarms of stinging bees."})), false, new String[]{});
			public ItemPlasmidInjectable security_bullseye = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("security_bullseye", 14, 24, false, new String[]{"Light them up!"})), false, new String[]{});
			public ItemPlasmidInjectable sonic_boom = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("sonic_boom", 21, 0, false, new String[]{"When push comes to shove."})), false, new String[]{});
			public ItemPlasmidInjectable target_dummy = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("target_dummy", 25, -1, false, new String[]{"They take the heat... so you", "don't have to!"})), false, new String[]{});
			public ItemPlasmidInjectable telekinesis = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("telekinesis", 4, -1, false, new String[]{"Mind over matter!"})), false, new String[]{});
			public ItemPlasmidInjectable winter_blast = M.registerItem(new ItemPlasmidInjectable(new PlasmidWinterBlast("winter_blast", 16, 38, false, new String[]{"Give your foes the cold shoulder!"})), false, new String[]{});
			public ItemPlasmidInjectable rescue_little_sister = M.registerItem(new ItemPlasmidInjectable(new PlasmidUnfinished("rescue_little_sister", 0, -1, false, new String[]{"To save one life", "is to save the world entire."})), false, new String[]{});
		}
		
		public Drinkable drinkable = new Drinkable();
		
		public class Drinkable
		{
			public ItemPlasmidDrinkable old_man_winter = M.registerItem(new ItemPlasmidDrinkable(new PlasmidOldManWinter("old_man_winter", 24, 54, false, new String[]{"Freeze your foes with", "this arctic ally!"})), false, new String[]{});
			public ItemPlasmidDrinkable peeping_tom = M.registerItem(new ItemPlasmidDrinkable(new PlasmidPeepingTom("peeping_tom", 1, 24, false, 3, new String[]{"Turn every room into a peepshow!"})), false, new String[]{});
			public ItemPlasmidDrinkable bucking_bronco = M.registerItem(new ItemPlasmidDrinkable(new PlasmidUnfinished("bucking_bronco", 31, 47, true, new String[]{"Break even the curliest wolf!"})), false, new String[]{});
			public ItemPlasmidDrinkable charge = M.registerItem(new ItemPlasmidDrinkable(new PlasmidUnfinished("charge", 18, -1, true, new String[]{"Blow your enemies away", "with a powerful CHARGE!"})), false, new String[]{});
			public ItemPlasmidDrinkable devils_kiss = M.registerItem(new ItemPlasmidDrinkable(new PlasmidDevilsKiss("devils_kiss", 31, 53, true, new String[]{"Light the way!"})), false, new String[]{});
			public ItemPlasmidDrinkable ironsides = M.registerItem(new ItemPlasmidDrinkable(new PlasmidIronsides("ironsides", 1, -1, true, 2, new String[]{"Generate a bullet catching shield!"})), false, new String[]{});
			public ItemPlasmidDrinkable murder_of_crows = M.registerItem(new ItemPlasmidDrinkable(new PlasmidMurderOfCrows("murder_of_crows", 32, 57, true, new String[]{"Proven deterrent against hooligans"})), false, new String[]{});
			public ItemPlasmidDrinkable possession = M.registerItem(new ItemPlasmidDrinkable(new PlasmidUnfinished("possession", 50, 64, true, new String[]{"Any STALLION can be TAMED"})), false, new String[]{});
			public ItemPlasmidDrinkable return_to_sender = M.registerItem(new ItemPlasmidDrinkable(new PlasmidReturnToSender("return_to_sender", 1, 35, true, 1, new String[]{"Send your enemies' attacks", "back where they came from!"})), false, new String[]{});
			public ItemPlasmidDrinkable shock_jockey = M.registerItem(new ItemPlasmidDrinkable(new PlasmidShockJockey("shock_jockey", 23, 57, true, new String[]{"Who needs the power company?"})), false, new String[]{});
			public ItemPlasmidDrinkable undertow = M.registerItem(new ItemPlasmidDrinkable(new PlasmidUndertow("undertow", 18, 36, true, new String[]{"Wash away your enemies!"})), false, new String[]{});
		}
	}
	
	public Passives passives = new Passives();
	
	public class Passives
	{
		public Tonics tonics = new Tonics();
		
		public class Tonics
		{
			public Combat combat = new Combat();
			
			public class Combat
			{
				public ItemPassiveTonic armored_shell = new ItemPassiveTonic(new PassiveArmor("armored_shell", "armoredShell", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 - 15F / 100F), new String[]{"Useful in any hazardous situation,", "Armored Shell offers fantastic", "protection against life's bumps", "and bruises. Don't be a softie", " -- use Armored Shell now!"});
				public ItemPassiveTonic armored_shell_2 = new ItemPassiveTonic(new PassiveArmor("armored_shell_2", "armoredShell2", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 - 25F / 100F), new String[]{"When the bullets and blows are", "blasting away, you need all", "the protection you can get.", "Armored Shell Two provides more", "protection than our competitor!"});
				//damage research
				//damage research 2
				public ItemPassiveTonic electric_flesh = new ItemPassiveTonic(new PassiveElementalBoost("electric_flesh", "electricFlesh", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.ELECTRICITY, 1 - 75F / 100, 1 + 30F / 100), new String[]{"Supercharge your body with", "Electric Flesh, the ultimate in", "electricity enhancements.", "Insulate yourself from harm with", "the new Electric Flesh!"});
				public ItemPassiveTonic electric_flesh_2 = new ItemPassiveTonic(new PassiveElementalBoost("electric_flesh_2", "electricFlesh2", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.ELECTRICITY, 0, 1 + 60F / 100), new String[]{"When we said Electric Flesh", "was the ultimate in electricity", "enhancements, we turned out", "to be premature.", "New Electric Flesh Two is", "even better! Be the shock-er, not the", "shock-ee, with Electric Flesh Two!"});
				public ItemPassiveTonic frozen_field = new ItemPassiveTonic(new PassiveFrozenField("frozen_field", "frozenField", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 - 15F / 100, 10F / 100), new String[]{"Leave your foes out in", "the cold with Frozen Field!"});
				public ItemPassiveTonic frozen_field_2 = new ItemPassiveTonic(new PassiveFrozenField("frozen_field_2", "frozenField2", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 - 30F / 100, 20F / 100), new String[]{"Don't let yourself be frozen out", "-- get Frozen Field Two today!"});
				public ItemPassiveTonic human_inferno = new ItemPassiveTonic(new PassiveElementalBoost("human_inferno", "humanInferno", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.FIRE, 1 - 75F / 100, 1 + 30F / 100), new String[]{"Human Inferno", "-- the hottest Gene Tonic in Rapture!"});
				public ItemPassiveTonic human_inferno_2 = new ItemPassiveTonic(new PassiveElementalBoost("human_inferno_2", "humanInferno", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.FIRE, 0, 1 + 60F / 100), new String[]{"Human Inferno Two", "-- hotter than ever!"});
				//machine buster
				//machine buster 2
				//photographer's eye
				//photographer's eye 2
				public ItemPassiveTonic static_discharge = new ItemPassiveTonic(new PassiveStaticDischarge("_discharge", "Discharge", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.ELECTRICITY, 3, 15F / 100), new String[]{"Ryan Industries introduces the latest", "in wartime deterrent genetics.", "Static Discharge makes you a walking Tesla Coil,", "zapping anything and everything foolish", "enough to strike you. Feel safe, be safe", "with Static Discharge!"});
				public ItemPassiveTonic static_discharge_2 = new ItemPassiveTonic(new PassiveStaticDischarge("_discharge_2", "Discharge2", new Passive[]{}, new Passive[]{}, Type.COMBAT, Element.ELECTRICITY, 5, 25F / 100), new String[]{"Is your old Static Discharge not", "slowing them down enough?", "Upgrade to new Static Discharge Two, today", "(A Ryan Industries Gene Tonic)!"});
				public ItemPassiveTonic wrench_jockey = new ItemPassiveTonic(new PassiveWrenchJockey("wrench_jockey", "wrenchJockey", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 + 75F / 100), new String[]{"Wrench Jockey bulks up your upper body,", "allowing you to wield club-like weapons", "with unprecedented skill and power!"});
				public ItemPassiveTonic wrench_jockey_2 = new ItemPassiveTonic(new PassiveWrenchJockey("wrench_jockey_2", "wrenchJockey2", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 + 175F / 100), new String[]{"When your opponent has Wrench Jockey,", "how can you hope to beat him in a fight?", "By installing Wrench Jockey Two, of course!", "Don't get caught with last year's model,", "upgrade today!"});
				public ItemPassiveTonic wrench_lurker = new ItemPassiveTonic(new PassiveWrenchLurker("wrench_lurker", "wrenchLurker", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 + 50F / 100, 1 - 60F / 100), new String[]{"When fighting those stronger or faster than", "yourself, you'll need every advantage", "possible in a scrum. Wrench Lurker allows you", "to make the most of your opportunities when", "your antagonist is caught off guard."});
				public ItemPassiveTonic wrench_lurker_2 = new ItemPassiveTonic(new PassiveWrenchLurker("wrench_lurker_2", "wrenchLurker2", new Passive[]{}, new Passive[]{}, Type.COMBAT, 1 + 100F / 100, 1 - 90F / 100), new String[]{"Only a fool fights fair. When you want to", "take them down from behind, be smart,", "use Wrench Lurker Two!"});
				//drill power
				//drill power 2
				//drill specialist
				//elemental storm
				//fire storm
				//headhunter
				//ice storm
				//drill dash
			}
			
			public Engineering engineering = new Engineering();
			
			public class Engineering
			{
				//alarm expert
				//alarm expert 2
				//clever inventor
				//focused hacker
				//focused hacker 2
				//hacking expert
				//hacking expert 2
				//public  ItemPassiveTonic prolific_inventor = new ItemPassiveTonic(new PassiveProlificInventor("prolific_inventor", "prolificInventor", new Passive[]{}, new Passive[]{}, Type.ENGINEERING, 2), new String[]{""});
				//safecracker
				//safecracker 2
				//security expert
				//security expert 2
				//shorten alarms
				//shorten alarms 2
				//speedy hacker
				//speedy hacker 2
				//vending expert
				//vending expert 2
				//deadly machines
				//handyman
				//hardy machines
				//thrifty hacker
			}
			
			public Physical physical = new Physical();
			
			public class Physical
			{
				public ItemPassiveTonic bloodlust = new ItemPassiveTonic(new PassiveBloodlust("bloodlust", "bloodlust", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 25F / 100), new String[]{"Bloodlust heals your body and your mind as you swing!", "Be red in tooth and claw", "-- with Bloodlust!"});
				public ItemPassiveTonic booze_hound = new ItemPassiveTonic(new PassiveBoozeHound("booze_hound", "boozeHound", new Passive[]{}, new Passive[]{}, Type.PHYSICAL), new String[]{"Take full advantage of Rapture's distilleries and vintners.", "Drink to your health with Booze Hound!"});
				public ItemPassiveTonic eve_link = new ItemPassiveTonic(new PassiveEveLink("eve_link", "eveLink", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 10), new String[]{"Get more out of your First Aid Kits with EVE Link!", "This revolutionary new Gene Tonic causes", "your body to produce EVE whenever", "you use First Aid Kits."});
				public ItemPassiveTonic eve_link_2 = new ItemPassiveTonic(new PassiveEveLink("eve_link_2", "eveLink2", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 20), new String[]{"Get more EVE with every First Aid Kit", "by using EVE Link Two!"});
				public ItemPassiveTonic eve_saver = new ItemPassiveTonic(new PassiveEveSaver("eve_saver", "eveSaver", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 - 10F / 100), new String[]{"In today's genetic wonderland, you probably", "feel like there's never enough EVE at hand.", "With EVE Saver, your EVE will", "go farther than ever before!"});
				public ItemPassiveTonic eve_saver_2 = new ItemPassiveTonic(new PassiveEveSaver("eve_saver_2", "eveSaver2", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 - 15F / 100), new String[]{"Is Eve Saver not quite enough for you?", "Never change. Try Eve Saver 2 today!"});
				public ItemPassiveTonic extra_nutrition = new ItemPassiveTonic(new PassiveExtraNutrition("extra_nutrition", "extraNutrition", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 10F / 100), new String[]{"Extra Nutrition boosts your body's", "natural ability to turn food", "into renewed vitality."});
				public ItemPassiveTonic extra_nutrition_2 = new ItemPassiveTonic(new PassiveExtraNutrition("extra_nutrition_2", "extraNutrition2", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 15F / 100), new String[]{"Make your food even healthier", "with new Extra Nutrition Two!"});
				public ItemPassiveTonic extra_nutrition_3 = new ItemPassiveTonic(new PassiveExtraNutrition("extra_nutrition_3", "extraNutrition3", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 20F / 100), new String[]{"The latest in the Extra Nutrition line", "makes food taste twice as good as it used to.", "Try it today!"});
				//hacker's delight
				//hacker's delight 2
				//hacker's delight 3
				public ItemPassiveTonic medical_expert = new ItemPassiveTonic(new PassiveMedicalExpert("medical_expert", "medicalExpert", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 20F / 100), new String[]{"With Medical Expert, your", "First Aid Kits will go farther,", "healing sickness and injury at a rate", "you're sure to find astonishing.", "Don't use a First Aid Kit without", "your best friend, Medical Expert!"});
				public ItemPassiveTonic medical_expert_2 = new ItemPassiveTonic(new PassiveMedicalExpert("medical_expert_2", "medicalExpert2", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 40F / 100), new String[]{"With new Medical Expert Two,", "First Aid Kits heal you more than ever!"});
				public ItemPassiveTonic medical_expert_3 = new ItemPassiveTonic(new PassiveMedicalExpert("medical_expert_3", "medicalExpert3", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 60F / 100), new String[]{"Heal your injuries in the blink", "of an eye with new Medical Expert Three.", "Your best friend just got better!"});
				public ItemPassiveTonic natural_camouflage = new ItemPassiveTonic(new PassiveNaturalCamouflage("natural_camouflage", "naturalCamouflage", new Passive[]{}, new Passive[]{}, Type.PHYSICAL), new String[]{"Need some peace and quiet?", "Splice in Natural Camouflage, stop moving,", "and just fade away. Remember,", "not seeing is believing!"});
				//scrounger
				//security evasion
				//security evasion 2
				public ItemPassiveTonic sports_boost = new ItemPassiveTonic(new PassiveSportsBoost("sportsboost", "sportsBoost", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 10F / 100), new String[]{"In today's dangerous times,", "it never hurts to be a little faster,", "a little quicker on the draw. Remember,", "you don't have to outrun the crazed Splicer", "- just your neighbor!"});
				public ItemPassiveTonic sports_boost_2 = new ItemPassiveTonic(new PassiveSportsBoost("sportsboost_2", "sportsBoost2", new Passive[]{}, new Passive[]{}, Type.PHYSICAL, 1 + 20F / 100), new String[]{"Tired of being outrun by your neighbor", "with SportsBoost installed?", "Get new SportsBoost Two, and outrun HIM!"});
				//cure all
				//elemental sponge
				//elemental vampire
				//fountain of youth
				//short circuit
				//short circuit 2
			}
		}
		
		public Gear gear = new Gear();
		
		public class Gear
		{
			public Hats hats = new Hats();
			
			public class Hats
			{
				//gear
				public ItemPassiveGear ammo_cap = new ItemPassiveGear(new PassiveAmmoCap("ammo_cap", "ammoCap", new Passive[]{}, new Passive[]{}, Type.GEAR, 40F / 100), EnumArmorType.HEAD, new String[]{"Get another try, where others do not!"});
				public ItemPassiveGear burning_halo = new ItemPassiveGear(new PassiveBurningHalo("burning_halo", "burningHalo", new Passive[]{}, new Passive[]{}, Type.GEAR, 70F / 100, 4, Element.FIRE), EnumArmorType.HEAD, new String[]{"Let the angels protect you and", "ward off evil with blazing fire!"});
				public ItemPassiveGear electric_punch = new ItemPassiveGear(new PassiveBurningHalo("electric_punch", "electricPunch", new Passive[]{}, new Passive[]{}, Type.GEAR, 70F / 100, 4, Element.ELECTRICITY), EnumArmorType.HEAD, new String[]{"Smite your enemies like powerful a lightning!"});
				public ItemPassiveGear electric_touch = new ItemPassiveGear(new PassiveBurningHalo("electric_touch", "electricTouch", new Passive[]{}, new Passive[]{}, Type.GEAR, 50F / 100, 3, Element.ELECTRICITY), EnumArmorType.HEAD, new String[]{"Give your enemies a dazzling shock!"});
				//evil eye
				public ItemPassiveGear extra_extra = new ItemPassiveGear(new PassiveExtraExtra("extra_extra", "extraExtra", new Passive[]{}, new Passive[]{}, Type.GEAR, true), EnumArmorType.HEAD, new String[]{"Breaking news! A new piece of gear,", "from the amazing Fink m.f.g.!", "Stay up to date with Extra! Extra!"});
				//gear head
				public ItemPassiveGear hill_runners_hat = new ItemPassiveGear(new PassiveHillRunnersHat("hill_runners_hat", "hillRunnersHat", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 + 50F / 100, 5), EnumArmorType.HEAD, new String[]{"Get far away once you are in the heat."});
				//quick handed
				//rising bloodlust
				public ItemPassiveGear sheltered_life = new ItemPassiveGear(new PassiveShelteredLife("sheltered_life", "shelteredLife", new Passive[]{}, new Passive[]{}, Type.GEAR, 3), EnumArmorType.HEAD, new String[]{"Stay safe with the all new Sheltered Life gear!"});
				//storm
				public ItemPassiveGear surprise_element = new ItemPassiveGear(new PassiveSurpriseElement("surprise_element", "surpriseElement", new Passive[]{}, new Passive[]{}, Type.GEAR, 50F / 100), EnumArmorType.HEAD, new String[]{"Not even a fortune teller will", "forsee what magic it is."});
				//throttle control
				public ItemPassiveGear ticket_puncher = new ItemPassiveGear(new PassiveTicketPuncher("ticket_puncher", "ticketPuncher", new Passive[]{}, new Passive[]{}, Type.GEAR, 2, 3, 20F / 100), EnumArmorType.HEAD, new String[]{"With Ticket Puncher, you", "will surely pack a punch!"});
			}
			
			public Shirts shirt = new Shirts();
			
			public class Shirts
			{
				//ammo advantage
				public ItemPassiveGear better_mousetrap = new ItemPassiveGear(new PassiveEveSaver("better_mousetrap", "betterMousetrap", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 - 20F / 100), EnumArmorType.BODY, new String[]{"Your powers can now be better than ever!"});
				public ItemPassiveGear blood_to_salts = new ItemPassiveGear(new PassiveBloodToSalts("blood_to_salts", "bloodToSalts", new Passive[]{}, new Passive[]{}, Type.GEAR, 20F / 100, 40F / 100), EnumArmorType.BODY, new String[]{"Get your valuable salts back!"});
				//bullet boon
				public ItemPassiveGear coat_of_harms = new ItemPassiveGear(new PassiveCoatOfHarms("coat_of_harms", "coatOfHarms", new Passive[]{}, new Passive[]{}, Type.GEAR, 30F / 100), EnumArmorType.BODY, new String[]{"Make sure your enemies", "will feel the pain!"});
				public ItemPassiveGear drop_cloth = new ItemPassiveGear(new PassiveDropCloth("drop_cloth", "dropCloth", new Passive[]{}, new Passive[]{}, Type.GEAR, 1.5F, 5), EnumArmorType.BODY, new String[]{"Keep on going once you drop!"});
				public ItemPassiveGear executioner = new ItemPassiveGear(new PassiveExecutioner("executioner", "executioner", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 + 25F / 100, 60F / 100), EnumArmorType.BODY, new String[]{"A quick death for your enemy,", "guaranteed."});
				//magic bullet
				//explosive vest
				public ItemPassiveGear pyromaniac = new ItemPassiveGear(new PassiveStaticDischarge("pyromaniac", "pyromaniac", new Passive[]{}, new Passive[]{}, Type.GEAR, Element.FIRE, 3, 50F / 100), EnumArmorType.BODY, new String[]{"Play with fire!"});
				public ItemPassiveGear roar_to_life = new ItemPassiveGear(new PassiveRoarToLife("roar_to_life", "roarToLife", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 + 20F / 100, 1 + 40F / 100, 5), EnumArmorType.BODY, new String[]{"Get out of that sticky mess", "with a majestic roar!"});
				public ItemPassiveGear scavengers_vest = new ItemPassiveGear(new PassiveScavengersVest("scavengers_vest", "scavengersVest", new Passive[]{}, new Passive[]{}, Type.GEAR, 40F / 100), EnumArmorType.BODY, new String[]{"With the Scavenger's Vest gear, the", "firepower lies just around the corner!"});
				public ItemPassiveGear shock_jacket = new ItemPassiveGear(new PassiveStaticDischarge("shock_jacket", "shockJacket", new Passive[]{}, new Passive[]{}, Type.GEAR, Element.ELECTRICITY, 2, 50F / 100), EnumArmorType.BODY, new String[]{"If something hits you,", "it sure won't do it again!"});
				//sky-line accuracy
				public ItemPassiveGear sugar_rush = new ItemPassiveGear(new PassiveSugarRush("sugar_rush", "sugarRush", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 + 50F / 100, 3), EnumArmorType.BODY, new String[]{"Do you have a sweet tooth?", "Make sure you get the maximum", "sweet enjoyment with Sugar Rush!"});
				public ItemPassiveGear winter_shield = new ItemPassiveGear(new PassiveWinterShield("winter_shield", "winterShield", new Passive[]{}, new Passive[]{}, Type.GEAR, 5), EnumArmorType.BODY, new String[]{"Hazards when using the Sky-Lines?", "Worry no more! Put on your winter shield", "for the full protection!"});
			}
			
			public Pants pants = new Pants();
			
			public class Pants
			{
				public ItemPassiveGear angry_stompers = new ItemPassiveGear(new PassiveAngryStompers("angry_stompers", "angryStompers", new Passive[]{}, new Passive[]{}, Type.GEAR, 200F / 100, 25F / 100), EnumArmorType.LEGS, new String[]{"Hit hard when you need to."});
				public ItemPassiveGear brittle_skinned = new ItemPassiveGear(new PassiveBrittleSkinned("brittle_skinned", "brittleSkinned", new Passive[]{}, new Passive[]{}, Type.GEAR, 200F / 100, 5), EnumArmorType.LEGS, new String[]{"Expose your foes' weaknesses!"});
				public ItemPassiveGear bull_rush = new ItemPassiveGear(new PassiveBullRush("bull_rush", "bullRush", new Passive[]{}, new Passive[]{}, Type.GEAR, 200F / 100), EnumArmorType.LEGS, new String[]{"Hit them with the force of a charging bull!"});
				//deadly lungers
				public ItemPassiveGear death_benefit = new ItemPassiveGear(new PassiveDeathBenefit("death_benefit", "deathBenefit", new Passive[]{}, new Passive[]{}, Type.GEAR, 2), EnumArmorType.LEGS, new String[]{"Have Death on your side!"});
				public ItemPassiveGear filthy_leech = new ItemPassiveGear(new PassiveFilthyLeech("filthy_leech", "filthyLeech", new Passive[]{}, new Passive[]{}, Type.GEAR, 10F / 100), EnumArmorType.LEGS, new String[]{"Get your pep back with this flithy trick!"});
				public ItemPassiveGear fire_bird = new ItemPassiveGear(new PassiveFireBird("fire_bird", "fireBird", new Passive[]{}, new Passive[]{}, Type.GEAR, 3, 4), EnumArmorType.LEGS, new String[]{"Give your foes a warm welcome from above!"});
				//ghost posse
				//ghost soldier
				//head master
				//health for salts
				public ItemPassiveGear last_man_standing = new ItemPassiveGear(new PassiveLastManStanding("last_man_standing", "lastManStanding", new Passive[]{}, new Passive[]{}, Type.GEAR, 6, 20F / 100), EnumArmorType.LEGS, new String[]{"Stay alive when it really matters!"});
				//quick draw
				public ItemPassiveGear sky_line_reloader = new ItemPassiveGear(new PassiveSkyLineReloader("skyline_reloader", "skyLineReloader", new Passive[]{}, new Passive[]{}, Type.GEAR), EnumArmorType.LEGS, new String[]{"Let the skylines do the", "boring work for you!"});
				//spectral sidekick
				//urgent care
			}
			
			public Boots boots = new Boots();
			
			public class Boots
			{
				//betrayer
				//death from above
				//eagle strike
				//fit as a fiddle
				//fleet feet
				//handyman nemesis
				public ItemPassiveGear high_and_mighty = new ItemPassiveGear(new PassiveHighAndMighty("high_and_mighty", "highAndMighty", new Passive[]{}, new Passive[]{}, Type.GEAR, 5, 1 + 25F / 100, 40F / 100), EnumArmorType.FEET, new String[]{"With this piece of gear you'll", "be way better than all those loosers!"});
				public ItemPassiveGear kill_to_live = new ItemPassiveGear(new PassiveKillToLive("kill_to_live", "killToLive", new Passive[]{}, new Passive[]{}, Type.GEAR, 2, 65F / 100), EnumArmorType.FEET, new String[]{"Thrive in your opponent's misery."});
				public ItemPassiveGear newtons_law = new ItemPassiveGear(new PassiveNewtonsLaw("newtons_law", "newtonsLaw", new Passive[]{}, new Passive[]{}, Type.GEAR, 4), EnumArmorType.FEET, new String[]{"F = ma"});
				//nor'easter
				public ItemPassiveGear overkill = new ItemPassiveGear(new PassiveOverkill("overkill", "overkill", new Passive[]{}, new Passive[]{}, Type.GEAR, 9, 4), EnumArmorType.FEET, new String[]{"There's no kill like Overkill!", "Deliver death blows that are simply stunning."});
				public ItemPassiveGear tunnel_vision = new ItemPassiveGear(new PassiveTunnelVision("tunnel_vision", "tunnelVision", new Passive[]{}, new Passive[]{}, Type.GEAR, 1 + 25F / 100, 1 - 25F / 100), EnumArmorType.FEET, new String[]{"With Tunnel Vision, you'll hit just the right spot!", "Aim like a hero! - Try it today!"});
				public ItemPassiveGear vampires_embrace = new ItemPassiveGear(new PassiveKillToLive("vampires_embrace", "vampiresEmbrace", new Passive[]{}, new Passive[]{}, Type.GEAR, 3, 1), EnumArmorType.FEET, new String[]{"Drain power from your foes."});
			}
		}
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
	
	public Weapons weapons = new Weapons();
	
	public class Weapons
	{
		public Melee melee = new Melee();
		
		public class Melee
		{
			public ItemWeaponWrench WeaponWrench = M.registerItem("wrench", (ItemWeaponWrench)new ItemWeaponWrench(3.0F, 0, RefMod.MODID + ":" + "item.weapon.wrench.pickup").setUnlocalizedName("weaponWrench"), false, new String[]{});
			public ItemWeaponMelee WeaponPipe = M.registerItem("pipe", (ItemWeaponMelee)new ItemWeaponMelee(3.0F, 5, RefMod.MODID + ":" + "item.weapon.wrench.pickup").setUnlocalizedName("weaponPipe"), false, new String[]{});
			
			public ItemWeaponSkyHook WeaponSkyHook = M.registerItem("sky_hook", new ItemWeaponSkyHook(5, true, RefMod.MODID + ":" + "item.weapon.pistol.cock"), false, new String[]{});
			public ItemWeaponSkyHook WeaponAirGrabber = M.registerItem("air_grabber", (ItemWeaponSkyHook)new ItemWeaponSkyHook(5, false, RefMod.MODID + ":" + "item.weapon.pistol.cock").setUnlocalizedName("weaponAirGrabber"), false, new String[]{});
		}
		
		public Crossbow crossbow = new Crossbow();
		
		public class Crossbow
		{
			public ItemWeaponCrossbow WeaponCrossbow = M.registerItem((ItemWeaponCrossbow)new ItemWeaponCrossbow("crossbow", 5, 21, 0.0F, 4.5F, 14, 0.52F, "Damage", "Bolt", new String[]{"IronTip", "Incendiary", "Trap", "Tranquilizer", "Noisemaker", "Gas"}), false, new String[]{});
			
			public Ammo ammo = new Ammo();
			
			public class Ammo
			{
				public ItemAmmo steel_tip_bolt = M.registerItem("ammo_crossbow_bolt_steel_tip", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltSteelTip").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo incendiary_bolt = M.registerItem("ammo_crossbow_bolt_incendiary", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltIncendiary").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo trap_bolt = M.registerItem("ammo_crossbow_bolt_trap", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltTrap").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo tranquilizer_bolt = M.registerItem("ammo_crossbow_bolt_tranquilizer", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltTranquilizer").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo noisemaker_bolt = M.registerItem("ammo_crossbow_bolt_noisemaker", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltNoisemaker").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo gas_bolt = M.registerItem("ammo_crossbow_bolt_gas", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponCrossbowBoltGas").setCreativeTab(M.tabs.weapons), false, new String[]{});
			}
		}
		
		public Pistol pistol = new Pistol();
		
		public class Pistol
		{
			public ItemWeaponPistol WeaponPistolRapture = M.registerItem((ItemWeaponPistol)new ItemWeaponPistol("pistol_rapture", 6, 24, 8, 1F, 16.6F, 15, 0.39F, "Damage", "Clip", new String[]{"Standard", "ArmorPiercing", "Antipersonnel"}), false, new String[]{});
			
			public Ammo ammo = new Ammo();
			
			public class Ammo
			{
				public ItemAmmo standard_round = M.registerItem("ammo_pistol_round_standard", (ItemAmmo)new ItemAmmo(6).setUnlocalizedName("weaponPistolAmmoStandard").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo armor_piercing_round = M.registerItem("ammo_pistol_round_armor_piercing", (ItemAmmo)new ItemAmmo(6).setUnlocalizedName("weaponPistolAmmoArmorPiercing").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo antipersonnel_round = M.registerItem("ammo_pistol_round_antipersonnel", (ItemAmmo)new ItemAmmo(6).setUnlocalizedName("weaponPistolAmmoAntipersonnel").setCreativeTab(M.tabs.weapons), false, new String[]{});
			}
		}
		
		public Shotgun shotgun = new Shotgun();
		
		public class Shotgun
		{
			public ItemWeaponShotgun WeaponShotgunRapture = M.registerItem("shotgun_rapture", (ItemWeaponShotgun)new ItemWeaponShotgun(4, 24, 12, 17.5F, 22.5F, 12, 0.1F).setUnlocalizedName("weaponShotgunRapture"), false, new String[]{});
			
			public Ammo ammo = new Ammo();
			
			public class Ammo
			{
				public ItemAmmo standard_buck = M.registerItem("ammo_shotgun_shell_00", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponShotgunAmmo00").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo electric_buck = M.registerItem("ammo_shotgun_shell_electric", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponShotgunAmmoElectric").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo exploding_buck = M.registerItem("ammo_shotgun_shell_exploding", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponShotgunAmmoExploding").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo solid_slug = M.registerItem("ammo_shotgun_shell_solid_slug", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponShotgunAmmoSolidSlug").setCreativeTab(M.tabs.weapons), false, new String[]{});
			}
		}
		
		public TommyGun tommy_gun = new TommyGun();
		
		public class TommyGun
		{
			public ItemWeaponTommyGun WeaponMachineGunRapture = M.registerItem((ItemWeaponTommyGun)new ItemWeaponTommyGun("tommy_gun", 30, 3, 2.6F, 7.6F, 3.3F, 22, 0.31F, "Recoil Reduction", "Damage", new String[]{"Standard", "Antipersonnel", "ArmorPiercing"}), false, new String[]{});
			
			public Ammo ammo = new Ammo();
			
			public class Ammo
			{
				public ItemAmmo standard_round = M.registerItem("ammo_auto_round_standard", (ItemAmmo)new ItemAmmo(30).setUnlocalizedName("weaponAutoAmmoStandard").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo antipersonnel_round = M.registerItem("ammo_auto_round_antipersonnel", (ItemAmmo)new ItemAmmo(30).setUnlocalizedName("weaponAutoAmmoAntipersonnel").setCreativeTab(M.tabs.weapons), false, new String[]{});
				public ItemAmmo armor_piercing_round = M.registerItem("ammo_auto_round_armor_piercing", (ItemAmmo)new ItemAmmo(30).setUnlocalizedName("weaponAutoAmmoArmorPiercing").setCreativeTab(M.tabs.weapons), false, new String[]{});
			}
		}
		
		public GrenadeLauncher grenade_launcher = new GrenadeLauncher();
		
		public class GrenadeLauncher
		{
			public ItemWeaponGrenadeLauncher WeaponGrenadeLauncher = M.registerItem((ItemWeaponGrenadeLauncher)new ItemWeaponGrenadeLauncher("grenade_launcher", 0, 6, 0), false, new String[]{});
			
			public Ammo ammo = new Ammo();
			
			public class Ammo
			{
				public ItemAmmo frag_grenade = M.registerItem("ammo_grenade_frag", (ItemAmmo)new ItemAmmo(0).setUnlocalizedName("weaponGrenadeLauncherAmmoFrag").setCreativeTab(M.tabs.weapons), false, new String[]{});
			}
		}
		
		public Throwable throwable = new Throwable();
		
		public class Throwable
		{
			public ItemTennisBall tennis_ball = M.registerItem("tennis_ball", (ItemTennisBall)new ItemTennisBall(0.5F), false, new String[]{});
		}
	}
	
	public Utility utility = new Utility();
	
	public class Utility
	{
		public ItemTank tank = M.registerItem("tank", (ItemTank)new ItemTank(1000).setUnlocalizedName("tank"), false, new String[]{});
	}
	
	public Armor armor = new Armor();
	
	public class Armor
	{
		public Materials materials = new Materials();
		
		public class Materials
		{
			public ArmorMaterial DIVING_SUIT_METAL = EnumHelper.addArmorMaterial("DIVING_SUIT_METAL", "", 16, new int[]{5, 6, 5, 3}, 0);
			public ArmorMaterial DIVING_SUIT_CLOTH = EnumHelper.addArmorMaterial("DIVING_SUIT_CLOTH", "", 7, new int[]{1, 2, 1, 1}, 0);
		}
		
		public class ArmorSet<I extends ItemArmor>
		{
			public I head;
			public I body;
			public I legs;
			public I feet;
			
			public ArmorSet(I head, I body, I legs, I feet)
			{
				this.head = head;
			}
			
			public I get(EnumArmorType armorType)
			{
				switch(armorType)
				{
				case HEAD:
					return this.head;
				case BODY:
					return this.body;
				case LEGS:
					return this.legs;
				case FEET:
					return this.feet;
				}
				return null;
			}
		}
		
		public ArmorSet<ItemArmorDivingSuit> diving_suit = new ArmorSet((ItemArmorDivingSuit)new ItemArmorDivingSuit(M.items.armor.materials.DIVING_SUIT_METAL, EnumArmorType.HEAD, "diving_suit_helmet", "diving_suit_3", true).setUnlocalizedName("divingSuitHelmet"), (ItemArmorDivingSuitTank)new ItemArmorDivingSuitTank(M.items.armor.materials.DIVING_SUIT_CLOTH, EnumArmorType.BODY, "diving_suit_upper_body", false, M.items.armor.diving_suit.head, M.items.armor.diving_suit.legs, M.items.armor.diving_suit.feet).setUnlocalizedName("divingSuitUpperBody"), (ItemArmorDivingSuit)new ItemArmorDivingSuit(M.items.armor.materials.DIVING_SUIT_CLOTH, EnumArmorType.LEGS, "diving_suit_lower_body", false).setUnlocalizedName("divingSuitLowerBody"), (ItemArmorDivingSuit)new ItemArmorDivingSuit(M.items.armor.materials.DIVING_SUIT_METAL, EnumArmorType.FEET, "diving_suit_boots", true).setUnlocalizedName("divingSuitBoots"));
	}
}
