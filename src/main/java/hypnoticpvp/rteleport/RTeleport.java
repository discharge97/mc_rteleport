package hypnoticpvp.rteleport;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public final class RTeleport extends JavaPlugin {

    private static Economy econ = null;

    @Override
    public void onEnable() {
        setupEconomy();
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        econ.bankWithdraw("aa", getConfig().getDouble("price"));


    }

//    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;

            if (command.getName().toLowerCase().trim().equals("rtp") && canUseRTeleport(player.getWorld().getName())) {
                if(econ.bankBalance(player.getName()).balance >= getConfig().getDouble("price")){
                    if (econ.bankWithdraw(player.getName(), getConfig().getDouble("price")).type == EconomyResponse.ResponseType.SUCCESS){
                        int start_x = getConfig().getInt("start_x");
                        int start_z = getConfig().getInt("start_z");
                        int end_z = getConfig().getInt("end_z");
                        int end_x = getConfig().getInt("end_x");

                        Location loc = player.getLocation();

                    }

                }
            }
        }
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
        return (econ != null);
    }

    public Location getRandomLocation(Location loc, int start_x, int start_z, int end_x, int end_z) {
        Random random = new Random();
        random.doubles(start_x, end_x).findFirst().getAsDouble();
    }

    public boolean canUseRTeleport(String world){
        List<String> worlds = getConfig().getStringList("worlds");
        return worlds.contains(world);
    }
}
