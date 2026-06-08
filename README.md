# SoulCaskets
A lightweight death grave plugin for [Paper](https://papermc.io) 26.1.2+.
When a player dies, their items and experience are stored in a grave block — no drops, no panic.

---

## Features

- **Death graves** - a configurable block is placed at the death location, storing all items and XP
- **No item drops** - nothing hits the ground on death
- **Coordinates on death** - the grave's location is sent to the player's chat instantly
- **Owner-only retrieval** - only the player who died can open their grave
- **Full XP recovery** - all dropped experience is returned on collection
- **Optional expiry timer** - graves can be set to expire after a configurable duration, dropping everything
- **Configurable grave block** - any valid block material can be set in the config
- **Void-safe** - graves placed above the minimum world height if the player dies in the void

---

## Installation

1. Download the latest `.jar` from [Releases](../../releases) or [Modrinth](https://modrinth.com/plugin/soulcaskets).
2. Place the `.jar` in your server's `plugins/` folder.
3. Restart your server.

**Requirements:** Paper 26.1.2+ · Java 25+

---

## Usage

When a player dies, a grave block appears at the death location and a message is sent to their chat with the coordinates. Right-clicking the grave block returns all stored items and experience to the player.

### Players

| Action | How |
|---|---|
| Find your grave | Read the coordinates from chat on death |
| Retrieve items & XP | Right-click your grave block |

Other players cannot open your grave.

---

## Configuration

`plugins/SoulCaskets/config.yml`

| Key | Default | Description |
|---|---|---|
| `grave-block` | `SOUL_SAND` | Block type used for the grave |
| `timer-enabled` | `false` | Whether graves expire after a set duration |
| `timer-duration` | `3600` | Seconds until an expired grave drops its contents |
| `message-prefix` | `[SoulCaskets]` | Chat message prefix (MiniMessage format) |

---

## Building from source

Requires Java 25 and Gradle.

```bash
git clone https://github.com/your-name/SoulCaskets.git
cd SoulCaskets
./gradlew build
```

The compiled JAR will be in `build/libs/SoulCaskets-1.0.0.jar`.

Every push to `main` triggers a [GitHub Actions](.github/workflows/build.yml) build — download the artifact from the **Actions** tab.

---

## License

[Apache License 2.0](LICENSE) - your-name
