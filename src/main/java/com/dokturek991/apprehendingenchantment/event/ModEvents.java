package com.dokturek991.apprehendingenchantment.event;

import com.dokturek991.apprehendingenchantment.ApprehendingEnchantmentMod;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = ApprehendingEnchantmentMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead.level().isClientSide) return;

        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player player)) return;

        // Exclude bosses
        if (dead instanceof WitherBoss || dead instanceof EnderDragon) return;

        // Only if the entity has a vanilla spawn egg
        SpawnEggItem eggItem = SpawnEggItem.byId(dead.getType());
        if (eggItem == null) return;

        // Check enchantment level on the player's main-hand weapon
        ItemStack weapon = player.getMainHandItem();
        var enchantmentRegistry = dead.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
         var apprehendingHolderOpt = enchantmentRegistry.getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
         if (apprehendingHolderOpt.isEmpty()) return;
         int level = EnchantmentHelper.getItemEnchantmentLevel(apprehendingHolderOpt.get(), weapon);
        if (level <= 0) return;

        double chance = switch (level) {
            case 1 -> 0.05D; // 5%
            case 2 -> 0.10D; // 10%
            case 3 -> 0.15D; // 15%
            default -> 0.0D;
        };

        if (player.getRandom().nextDouble() >= chance) return;

        ItemStack eggStack = new ItemStack(eggItem);
        event.getDrops().add(new ItemEntity(dead.level(), dead.getX(), dead.getY(), dead.getZ(), eggStack));
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.LIBRARIAN) return;

        // Randomize price in the provided ranges; include a book to mirror vanilla librarian trades
        int price1 = Mth.nextInt(RandomSource.create(), 12, 16);
        int price2 = Mth.nextInt(RandomSource.create(), 24, 28);
        int price3 = Mth.nextInt(RandomSource.create(), 36, 40);

        List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
        List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);

        level2.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 1));
                return new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price1), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 10, 0.05F);
            }
        });
        level3.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 2));
                return new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price2), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 15, 0.05F);
            }
        });
        level4.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 3));
                return new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price3), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 20, 0.05F);
            }
        });
    }
}