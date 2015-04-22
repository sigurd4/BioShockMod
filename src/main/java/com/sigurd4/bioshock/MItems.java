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
}

