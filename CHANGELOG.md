# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2025-10-18
- Rebrand: display name changed to "Dok's Enchants"; `mod_id` is now `doks_enchants`.
- Java: renamed main mod class to `DoksEnchants` and events class to `DoksEnchantsEvents`; updated annotations and constants accordingly.
- Config: bumped `mod_version` to `1.0.0` and `mod_name` in `gradle.properties`; `neoforge.mods.toml` is generated from templates and now reflects the new display name and version.
- Docs: updated `README.md` title and current version.
- Build: validated compilation locally after renames; no functional regressions observed.
- Behavior: Apprehending and Endless Suffering mechanics unchanged; Endless Suffering librarian trade remains Novice (~5% chance) as of 0.2.1.

## [0.2.1] - 2025-10-18
- Librarian trade for Endless Suffering moved to Novice (level 1) with ~5% chance; price 56–64 emeralds + 1 book.
- Documentation: updated README to reflect novice trade and added version header.
- Build: verified successful compilation after trade logic changes.

## [0.2.0] - 2025-10-18
- Switched to vanilla item tags for cross-mod compatibility:
  - Endless Suffering: `supported_items` now `#minecraft:enchantable/chest_armor` (applies to any chestplate that uses the tag)
  - Apprehending: `supported_items` and `primary_items` now include `#minecraft:axes` and `#minecraft:enchantable/sharp_weapon` (covers swords and axes from mods)
- Updated README to the requested structure; documented tag-based applicability and obtaining methods
- Bumped mod version to `0.2.0`
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