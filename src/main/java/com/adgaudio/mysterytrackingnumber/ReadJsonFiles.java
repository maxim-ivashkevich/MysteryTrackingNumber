package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ReadJsonFiles {
	public static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();
		// filenames.addAll(Arrays.asList(
		// "/couriers/usps.json", "/couriers/s10.json", "/couriers/dhl.json",
		// "/couriers/misc.json", "/couriers/fedex.json"));
		// return filenames;
		//
		CodeSource src = ReadJsonFiles.class.getProtectionDomain().getCodeSource();
		if (src != null) {
			URL jar = src.getLocation();
			try {
				if (jar.toURI().getScheme().equals("jar")) {
					ZipInputStream zip = new ZipInputStream(jar.openStream());
					while (true) {
						ZipEntry e = zip.getNextEntry();
						if (e == null)
							break;
						String name = e.getName();
						if (name.startsWith("couriers")) {
							filenames.add(name);
						}
					}
				} else {
					try (InputStream in = ReadJsonFiles.class.getResourceAsStream(path);
							BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
						String resource;

						while ((resource = br.readLine()) != null) {
							filenames.add(path + "/" + resource);
						}
					}
				}
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
		return filenames;
	}

	static BufferedReader openFile(String filepath) {
		InputStream in = ReadJsonFiles.class.getResourceAsStream(filepath);
		if (in == null) {
			throw new RuntimeException("Could not load resource: " + filepath);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return br;
	}
}
