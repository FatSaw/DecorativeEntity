# DecorativeEntity
Spawns decorative entities
## Features / how it works:
* Packet handler gets sent chunk location, send all entity packets to player after chunk was sent.
* Entity packets precalculated on plugin start.
* Separated packetencoder used to full access all packet fields(some implemented yet).

## Entity list
* Text hologramms
* Fallingblock
* EndCrystal with beam
* Minecart with custom block
* Player

## Commands:

### How it works
* `decorativeentity reload` - reload configs
* `decorativeentity stateid` - display target block state id in hex (it can be useful to configure blockid)

### Permissions
* `decorativeentity.reload` - allows use reload subcommand
* `decorativeentity.stateid` - allows use stateid subcommand
