package cinqdt1.Mod.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class FishingEvent extends PlayerEvent {
	public FishingEvent(EntityPlayer player)
    {
		super(player);
    }
	 /**
	  * Fired when the player start to fishing
	  * 
	  * Cancel the event to prevent it from processing.
	  */
	@Cancelable
    public static class Start extends FishingEvent
    {
        public Start(EntityPlayer player)
        {
            super(player);
        }
    }
	/**
	  * Fired when the player stop to fishing
	  * 
	  * Cancel the event to prevent it from processing.
	  */
	@Cancelable
   public static class Stop extends FishingEvent
   {
       public Stop(EntityPlayer player)
       {
    	   super(player);
       }
   }
}
