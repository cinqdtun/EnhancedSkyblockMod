package com.cinqdt1.Mod.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DungeonEvent extends Event{
	
	public final int floor;
	
	public DungeonEvent(int floor)
	{
		this.floor = floor;
	}
	/**
	  * Fired when the player entered in dungeon
	  * 
	  * Cancel the event to prevent it from processing.
	  */
	@Cancelable
   	public static class Entered extends DungeonEvent
   	{
       public Entered(int floor)
       {
           super(floor);
       }
   	}
	/**
	  * Fired when the player leave the dungeon
	  *
	  * Cancel the event to prevent it from processing.
	  */
	@Cancelable
	public static class Leave extends DungeonEvent
    {
      public Leave(int floor)
      {
   	   	super(floor);
      }
    }
}
