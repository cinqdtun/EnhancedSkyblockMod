package cinqdt1.Mod.commands;

import java.util.Collections;
import java.util.List;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.config.ModConfiguration;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

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
