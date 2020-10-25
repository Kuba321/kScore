package me.matyyy.kscore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.matyyy.kscore.api.KScoreAPI;

public class FileManager {

	private final KScoreAPI kscoreapi;
	private final File file;
	
	public FileManager(KScoreAPI zp) {
		this.kscoreapi = zp;
		
		this.file = new File(this.kscoreapi.getMainAPI().getDataFolder(), "config.yml");
	}
	
	public void createFiles() {
		if(!this.kscoreapi.getMainAPI().getDataFolder().exists()) {
			this.kscoreapi.getMainAPI().getDataFolder().mkdirs();
		}
		if(!this.file.exists()) {
			this.copy(this.kscoreapi.getMainAPI().getResource("config.yml"), this.file);
		}
	}
	
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] bf = new byte[1024];
			int l;
			while((l = in.read(bf)) > 0) {
				out.write(bf, 0, l);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
