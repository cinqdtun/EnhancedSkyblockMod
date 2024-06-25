package com.cinqdt1.Mod.commands;

import com.cinqdt1.Mod.features.XpRunTracker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class NotCountRun extends CommandBase {
	@Override
	public String getCommandName() {
		return "notcountrun";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		new Thread(() -> {
			XpRunTracker.countRun = false;
		}).start();
	}
}
