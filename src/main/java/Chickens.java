import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Chickens extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(event.getPlayer().getInventory().getItemInMainHand() != null && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) {
                Chicken closest = getNearestChicken(event.getPlayer());
                if(closest != null) {
                    event.getPlayer().setCompassTarget(closest.getLocation());
                    event.getPlayer().sendMessage(ChatColor.GREEN+"Go kill dat chicken!");
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED+"No chicken nearby :(");
                }
            }
        }
    }

    public Chicken getNearestChicken(Player player) {
        Chicken closest = null;
        for(Entity e : player.getWorld().getEntities()) {
            if(e instanceof Chicken) {
                if(closest == null || e.getLocation().distance(player.getLocation()) < closest.getLocation().distance(player.getLocation())) {
                    closest = (Chicken) e;
                }
            }
        }
        return closest;
    }


}