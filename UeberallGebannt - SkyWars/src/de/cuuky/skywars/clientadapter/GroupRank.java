package de.cuuky.skywars.clientadapter;

import org.bukkit.entity.Player;

public enum GroupRank {

	ADMIN("prefix.Admin", "&4Admin &8| &7", "", 1),
	SR_MODERATOR("prefix.SrModerator", "&cSrMod &8| &7", "", 2),
	SR_DEVELOPER("prefix.SrDeveloper", "&3SrDev &8| &7", "", 5),
	SR_BUILDER("prefix.SrBuilder", "&eSrBuild &7| &e", "", 3),
	MODERATOR("prefix.Moderator", "&cMod &8| &7", "", 4),
	DEVELOPER("prefix.Developer", "&3Dev &8| &7", "", 5),
	BUILDER("prefix.Builder", "&eBuild &8| &7", "", 6),
	SUPPORTER("prefix.Supporter", "&9Supp &8| &7", "", 7),
	DESIGNER("prefix.Designer", "&bDesigner &8| &7", "", 8),
	CONTENT("prefix.Content", "&dContent &8| &7", "", 9),
	PRAKTIKANT("prefix.Praktikant", "&3Praktikant &8| &7", "", 10),
	JR_DEVELOPER("prefix.JrDeveloper", "&3JrDev &8| &7", "", 11),
	JR_BUILDER("prefix.JrBuilder", "&eJrBuild &8| &7", "", 12),
	JR_SUPPORTER("prefix.JrSupporter", "&9JrSupp &8| &7", "", 13),
	YOUTUBER("prefix.YouTuber", "&5YT &8| &7", "", 14),
	JR_YOUTUBER("prefix.JrYouTuber", "&5JrYT &8| &7", "", 15),
	STREAMER("prefix.Streamer", "&5ST &8| &7", "", 16),
	JR_STREAMER("prefix.JrStreamer", "&5JrST &8| &7", "", 17),
	PARTNER("prefix.Partner", "&6Partner &8| &7", "", 18),
	FREUND("prefix.Freund", "&aFreund &8| &7", "", 19),
	RUBY("prefix.Ruby", "&cRuby &8| &c", "", 20),
	EMERALD("prefix.Emerald", "&2Emerald &8| &7", "", 21),
	DIAMOND("prefix.Diamond", "&bDiamond &8| &7", "", 22),
	SPIELER("prefix.Spieler", "&7Spieler &8| &7", "", 23),
	NONE(null, "&7", "", 24);

	private String permission, prefix, suffix;
	private int sortPriority;

	private GroupRank(String permission, String prefix, String suffix, int sortPriority) {
		this.permission = permission;
		this.prefix = prefix.replace("&", "§");
		this.suffix = suffix.replace("&", "§");
		this.sortPriority = sortPriority;
	}

	public String getPermission() {
		return permission;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public int getSortPriority() {
		return sortPriority;
	}

	public static GroupRank getGroupRank(Player player) {
		GroupRank current = null;
		for (GroupRank rank : values()) {
			if (rank.getPermission() != null && !player.hasPermission(rank.getPermission()))
				continue;

			if (current == null)
				current = rank;
			else if (current.getSortPriority() > rank.getSortPriority())
				current = rank;
		}

		return current;
	}
}
