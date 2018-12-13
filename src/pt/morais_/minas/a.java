package pt.morais_.minas;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class a extends JavaPlugin implements Listener {
	public static a main;

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§aMorais-Minas ligado");
		saveDefaultConfig();
		reloadConfig();
		if (!setupEconomy()) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		setupPermissions();
		Bukkit.getPluginManager().registerEvents(new b(), this);
		main = this;
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§4Morais-Minas desligado");
	}

	public static a getPlugin() {
		return (a) getPlugin(a.class);
	}

	private Economy econ = null;
	private Permission perms;

	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		this.econ = ((Economy) rsp.getProvider());
		return this.econ != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		this.perms = ((Permission) rsp.getProvider());
		return this.perms != null;
	}

	public Economy getEcononomy() {
		return this.econ;
	}

	public Permission getPermissions() {
		return this.perms;
	}

}
