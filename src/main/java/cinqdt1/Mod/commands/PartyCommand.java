package cinqdt1.Mod.commands;

import cinqdt1.Mod.gui.PartyGUI;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class PartyCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "esmparty";
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
            try {
                Thread.sleep(1000);
                Minecraft.getMinecraft().displayGuiScreen(new PartyGUI());
            } catch (InterruptedException ignored) {}
        });
    }
}
