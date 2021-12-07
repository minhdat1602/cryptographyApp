package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class FIleUtils {
	public static String readFile(File file) {
		try (InputStream is = new FileInputStream(file);) {
			int bytesRead = 0;
			int bytesToRead = (int) file.length();
			byte[] data = new byte[bytesToRead];
			while (bytesRead < bytesToRead) {
				bytesRead += is.read(data, bytesRead, bytesToRead - bytesRead);
			}

			return file.getName().endsWith("txt") ? new String(data) : Base64.getEncoder().encodeToString(data);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static void writeFile(String src, File des) {
		byte[] data = null;
		try (OutputStream os = new FileOutputStream(des)) {
			data = des.getName().endsWith("txt") ? src.getBytes("UTF-8") : Base64.getDecoder().decode(src.getBytes("UTF-8"));
			os.write(data);
			os.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		File file = new File("D:\\MS001.jpg");
		byte[] data = null;
		try (InputStream is = new FileInputStream(file);) {
			int bytesRead = 0;
			int bytesToRead = (int) file.length();
			data = new byte[bytesToRead];
			while (bytesRead < bytesToRead) {
				bytesRead += is.read(data, bytesRead, bytesToRead - bytesRead);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		String a = Base64.getEncoder().encodeToString(data);

		byte[] b = Base64.getDecoder().decode(a.getBytes("UTF-8"));
		File des = new File("D:\\MS002.jpg");
		try (OutputStream os = new FileOutputStream(des)) {
			os.write(b);
			os.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
