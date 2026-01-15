# MUD2 Protocol Codes

## Code to CSS Class Mapping

| Code | CSS | Example |
|------|-----|---------|
| `<0201>` | `ka` | `Narrow road between lands`, `Hall`, `Cellar` |
| `<0202>` | `ca` | `This cosy, Tudor period room...` |
| `<030001>` | `ca` | (before weather) |
| `<1402>` | `ca` | `It is raining.` |
| `<030100>` | `ga` | `a cup of tea`, `a peach`, `an orange` |
| `<030101>` | `ga` | `The kitchen door is locked shut.`, `There is a large stick` |
| `<030201>` | `oa` | `A bright red, rubber bouncy-ball`, `A picture postcard` |
| `<030300>` | `oa` | `a piece of obsidian` |
| `<030301>` | `oa` | `A streetsign has fallen here.`, `A heavy-looking antique barometer` |
| `<040001>` | `fa` | `An evil, black rat bares its razor-sharp incisors` |
| `<040003>` | `fa` | `The rat scuttles off.` |
| `<050001>` | `ba` | `Verso is here holding` |
| `<050006>` | `ba` | `Verso`, `Grreen the guest` |
| `<01>` | `ea` | (prompt) |
| `<0102>` | `ea` | `*`, `n`, `w`, `s` |
| `<1203>` | `ha` | (before Players:) |
| `<1207>` | `la` | `[Welcome, Grreen!...]` |
| `<2000>` | `pa` | (indoor room marker) |
| `<2013>` | `pa` | (outdoor room marker) |

## CSS Class Legend

| Class | Foreground | Background |
|-------|------------|------------|
| `ba` | RED | BLACK |
| `ca` | GREEN | BLACK |
| `ea` | BLUE | BLACK |
| `fa` | MAGENTA | BLACK |
| `ga` | CYAN | BLACK |
| `ha` | WHITE | BLACK |
| `ka` | LT_GREEN | BLACK |
| `la` | LT_YELLOW | BLACK |
| `oa` | LT_CYAN | BLACK |
| `pa` | LT_WHITE | BLACK |

## Prefix Matching

Codes are matched by stripping 2 characters at a time from the end until a match is found in BasicMudClientModeStyle.java.

Example: `<030301>` → `<0303` → `<03` → matches `oa`
