# UniversalLanguageSelector

This plugin provides some APIs related to players' language and adds `/language` command to set their language.

## some coding stuff
- api package should not import both bukkit and bungee apis.
  - UniversalLanguageSelectorAPI is the only exception as they have #bukkit to access bukkit specific apis, and #bungee to access bungee specific apis.
- bungee package cannot import TomeitoLib and all spigot apis
