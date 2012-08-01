package me.BlockCat.RankUp;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExe implements CommandExecutor {

	HashMap<Player,String> promote = new HashMap<Player, String>();
	HashMap<String,Double> cost1 = new HashMap<String, Double>();
	private Checker checker = new Checker();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		Player player = (Player)sender;

		if (args.length == 0) {
			List<String> ladders = checker.getAvailableLadders(player);

			player.sendMessage(ChatColor.AQUA + " You can Upgrade to:");
			player.sendMessage(ChatColor.GOLD + "~~~~~~~~~~~~~~~~");

			for (String x : ladders) {
				player.sendMessage(ChatColor.RED + x);
			}

			player.sendMessage(ChatColor.GOLD + "~~~~~~~~~~~~~~~~");
			player.sendMessage(ChatColor.AQUA + "Do /rankup <choice>, to choose.");
		}

		if (args.length == 1) {
			String lad = args[0].toLowerCase();

			if (args[0].equalsIgnoreCase("accept")) {
				if (promote.containsKey(player)) {
					String n = promote.get(player);
					promote.remove(player);
					//Configuration.getConfig(lad).getCost(n);

					double cost = cost1.get(n);

					checker.payAndRank(player, n, cost);
				} else {
					player.sendMessage(ChatColor.DARK_RED + "You didn't choose a class yet.");
				}
				return true;
			}

			String next = checker.nextGroup(player, lad);
			if (next.equalsIgnoreCase("max")) {
				player.sendMessage(ChatColor.DARK_RED + "You already are maximum promoted.");
				return true;
			}
			if (next.equalsIgnoreCase("Nay1&")) {
				player.sendMessage(ChatColor.DARK_RED + "That does not exist.");
				return true;
			}

			if (checker.ladderAllowed(player, lad)) {			
				player.sendMessage(ChatColor.GOLD + "The rankings");


				for (String x : Configuration.getConfig(lad).getGroups()) {
					double c = Configuration.getConfig(lad).getCost(x);
					player.sendMessage(ChatColor.GREEN + x + " : " + ChatColor.BLUE + c);
					
					if (!cost1.containsKey(x))
					cost1.put(x, c);
				}
				player.sendMessage(ChatColor.GOLD + "To be promoted type: /rankup accept");
				promote.put(player, next);
			}
			else {
				player.sendMessage(ChatColor.DARK_RED + "You are not allowed to take this class anymore");
			}
		}

		return true;
	}

}
