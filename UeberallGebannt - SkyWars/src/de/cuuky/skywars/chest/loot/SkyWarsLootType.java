package de.cuuky.skywars.chest.loot;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public enum SkyWarsLootType {

	BASIC(new ItemStack[] { new ItemBuilder().itemstack(new ItemStack(Materials.STONE.parseMaterial())).amount(JavaUtils.randomInt(4, 64)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.BRICK.parseMaterial())).amount(JavaUtils.randomInt(4, 64)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.OAK_PLANKS.parseMaterial())).amount(JavaUtils.randomInt(4, 64)).build(), // <-- OAK WOOD PLANKS
			new ItemBuilder().itemstack(new ItemStack(Materials.GLASS.parseMaterial())).amount(JavaUtils.randomInt(4, 64)).build(),

			new ItemBuilder().itemstack(new ItemStack(Materials.COBWEB.parseMaterial())).amount(JavaUtils.randomInt(2, 10)).build(),

			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Materials.COOKED_BEEF.parseMaterial())).amount(JavaUtils.randomInt(4, 12)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.PUMPKIN_PIE.parseMaterial())).amount(JavaUtils.randomInt(4, 12)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.BEEF.parseMaterial())).amount(JavaUtils.randomInt(4, 16)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_APPLE.parseMaterial())).amount(1).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Materials.POTION.parseMaterial(), 1, (short) 16389)).amount(JavaUtils.randomInt(1, 3)).build(), // <-- ID 16389
	}, false),

	COMMON(new ItemStack[] {
			// ARMOR (CHAINMAIL UND GOLD RANDOM PROT 1-2)

			new ItemBuilder().itemstack(new ItemStack(Materials.CHAINMAIL_BOOTS.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.CHAINMAIL_CHESTPLATE.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.CHAINMAIL_HELMET.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.CHAINMAIL_LEGGINGS.parseMaterial())).amount(1).build(),

			new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_BOOTS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_CHESTPLATE.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_HELMET.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_LEGGINGS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(),

			new ItemBuilder().itemstack(new ItemStack(Materials.IRON_BOOTS.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_CHESTPLATE.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_HELMET.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_LEGGINGS.parseMaterial())).amount(1).build(),

			// SWORDS (WOODEN_SWORD --> RANDOM SHARPNESS 2, STONE_SWORD --> RANDOM SHARPNESS 1)

			new ItemBuilder().itemstack(new ItemStack(Materials.WOODEN_SWORD.parseMaterial())).amount(1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.STONE_SWORD.parseMaterial())).amount(1).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Materials.IRON_INGOT.parseMaterial())).amount(JavaUtils.randomInt(4, 10)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND.parseMaterial())).amount(JavaUtils.randomInt(1, 4)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.FLINT.parseMaterial())).amount(JavaUtils.randomInt(1, 4)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.EXPERIENCE_BOTTLE.parseMaterial())).amount(JavaUtils.randomInt(1, 10)).build(), }, true),

	UNCOMMON(new ItemStack[] { new ItemBuilder().itemstack(new ItemStack(Materials.IRON_INGOT.parseMaterial())).amount(JavaUtils.randomInt(8, 14)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND.parseMaterial())).amount(JavaUtils.randomInt(2, 6)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.FLINT.parseMaterial())).amount(JavaUtils.randomInt(1, 4)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.EXPERIENCE_BOTTLE.parseMaterial())).amount(JavaUtils.randomInt(1, 10)).build(),

			// ARMOR (PROTECTION 1-2)

			new ItemBuilder().itemstack(new ItemStack(Materials.IRON_BOOTS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_CHESTPLATE.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_HELMET.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.IRON_LEGGINGS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(),

			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_APPLE.parseMaterial())).amount(JavaUtils.randomInt(1, 2)).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Materials.ENDER_PEARL.parseMaterial())).amount(1).build(),

			// SWORDS

			new ItemBuilder().itemstack(new ItemStack(Materials.IRON_SWORD.parseMaterial())).amount(1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_SWORD.parseMaterial())).amount(1).build(), }, true),

	RARE(new ItemStack[] {
			// ARMOR (PROTECTION 1-2)

			new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_BOOTS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_CHESTPLATE.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_HELMET.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(), new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_LEGGINGS.parseMaterial())).amount(1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, JavaUtils.randomInt(1, 2)).build(),

			// SWORDS

			new ItemBuilder().itemstack(new ItemStack(Materials.DIAMOND_SWORD.parseMaterial())).amount(1).addEnchantment(Enchantment.DAMAGE_ALL, JavaUtils.randomInt(1, 2)).build(),

			// FOOD

			new ItemBuilder().itemstack(new ItemStack(Materials.GOLDEN_APPLE.parseMaterial())).amount(JavaUtils.randomInt(1, 3)).build(),

			// ITEMS

			new ItemBuilder().itemstack(new ItemStack(Materials.ENDER_PEARL.parseMaterial())).amount(JavaUtils.randomInt(1, 2)).build(), }, true);

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
		for (SkyWarsLootType type : values())
			type.removeVote(player);
	}

	public ArrayList<SkyWarsPlayer> getVotes() {
		return votes;
	}
}