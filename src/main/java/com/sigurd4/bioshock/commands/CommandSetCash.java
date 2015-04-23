package com.sigurd4.bioshock.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.sigurd4.bioshock.Stuff;
import com.sigurd4.bioshock.item.ItemMoney;

public class CommandSetCash extends CommandBase implements ICommand
{
	@Override
	public String getName()
	{
		return "bioshocksetcash";
	}
	
	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands.bioshocksetcash.usage";
	}
	
	@Override
	public void execute(ICommandSender commandSender, String[] command) throws CommandException
	{
		if(command.length < 3 || command.length > 3)
		{
			throw new WrongUsageException("commands.bioshocksetcash.usage", new Object[0]);
		}
		else
		{
			EntityPlayer player = getPlayer(commandSender, command[0]);
			Item item = getItemByText(commandSender, command[1]);
			int amount = parseInt(command[2]);
			
			if(!(item instanceof ItemMoney))
			{
				notifyOperators(commandSender, this, "commands.bioshocksetcash.tagError", new Object[]{"Item is not a valid currency"});
				return;
			}
			else if(amount < 0)
			{
				notifyOperators(commandSender, this, "commands.bioshocksetcash.tagError", new Object[]{"Amount is too low"});
				return;
			}
			else
			{
				ItemMoney.setCash((ItemMoney)item, player, amount);
				notifyOperators(commandSender, this, "commands.bioshocksetcash.success", new Object[]{"Set ", player, Stuff.Strings.possessive(player.getDisplayNameString()) + " ", new ItemStack(item), " to ", amount});
			}
		}
	}
	
	/**
	 * Adds the strings available in this command to the given list of tab
	 * completion options.
	 */
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.getPlayers()) : args.length == 2 ? func_175762_a(args, Item.itemRegistry.getKeys()) : null;
	}
	
	protected String[] getPlayers()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 0;
	}
}
