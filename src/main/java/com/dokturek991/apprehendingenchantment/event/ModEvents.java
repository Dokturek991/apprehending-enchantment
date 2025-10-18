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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

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

        List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
        List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);
        // MARKED FOR DELETION IF ISSUES OCCUR
        List<VillagerTrades.ItemListing> level5 = event.getTrades().get(5);

        final String tradeFlagKey = ApprehendingEnchantmentMod.MODID + ":apprehending_trade_added";
        // MARKED FOR DELETION IF ISSUES OCCUR
        final String tradeFlagKeyEndless = ApprehendingEnchantmentMod.MODID + ":endless_suffering_trade_added";

        level2.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                // Ensure only one Apprehending offer per librarian
                if (trader.getPersistentData().getBoolean(tradeFlagKey)) return null;
                // Rarity gate: ~20% chance at level 2
                if (random.nextFloat() > 0.20f) return null;

                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                int price = Mth.nextInt(random, 12, 16);
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 1));
                MerchantOffer offer = new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 10, 0.05F);
                trader.getPersistentData().putBoolean(tradeFlagKey, true);
                return offer;
            }
        });
        level3.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                // Ensure only one Apprehending offer per librarian
                if (trader.getPersistentData().getBoolean(tradeFlagKey)) return null;
                // Rarity gate: ~10% chance at level 3
                if (random.nextFloat() > 0.10f) return null;

                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                int price = Mth.nextInt(random, 24, 28);
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 2));
                MerchantOffer offer = new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 15, 0.05F);
                trader.getPersistentData().putBoolean(tradeFlagKey, true);
                return offer;
            }
        });
        level4.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                // Ensure only one Apprehending offer per librarian
                if (trader.getPersistentData().getBoolean(tradeFlagKey)) return null;
                // Rarity gate: ~5% chance at level 4
                if (random.nextFloat() > 0.05f) return null;

                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.APPREHENDING_KEY);
                if (holderOpt.isEmpty()) return null;
                int price = Mth.nextInt(random, 36, 40);
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 3));
                MerchantOffer offer = new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 20, 0.05F);
                trader.getPersistentData().putBoolean(tradeFlagKey, true);
                return offer;
            }
        });
        // MARKED FOR DELETION IF ISSUES OCCUR
        level5.add(new VillagerTrades.ItemListing() {
            @Override
            public MerchantOffer getOffer(Entity trader, RandomSource random) {
                // Ensure only one Endless Suffering offer per librarian
                if (trader.getPersistentData().getBoolean(tradeFlagKeyEndless)) return null;
                // Rarity gate: ~2% chance at level 5 (master)
                if (random.nextFloat() > 0.02f) return null;

                var holderOpt = trader.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolder(ApprehendingEnchantmentMod.ENDLESS_SUFFERING_KEY);
                if (holderOpt.isEmpty()) return null;
                int price = Mth.nextInt(random, 56, 64);
                ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holderOpt.get(), 1));
                MerchantOffer offer = new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, price), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(Items.BOOK, 1)), book, 12, 30, 0.05F);
                trader.getPersistentData().putBoolean(tradeFlagKeyEndless, true);
                return offer;
            }
        });
    }

    @SubscribeEvent
    // MARKED FOR DELETION IF ISSUES OCCUR
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide) return;

        // Check chestplate for Endless Suffering I
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.isEmpty()) return;
        var enchantmentRegistry = player.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
        var endlessHolderOpt = enchantmentRegistry.getHolder(com.dokturek991.apprehendingenchantment.ApprehendingEnchantmentMod.ENDLESS_SUFFERING_KEY);
        if (endlessHolderOpt.isEmpty()) return;
        int level = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(endlessHolderOpt.get(), chest);
        if (level <= 0) return; // single level only

        // 2s cooldown between procs when in fight
        final String cdKey = com.dokturek991.apprehendingenchantment.ApprehendingEnchantmentMod.MODID + ":endless_suffering_cd_until";
        long now = player.level().getGameTime();
        long until = player.getPersistentData().getLong(cdKey);
        if (until > now) return;
        player.getPersistentData().putLong(cdKey, now + 40); // 40 ticks = 2 seconds

        // Wave-like flame particles around the player (radius 2)
        ServerLevel server = (ServerLevel) player.level();
        double radius = 2.0D;
        int points = 24;
        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2.0) * i / points;
            double px = player.getX() + radius * Math.cos(angle);
            double pz = player.getZ() + radius * Math.sin(angle);
            server.sendParticles(ParticleTypes.FLAME, px, player.getY() + 0.1, pz, 4, 0.05, 0.05, 0.05, 0.01);
        }

        // Activation sound
        server.playSound(null, player.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 0.8f, 1.0f);

        // Damage nearby entities and heal wearer per entity hit
        AABB box = new AABB(
                player.getX() - radius, player.getY() - 1.0, player.getZ() - radius,
                player.getX() + radius, player.getY() + 2.0, player.getZ() + radius
        );
        java.util.List<LivingEntity> targets = server.getEntitiesOfClass(LivingEntity.class, box, e -> e != player && e.isAlive());
        int hitCount = 0;
        for (LivingEntity target : targets) {
            target.hurt(server.damageSources().magic(), 2.0F); // 2 damage
            hitCount++;
        }
        if (hitCount > 0) {
            player.heal(2.0F * hitCount); // 2 HP per affected entity
        }
    }
}