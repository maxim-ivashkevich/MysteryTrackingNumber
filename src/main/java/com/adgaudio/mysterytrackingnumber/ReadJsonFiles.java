package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class ReadJsonFiles {
	public static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		URI uri;
		try {
			uri = ReadJsonFiles.class.getResource(path).toURI();
		} catch (URISyntaxException e) {
			uri = null;
		}
		Path myPath;
		if (uri != null && uri.getScheme().equals("jar")) {
			FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
			myPath = fileSystem.getPath(path);
		} else {
			myPath = Paths.get(uri);
		}
		System.out.println(myPath);
		Stream<Path> walk = Files.walk(myPath, 1);
		for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
			String tmp = it.next().getFileName().toString();
			if (tmp.endsWith(".json")) {
				filenames.add(path + "/" + tmp);
			}
		}
		System.out.println(filenames);
		walk.close();

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
