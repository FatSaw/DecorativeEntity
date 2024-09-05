package me.bomb.decorativeentity.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import me.bomb.decorativeentity.packet.Packet;
import me.bomb.decorativeentity.util.SimpleConfiguration;

class Options {
	
	protected SimpleConfiguration sc;
	protected final HashMap<String, HashMap<Long, Packet[]>> packets = new HashMap<String, HashMap<Long, Packet[]>>();
	
	protected Options(File file, int buffersize) {
		byte[] bytes = null;
		if (!file.exists()) {
			InputStream is = ArmorstandOptions.class.getClassLoader().getResourceAsStream(file.getName());
			try {
				bytes = new byte[buffersize];
				bytes = Arrays.copyOf(bytes, is.read(bytes));
			} catch (IOException e) {
			}
			try {
				is.close();
			} catch (IOException e) {
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
			}
		} else {
			try {
				InputStream is = new FileInputStream(file);
				long filesize = file.length();
				if(filesize > 0x01000000) {
					filesize = 0x01000000;
				}
				bytes = new byte[(int) filesize];
				int size = is.read(bytes);
				if(size < filesize) {
					bytes = Arrays.copyOf(bytes, size);
				}
				is.close();
			} catch (IOException e) {
			}
		}
		this.sc = new SimpleConfiguration(bytes);
		bytes = null;
	}
	
	public final Packet[] getPackets(String worldname, long chunkpos) {
		HashMap<Long, Packet[]> packetoptions = packets.get(worldname);
		return packetoptions == null ? null : packetoptions.get(chunkpos);
	}
	
}
