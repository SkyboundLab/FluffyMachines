package io.ncbpfluffybear.fluffymachines.items.tools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import io.ncbpfluffybear.fluffymachines.FluffyMachines;
import io.ncbpfluffybear.fluffymachines.utils.FluffyItems;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UpgradedPaxel extends SlimefunItem implements Listener, NotPlaceable {

    public final Set<Material> axeBlocks = Stream.of(
            Tag.LOGS.getValues(),
            Tag.PLANKS.getValues(),
            Tag.WOODEN_STAIRS.getValues(),
            Tag.SIGNS.getValues(),
            Tag.WOODEN_FENCES.getValues(),
            Tag.FENCE_GATES.getValues(),
            Tag.WOODEN_TRAPDOORS.getValues(),
            Tag.WOODEN_PRESSURE_PLATES.getValues(),
            Tag.WOODEN_DOORS.getValues(),
            Tag.WOODEN_SLABS.getValues(),
            Tag.WOODEN_BUTTONS.getValues(),
            Tag.BANNERS.getValues(),
            Tag.LEAVES.getValues(),
            Tag.CAMPFIRES.getValues(),
            new HashSet<>(
                Arrays.asList(
                    Material.CHEST,
                    Material.TRAPPED_CHEST,
                    Material.CRAFTING_TABLE,
                    Material.SMITHING_TABLE,
                    Material.LOOM,
                    Material.CARTOGRAPHY_TABLE,
                    Material.FLETCHING_TABLE,
                    Material.BARREL,
                    Material.JUKEBOX,
                    Material.BOOKSHELF,
                    Material.JACK_O_LANTERN,
                    Material.CARVED_PUMPKIN,
                    Material.PUMPKIN,
                    Material.MELON,
                    Material.COMPOSTER,
                    Material.BEEHIVE,
                    Material.BEE_NEST,
                    Material.NOTE_BLOCK,
                    Material.LADDER,
                    Material.COCOA_BEANS,
                    Material.DAYLIGHT_DETECTOR,
                    Material.MUSHROOM_STEM,
                    Material.BROWN_MUSHROOM_BLOCK,
                    Material.RED_MUSHROOM_BLOCK,
                    Material.BAMBOO,
                    Material.VINE,
                    Material.LECTERN,
                    Material.MANGROVE_ROOTS
                )
            )
    ).flatMap(Set::stream).collect(Collectors.toSet());

    public final Set<Material> hoeBlocks = Stream.of(
        Tag.LEAVES.getValues(),
        new HashSet<>(
            Arrays.asList(
                Material.SPONGE,
                Material.WET_SPONGE,
                Material.SCULK_CATALYST,
                Material.SCULK_SHRIEKER,
                Material.SCULK_SENSOR,
                Material.CALIBRATED_SCULK_SENSOR,
                Material.SCULK_VEIN,
                Material.MOSS_BLOCK,
                Material.DRIED_KELP_BLOCK,
                Material.TARGET,
                Material.HAY_BLOCK,
                Material.SHROOMLIGHT,
                Material.NETHER_WART_BLOCK,
                Material.WARPED_WART_BLOCK,
                Material.WHEAT,
                Material.CARROTS,
                Material.POTATOES,
                Material.BEETROOTS,
                Material.MELON_STEM,
                Material.PUMPKIN_STEM,
                Material.SWEET_BERRY_BUSH,
                Material.COCOA,
                Material.NETHER_WART,
                Material.KELP,
                Material.SUGAR_CANE,
                Material.BAMBOO_SAPLING,
                Material.CACTUS
            )
        )
    ).flatMap(Set::stream).collect(Collectors.toSet());

    public UpgradedPaxel(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        Bukkit.getPluginManager().registerEvents(this, FluffyMachines.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    private void onMine(BlockDamageEvent e) {
        Player p = e.getPlayer();
        SlimefunItem sfItem = SlimefunItem.getByItem(p.getInventory().getItemInMainHand());

        if (sfItem != null && sfItem == FluffyItems.UPGRADED_PAXEL.getItem()) {
            boolean netherite = false;
            Block b = e.getBlock();
            ItemStack item = p.getInventory().getItemInMainHand();

            Material blockType = b.getType();

            if (item.getType() == Material.NETHERITE_PICKAXE || item.getType() == Material.NETHERITE_AXE || item.getType() == Material.NETHERITE_SHOVEL || item.getType() == Material.NETHERITE_HOE) {
                netherite = true;
            }

            if (SlimefunTag.EXPLOSIVE_SHOVEL_BLOCKS.isTagged(blockType)) {
                if (netherite) {
                    item.setType(Material.NETHERITE_SHOVEL);
                } else {
                    item.setType(Material.DIAMOND_SHOVEL);
                }
            } else if (axeBlocks.contains(blockType)) {
                if (netherite) {
                    item.setType(Material.NETHERITE_AXE);
                } else {
                    item.setType(Material.DIAMOND_AXE);
                }
            } else if (hoeBlocks.contains(blockType)) {
                if (netherite) {
                    item.setType(Material.NETHERITE_HOE);
                } else {
                    item.setType(Material.DIAMOND_HOE);
                }
            } else {
                if (netherite) {
                    item.setType(Material.NETHERITE_PICKAXE);
                } else {
                    item.setType(Material.DIAMOND_PICKAXE);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();
        ItemStack item = p.getInventory().getItemInMainHand();
        SlimefunItem sfItem = SlimefunItem.getByItem(item);

        if (sfItem instanceof UpgradedPaxel) {

            boolean netherite = item.getType() == Material.NETHERITE_PICKAXE || item.getType() == Material.NETHERITE_AXE || item.getType() == Material.NETHERITE_SHOVEL || item.getType() == Material.NETHERITE_HOE;

            if (netherite) {
                item.setType(Material.NETHERITE_AXE);
            } else {
                item.setType(Material.DIAMOND_AXE);
            }
        }

    }

    @EventHandler(ignoreCancelled = true)
    private void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            ItemStack item = p.getInventory().getItemInMainHand();
            SlimefunItem sfItem = SlimefunItem.getByItem(item);

            if (sfItem instanceof UpgradedPaxel && b.getType() == Material.GRASS_BLOCK) {
                boolean netherite = item.getType() == Material.NETHERITE_PICKAXE || item.getType() == Material.NETHERITE_AXE || item.getType() == Material.NETHERITE_SHOVEL || item.getType() == Material.NETHERITE_HOE;

                if (netherite) {
                    item.setType(Material.NETHERITE_HOE);
                } else {
                    item.setType(Material.DIAMOND_HOE);
                }
            }
        }
    }
}
