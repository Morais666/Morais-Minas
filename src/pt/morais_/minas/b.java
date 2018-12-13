package pt.morais_.minas;

import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import pt.morais_.minas.a;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class b implements Listener {

	@EventHandler

	public void sendActionBar(Player p, String text) {
		PacketPlayOutChat packet = new PacketPlayOutChat(
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		FileConfiguration config = a.getPlugin().getConfig();
		Economy eco = a.getPlugin().getEcononomy();
		if ((p.getWorld().getName().equalsIgnoreCase(config.getString("Mina.mundo"))))
			for (String key : a.main.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
				for (String blocks : a.main.getConfig().getStringList("Ranks." + key + ".Blocks")) {
					if (e.getBlock().getType() == Material.getMaterial(blocks)) {
						e.setCancelled(true);
						b.setType(Material.AIR);
						if (e.getPlayer().hasPermission(a.main.getConfig().getString("Ranks." + key + ".permission"))) {
							eco.depositPlayer(e.getPlayer(), a.main.getConfig().getInt("Ranks." + key + ".money"));
							if (a.main.getConfig().getBoolean("Mensagem.chat") == true) {
								sendActionBar(p, a.main.getConfig().getString("Mensagem.minerar")
										.replace("{money}",
												String.valueOf(a.main.getConfig().getInt("Ranks." + key + ".money")))
										.replaceAll("&", "§"));
							} else {
								// nada mesmo
							}
						}	
					}
				}
			}
	}
}
