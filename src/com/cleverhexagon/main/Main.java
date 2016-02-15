/**
 * 
 */
package com.cleverhexagon.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cleverhexagon.main.multithreading.OverwriteFileRunnable;

/**
 * @author viktor.marinov
 *
 */
public class Main {
	private static final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * @param args - First parameter is the destination folder in which to overwrite the files.
	 * Second parameter is the source folder from which to get the new files.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length >= 2) {
			String destinationFolder = args[1];
			String sourceFolder = args[0];
			
			List<Path> sourceFolderFileNames = new ArrayList<Path>();
			List<Path> destidationFolderFileNames = new ArrayList<Path>();
			
			try {
				Files.walk(Paths.get(destinationFolder)).forEach(fileName -> {
					if (Files.isRegularFile(fileName)) {
						destidationFolderFileNames.add(fileName);
						Path sourcePath = Paths.get(sourceFolder + "\\"+ fileName.getFileName().toString());
						sourceFolderFileNames.add(sourcePath);
					}
				});
				
				if (destinationFolder.length() > 0 ) {
					Iterator<Path> destinationFiles = destidationFolderFileNames.iterator();
					
					while(destinationFiles.hasNext()) {
						Path targetFile = destinationFiles.next();
						for (int i = 0 ; i < sourceFolderFileNames.size() ; i++) {
							Path sourceFile = sourceFolderFileNames.get(i);
							if (targetFile.getFileName().equals(sourceFile.getFileName())) {
								System.out.println("Target File: " + targetFile.getFileName().toString());
								Runnable task = new OverwriteFileRunnable(sourceFile, targetFile);
								
								executor.execute(task);
								break;
							}
						}
					}
				} else {
					System.out.println("Folder to overwrite is empty!");
					throw new FileNotFoundException("Folder Empty!");
				}
			} catch (IOException | NullPointerException e ) {
				e.printStackTrace();
			}
		}
	}
}
