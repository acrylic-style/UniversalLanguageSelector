# UniversalLanguageSelector

This plugin provides some APIs related to players' language and adds `/language` command to set their language.

Also, this plugin provides APIs to make translation easier (of course, you can use your own localization too)

## Requirements

### Spigot
- TomeitoLib (see acrylic-style/TomeitoLibrary)

### BungeeCord
- TomeitoBungee (also see acrylic-style/TomeitoLibrary)

## some coding stuff
- api package should not import both bukkit and bungee apis.
  - UniversalLanguageSelectorAPI is the only exception as they have #bukkit to access bukkit specific apis, and #bungee to access bungee specific apis.
- bungee package cannot import TomeitoLib and all spigot apis
