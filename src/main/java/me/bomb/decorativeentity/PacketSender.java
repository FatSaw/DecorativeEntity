package me.bomb.decorativeentity;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.bomb.decorativeentity.options.ArmorstandOptions;
import me.bomb.decorativeentity.options.EndCrystalOptions;
import me.bomb.decorativeentity.options.FallingBlockOptions;
import me.bomb.decorativeentity.options.HumanOptions;
import me.bomb.decorativeentity.options.MinecartOptions;
import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.packet.PacketEncoder;

final class PacketSender {

	private final Plugin plugin;
	private final BukkitScheduler sheduler;
	protected MinecartOptions minecartoptions;
	protected ArmorstandOptions armorstandoptions;
	protected EndCrystalOptions endcrystaloptions;
	protected FallingBlockOptions fallingblockoptions;
	protected HumanOptions humanoptions;

	protected PacketSender(Plugin plugin, BukkitScheduler sheduler) {
		this.plugin = plugin;
		this.sheduler = sheduler;
	}

	protected void sendPacketsForChunk(ChannelHandlerContext context, ChannelPromise promise, PacketEncoder encoder, Player player, long chunkpos) {
		World world = player.getWorld();
		String worldname = world.getName();
		int sent = 0;

		if (minecartoptions != null) {
			Packet[] minecartpackets = this.minecartoptions.getPackets(worldname, chunkpos);
			if (minecartpackets != null) {
				for (Packet packet : minecartpackets) {
					try {
						encoder.write(context, packet, promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (armorstandoptions != null) {
			Packet[] armorstandpackets = this.armorstandoptions.getPackets(worldname, chunkpos);
			if (armorstandpackets != null) {
				for (Packet packet : armorstandpackets) {
					try {
						encoder.write(context, packet, promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (endcrystaloptions != null) {
			Packet[] endcrystalpackets = this.endcrystaloptions.getPackets(worldname, chunkpos);
			if (endcrystalpackets != null) {
				for (Packet packet : endcrystalpackets) {
					try {
						encoder.write(context, packet, promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (fallingblockoptions != null) {
			Packet[] fallingblockpackets = this.fallingblockoptions.getPackets(worldname, chunkpos);
			if (fallingblockpackets != null) {
				for (Packet packet : fallingblockpackets) {
					try {
						encoder.write(context, packet, promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		Packet humanremove = null;

		if (humanoptions != null) {
			Packet[] humanpackets = this.humanoptions.getPackets(worldname, chunkpos);
			if (humanpackets != null) {
				int max = humanpackets.length;
				if (max > 0) {
					humanremove = humanpackets[--max];
				}
				int i = 0;
				while (i < max) {
					try {
						encoder.write(context, humanpackets[i], promise);
						++sent;
					} catch (Exception e) {
						e.printStackTrace();
					}
					++i;
				}
			}
		}

		if (sent == 0) {
			return;
		}
		try {
			encoder.flush(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (humanremove != null) {
			sheduler.runTaskLaterAsynchronously(plugin, new DelayedPacketSender(encoder, context, humanremove, promise), 50);
			/*
			 * try { encoder.write(context, humanremove, promise); ++sent; } catch
			 * (Exception e) { e.printStackTrace(); }
			 */
		}
		// final int x = (int) (chunkpos >> 32) & 0xFFFFFFFF, z = (int) chunkpos &
		// 0xFFFFFFFF;
		// player.sendMessage("§f§l[§e§lDE_DEBUG§f§l]§r World: §e'§a" + world.getName()
		// + "§e'§r Sent §e'§a" + sent + "§e'§r packets for chunk X: §e'§a" + x + "§e'§r
		// Z: §e'§a" + z + "§e'§r."); //DEBUG
	}

	protected final class DelayedPacketSender implements Runnable {

		private final PacketEncoder packetencoder;
		private final ChannelHandlerContext context;
		private final Packet packet;
		private final ChannelPromise promise;

		protected DelayedPacketSender(PacketEncoder packetencoder, ChannelHandlerContext context, Packet packet, ChannelPromise promise) {
			this.packetencoder = packetencoder;
			this.context = context;
			this.packet = packet;
			this.promise = promise;
		}

		@Override
		public void run() {
			try {
				packetencoder.write(context, packet, promise);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
