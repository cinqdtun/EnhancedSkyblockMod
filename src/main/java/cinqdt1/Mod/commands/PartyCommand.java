package cinqdt1.Mod.commands;

import cinqdt1.Mod.gui.PartyFinderGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
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
    public void processCommand(ICommandSender arg0, String[] arg1){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Minecraft.getMinecraft().displayGuiScreen(new PartyFinderGUI());
            } catch (InterruptedException ignored) {}
        });
    }
}
