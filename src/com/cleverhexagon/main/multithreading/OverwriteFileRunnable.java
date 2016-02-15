/**
 * 
 */
package com.cleverhexagon.main.multithreading;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author viktor.marinov
 * @param fileName 			the name, including extension of the file to be copied over.
 * @param sourceFolder 		the path to the folder from which to copy the file.
 * @param destinationFolder the path of the folder to which to copy the file.
 */
public class OverwriteFileRunnable implements Runnable {

	private final Path source;
	private final Path target;	
	
	public OverwriteFileRunnable(Path source, Path target) {
		super();
		this.source = source;
		this.target = target;
	}

	@Override
	public void run() {
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
