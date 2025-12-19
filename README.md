# LandClaim Plugin

Advanced Land Protection & Claim System for Minecraft Spigot/Paper servers.

## Features

- **Land Claiming**: Players can claim and protect their land
- **Protection System**: Prevents unauthorized building, breaking, and interaction
- **Configurable Limits**: Set maximum claims per player
- **Easy Commands**: Simple and intuitive command system
- **Persistent Storage**: Claims are saved and loaded automatically

## Commands

- `/claim` - Claim the area you're standing in
- `/unclaim` - Remove your claim at your location
- `/landclaim` - Main command with subcommands
  - `/landclaim info` - View claim information
  - `/landclaim list` - List your claims

## Permissions

- `landclaim.claim` - Allow claiming land (default: true)
- `landclaim.unclaim` - Allow unclaiming land (default: true)
- `landclaim.admin` - Admin commands (default: op)
- `landclaim.*` - All permissions (default: op)

## Configuration

Edit `config.yml` to customize:

- `max-claims`: Maximum number of claims per player (default: 3)
- `min-claim-size`: Minimum claim size in blocks (default: 10)
- `max-claim-size`: Maximum claim size in blocks (default: 1000)
- `protect-build`: Protect against building (default: true)
- `protect-break`: Protect against breaking (default: true)
- `protect-interact`: Protect against interaction (default: true)

## Building

```bash
mvn clean package
```

The compiled JAR will be in the `target/` directory.

## Requirements

- Java 17+
- Spigot/Paper 1.20.1+

## Installation

1. Build the plugin using Maven
2. Place the JAR file in your server's `plugins/` folder
3. Restart your server
4. Configure the plugin in `plugins/LandClaim/config.yml`

