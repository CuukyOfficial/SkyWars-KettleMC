package de.cuuky.skywars.chest.loot;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.cuuky.minecraftutils.item.ItemBuilder;
import de.cuuky.minecraftutils.utils.JavaUtils;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public enum SkyWarsLootType {

	BASIC(new ItemStack[] {
			new ItemBuilder().itemstack(new ItemStack(Material.STONE)).amount(JavaUtils.randomInt(4, 64)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.BRICK)).amount(JavaUtils.randomInt(4, 64)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.WOOD)).amount(JavaUtils.randomInt(4, 64)).build(), // <-- OAK WOOD PLANKS
			new ItemBuilder().itemstack(new ItemStack(Material.GLASS)).amount(JavaUtils.randomInt(4, 64)).build(),

			new ItemBuilder().itemstack(new ItemStack(Material.WEB)).amount(JavaUtils.randomInt(2, 10)).build(),


			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Material.COOKED_BEEF)).amount(JavaUtils.randomInt(4, 12)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.PUMPKIN_PIE)).amount(JavaUtils.randomInt(4, 12)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.RAW_BEEF)).amount(JavaUtils.randomInt(4, 16)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.GOLDEN_APPLE)).amount(1).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Material.POTION, 1, (short) 16389)).amount(JavaUtils.randomInt(1,3)).build(), // <-- ID 16389
	}, false),
	
	COMMON(new ItemStack[] {
			// ARMOR (CHAINMAIL UND GOLD RANDOM PROT 1-2)
			
			new ItemBuilder().itemstack(new ItemStack(Material.CHAINMAIL_BOOTS)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.CHAINMAIL_CHESTPLATE)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.CHAINMAIL_HELMET)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.CHAINMAIL_LEGGINGS)).amount(1).build(),

			new ItemBuilder().itemstack(new ItemStack(Material.GOLD_BOOTS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.GOLD_CHESTPLATE)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.GOLD_HELMET)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.GOLD_LEGGINGS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),

			new ItemBuilder().itemstack(new ItemStack(Material.IRON_BOOTS)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_CHESTPLATE)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_HELMET)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_LEGGINGS)).amount(1).build(),
			
			// SWORDS (WOODEN_SWORD --> RANDOM SHARPNESS 2, STONE_SWORD --> RANDOM SHARPNESS 1)

			new ItemBuilder().itemstack(new ItemStack(Material.WOOD_SWORD)).amount(1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.STONE_SWORD)).amount(1).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Material.IRON_INGOT)).amount(JavaUtils.randomInt(4, 10)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND)).amount(JavaUtils.randomInt(1, 4)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.FLINT)).amount(JavaUtils.randomInt(1, 4)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.EXP_BOTTLE)).amount(JavaUtils.randomInt(1, 10)).build(),
	}, true),
	
	UNCOMMON(new ItemStack[] {
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_INGOT)).amount(JavaUtils.randomInt(8, 14)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND)).amount(JavaUtils.randomInt(2, 6)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.FLINT)).amount(JavaUtils.randomInt(1, 4)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.EXP_BOTTLE)).amount(JavaUtils.randomInt(1, 10)).build(),

			// ARMOR (PROTECTION 1-2)

			new ItemBuilder().itemstack(new ItemStack(Material.IRON_BOOTS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_CHESTPLATE)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_HELMET)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.IRON_LEGGINGS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),

			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Material.GOLDEN_APPLE)).amount(JavaUtils.randomInt(1, 2)).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Material.ENDER_PEARL)).amount(1).build(),

			// SWORDS 

			new ItemBuilder().itemstack(new ItemStack(Material.IRON_SWORD)).amount(1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_SWORD)).amount(1).build(),
	}, true),
	
	RARE(new ItemStack[] {
			// ARMOR (PROTECTION 1-2)

			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_BOOTS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_CHESTPLATE)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_HELMET)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),
			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_LEGGINGS)).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1,2)).build(),

			// SWORDS 

			new ItemBuilder().itemstack(new ItemStack(Material.DIAMOND_SWORD)).amount(1).addEnchantment(Enchantment.DAMAGE_ALL, JavaUtils.randomInt(1,2)).build(),

			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Material.GOLDEN_APPLE)).amount(JavaUtils.randomInt(1, 3)).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Material.ENDER_PEARL)).amount(JavaUtils.randomInt(1, 2)).build(),
	}, true);

	private boolean votable;
	private ItemStack[] loot;
	private ArrayList<SkyWarsPlayer> votes;

	private SkyWarsLootType(ItemStack[] loot, boolean votable) {
		this.loot = loot;
		this.votes = new ArrayList<>();
		this.votable = votable;
	}
	
	public boolean isVotable() {
		return votable;
	}

	public ItemStack getRandomItem() {
		return loot[JavaUtils.randomInt(0, loot.length - 1)];
	}

	public void addVote(SkyWarsPlayer player) {
		votes.add(player);
	}

	public void removeVote(SkyWarsPlayer player) {
		votes.remove(player);
	}
	
	public static void removeAllVotes(SkyWarsPlayer player) {
		for(SkyWarsLootType type : values())
			type.removeVote(player);
	}

	public ArrayList<SkyWarsPlayer> getVotes() {
		return votes;
	}
}