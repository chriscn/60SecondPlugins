import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
 
import java.util.ArrayList;
 
public class Launchpad extends JavaPlugin implements Listener {
 
    // A list of players will be added here upon walking over the launchpad.
    private ArrayList<String> jumpers = new ArrayList<String>();
 
    // Method that enables the plugin. It will register the events.
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }
 
    // Handlers the player move event.
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        /*
            Gets the position at which a block is facing (self means it can be facing from any direction) and
            makes sure that the block is a pressure plate.
        */
        if (e.getTo().getBlock().getRelative(BlockFace.SELF).getType() == Material.STONE_PLATE) {
 
            /* This wasn't in the tutorial but I figured people would ask in the comments
               wondering how to add sounds + effects to the launchpad. So, here you go! :P
            */
            e.getTo().getBlock().getWorld().playSound(e.getPlayer().getLocation(), Sound.WITHER_DEATH, 1, 1);
            e.getTo().getBlock().getWorld().playEffect(e.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
 
            // Speeds up a player's velocity (makes them move somewhere faster).
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
 
            /* While the player's velocity is sped up, it keeps the same X and Y position at which they were moving to.
               The "1.0D" means that the player will "fly" in the air for the period of time at which the velocity is being
               increased.
            */
            e.getPlayer().setVelocity(new Vector(e.getPlayer().getLocation().getX(), 1.0D, e.getPlayer().getLocation().getZ()));
 
            // Adds a player to the ArrayList.
            jumpers.add(e.getPlayer().toString());
        }
    }
 
    // Handles the entity damage event.
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        // Sanity check: Makes sure the entity is a player.
        if (e.getEntity() instanceof Player) {
 
            // Now that we know that the entity is a player, we can cast Player to an entity.
            Player p = (Player) e.getEntity();
 
            // Gets the damage cause of a player and makes sure they are in the ArrayList.
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && jumpers.contains(p)) {
 
                // Sets the player's damage to nothing once they reach the ground.
                e.setDamage(0.0);
 
                /* After the player reaches the ground, they will be removed from the ArrayList so they can be prone
                   to other sources of damage again.
                */
                jumpers.remove(p);
            }
        }
    }
}
