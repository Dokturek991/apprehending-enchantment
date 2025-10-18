
Apprehending Enchantment
========================

A NeoForge mod for Minecraft `1.21.1` that adds a new weapon enchantment, Apprehending. Killing mobs with an Apprehending-enchanted sword or axe has a chance to drop that mob's vanilla spawn egg.

Features
- New enchantment: `Apprehending` (levels I–III) on `swords` and `axes`
- Player-only kills trigger the effect; excludes bosses
- Drops exactly one spawn egg per proc; no egg if the entity has no vanilla egg
- Chance per level: I 5%, II 10%, III 15%
- Not affected by `Looting` or similar drop-boosting enchantments

Obtaining
- Enchanting Table: Available like other weapon enchantments with standard XP costs
- Librarian trades (rare; only one Apprehending offer per librarian):
  - Level 2: ~20% chance to offer Apprehending I for `12–16` emeralds + `1 book`
  - Level 3: ~10% chance to offer Apprehending II for `24–28` emeralds + `1 book`
  - Level 4: ~5% chance to offer Apprehending III for `36–40` emeralds + `1 book`
  - Prices are rolled per villager within the ranges, so offers vary

Compatibility
- Works alongside common enchantments (e.g., `Mending`, `Unbreaking`, etc.)
- Only requires the enchant to be on the `main-hand` weapon

Installation
- Requires Minecraft `1.21.1` and NeoForge `21.1.208–21.1.211`
- Build: `./gradlew build`
- Place the generated JAR from `build/libs` into your Minecraft `mods` folder

Configuration
- No configuration options at this time

License
- All Rights Reserved

Changelog
- See `CHANGELOG.md` for version history and notable changes
