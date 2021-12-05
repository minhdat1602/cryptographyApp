package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FIleUtils {
	public static String readFile(File file) {
		try (InputStream is = new FileInputStream(file);) {
			int bytesRead = 0;
			int bytesToRead = (int) file.length();
			byte[] data = new byte[bytesToRead];
			while (bytesRead < bytesToRead) {
				bytesRead += is.read(data, bytesRead, bytesToRead - bytesRead);
			}

			return new String(data);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void writeFile(String src, File des) {
		byte[] data = src.getBytes();
		try (OutputStream os = new FileOutputStream(des)) {
			os.write(data);
			os.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
