package net.termer.tconfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {
	/**
	 * Writes the provided String to a File
	 * @param str the String to write to the File
	 * @param file the File to write to
	 * @throws IOException if writing to the File fails
	 * @since 1.0
	 */
	public static void print(String str, File file) throws IOException {
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(file);
		for(int i = 0; i < str.length(); i++) {
			fout.write((int)str.charAt(i));
		}
		fout.close();
	}
}
