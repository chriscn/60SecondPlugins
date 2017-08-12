import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Random;

public class ChickenBlock extends JavaPlugin implements Listener {

    Random random = new Random();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.STONE) {
            for(int i=0; i<10; i++) {
                Chicken chicken = event.getBlock().getWorld().spawn(event.getBlock().getLocation(), Chicken.class);
                chicken.setVelocity(new Vector(random.nextDouble()-0.5, random.nextDouble()/2.0, random.nextDouble()-0.5));
            }
        }
    }
}