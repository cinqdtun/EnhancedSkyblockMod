package com.cinqdt1.Mod.commands;

import com.cinqdt1.Mod.cinqdt1Mod;
import gg.essential.api.EssentialAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class MainCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "cinqdtunmod";
	}
	
	@Override
	public List<String> getCommandAliases() {
        return Collections.singletonList("cinqdtun");
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
			EssentialAPI.getGuiUtil().openScreen(cinqdt1Mod.instance.config.gui());
		}).start();
	}

}
