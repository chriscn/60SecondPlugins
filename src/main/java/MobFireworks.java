import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class MobFireworks extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Creature) {
            Player player = (Player) event.getDamager();
            final Entity e = event.getEntity();
            if(player.getInventory().getItemInMainHand().getType().equals(Material.SULPHUR)) {
                event.getEntity().setVelocity(new Vector(0, 1.35, 0));
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    public void run() {
                        Location l = e.getLocation();
                        l.getWorld().createExplosion(l, 1.0f);
                        e.remove();;
                    }
                }, 35);
            }
        }
    }
}