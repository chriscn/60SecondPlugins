import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class RemoteTNT extends JavaPlugin implements Listener{
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Action a = e.getAction();
        Player p = e.getPlayer();

        if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
            if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.LEVER){
                Block block = p.getTargetBlock((HashSet<Byte>) null, 10);
                if(block.getType() == Material.TNT){
                    block.setType(Material.AIR);
                    block.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class);

                }
            }
        }
    }
}