# Changelog

All notable changes to this project will be documented in this file.

## [0.1.3] - 2025-10-18
- Added new chestplate enchantment: Endless Suffering (level I)
- Triggers 2s-cooldown flame wave when wearer is damaged; heals per hit
- Deals 2 damage to nearby entities (radius 2); heals wearer 2 HP per entity
- Librarian-only acquisition: Master trade ~2% chance, 56–64 emeralds + 1 book
- Not available via Enchanting Table; still applicable via Anvil (cost 4)
- Datapack fixes: slots set to ["armor"], weight 1; disabled primary_items
- Verified build and server smoke run; no registry/datapack errors

## [0.1.2] - 2025-10-18
- Nerfed librarian trades to reduce prevalence:
  - Per-villager rarity gates: L2 ~20%, L3 ~10%, L4 ~5%
  - Enforce only one Apprehending trade per librarian via persistent flag
  - Prices rolled per villager within ranges (L2 `12–16`, L3 `24–28`, L4 `36–40`)
- No changes to core spawn-egg drop mechanics or enchantment levels

## [0.1.1] - 2025-10-17
- Initial release for Minecraft 1.21.1 / NeoForge 21.1.208–21.1.211
- Added Apprehending enchantment (levels I–III) for swords and axes
- Implemented player-only spawn egg drop logic with boss exclusion
- Added librarian trades for Apprehending books with balanced pricing
- Provided localization entries and updated project README