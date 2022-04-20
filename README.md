# LuckyBlock

### A configurable Lucky Block plugin

---

## Example of config.json

```json
[
  {
    "type": "explosion",
    "radius": 10,
    "break_blocks": true,
    "burn_blocks": true
  },
  {
    "type": "spawn_item",
    "item": "minecraft:diamond",
    "min_count": 1,
    "max_count": 10
  },
  {
    "type": "spawn_item",
    "item": "minecraft:emerald",
    "min_count": 1,
    "max_count": 1
  },
  {
    "type": "random_remove_item"
  },
  {
    "type": "remove_item",
    "item_types": [
      "diamond_pickaxe",
      "diamond.*",
      "(?s).*_SWORD",
      "dirt"
    ],
    "min_count": 1,
    "max_count": 10,
    "all_items": true
  },
  {
    "type": "spawn_entity",
    "entity": "zombie",
    "min_count": 1,
    "max_count": 20,
    "distance": 10
  }
]
```

You can see a list of all the available events [here](https://github.com/Minemobs/luckyblock/blob/main/src/main/java/fr/minemobs/luckyblock/object/LBEvents.java#L9)
