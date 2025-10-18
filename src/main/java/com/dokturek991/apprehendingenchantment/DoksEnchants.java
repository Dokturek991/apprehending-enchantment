package com.dokturek991.apprehendingenchantment;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(DoksEnchants.MODID)
public class DoksEnchants {
    public static final String MODID = "apprehending_enchantment";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Data-driven enchantment key (JSON-defined in data/apprehending_enchantment/enchantment/apprehending.json)
    public static final net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> APPREHENDING_KEY =
            net.minecraft.resources.ResourceKey.create(Registries.ENCHANTMENT, net.minecraft.resources.ResourceLocation.parse(MODID + ":apprehending"));

    // MARKED FOR DELETION IF ISSUES OCCUR
    public static final net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> ENDLESS_SUFFERING_KEY =
            net.minecraft.resources.ResourceKey.create(Registries.ENCHANTMENT, net.minecraft.resources.ResourceLocation.parse(MODID + ":endless_suffering"));

    public DoksEnchants(IEventBus modEventBus, ModContainer modContainer) {
        // No template blocks/items/tabs; core features are implemented via events and data JSON.
    }
}
