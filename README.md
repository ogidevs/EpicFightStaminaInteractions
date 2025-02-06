# Epic Fight Stamina Interactions

This mod is a recreation of Noyji's Epic Fight Stamina Plus made with the intent of porting his code inside the MDK environment, reorganize the code in a more readable way, make it fully configurable and fix the server-client incompatibility.

I also removed the requirement of having the VC Glider mod for it to function properly with the intention of making a small addon in the form of a mod or datapack to reintroduce the former features.

## What the mod does?
The mod makes it so that when you perform an actions as: Attacking, Sprinting, Using a Bow/Crossbow, Blocking with a shield will consume Epic Fight's stamina in a Souls-like type of mechanic.

After stamina reaches 0, you will receive a potion effect that will completely slow your movement making you more vulnerable to attacks for a couple instants.

It also adds some buffs when resting (crouching) near campfires (Each campfire gives you different effects).

## Requirements

Minecraft Forge Mod Loader Version: 47.3.11

Epic Fight: https://www.curseforge.com/minecraft/mc-mods/epic-fight-mod

## Known bugs
- When loading a world stamina is set as 100 instead of the latest value when it was saved.
- A single tick of the animation of attack will happen even if stamina reaches 0 if mouse is spammed... No Damage will be done though and tired animation will reprise where left

## Special Thanks

- **WATOTO** from the Epic fight discord for giving me the two animations that have been added in this mod, thanks for support
- **Forixaim** for suggesting me the registry idea to implement APIs and Datapacks
- **M3tte** of the **"E.G.O. Weapons"** for explaining me how mixins and Living Motions
- **Yonchi Chikito** for listening me vent and thinking with me on how to run the animations
- **Yesssman** for creating Epic Fight and for patiently giving support to everyone in the Developers section of the discord

The Epic Fight Development team and their discord for the help:
- https://discord.gg/V7tZgNsG
- https://github.com/Yesssssman/epicfightmod
  
Noyji for making the original mod:
- https://github.com/Noyji/EpicFightStaminaPlus
