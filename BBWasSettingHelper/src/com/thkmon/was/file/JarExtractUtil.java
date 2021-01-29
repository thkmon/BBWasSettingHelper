package com.thkmon.was.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarExtractUtil {

	public static void extractJar(String jarFilePath, String destinationDirPath) throws NullPointerException, Exception {
		File f = null;
		JarFile jarFile = null;
		
		try {
			f = new File(jarFilePath);
			jarFile = new JarFile(f);
			File destDir = new File(destinationDirPath);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
	
			Enumeration entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					File newDir = new File(destDir, entry.getName());
					if (entry.getName().toUpperCase().indexOf("META-INF") > -1) {
						continue;
					}
					
					newDir.mkdirs();
					
				} else {
					if (entry.getName().toUpperCase().indexOf("META-INF") > -1) {
						continue;
					}
	
					File targetFileObj = new File(destDir, entry.getName());
					
					InputStream input = null;
					FileOutputStream fileOutput = null;
					BufferedOutputStream buffOutput = null;
	
					try {
						input = jarFile.getInputStream(entry);
						fileOutput = new FileOutputStream(targetFileObj);
						buffOutput = new BufferedOutputStream(fileOutput);
						byte[] buf = new byte[4096];
						int read = -1;
						while ((read = input.read(buf)) != -1) {
							buffOutput.write(buf, 0, read);
						}
						buffOutput.flush();
						buffOutput.close();
	
						fileOutput.close();
						input.close();
	
					} catch (IOException e) {
						throw e;
					} catch (Exception e) {
						throw e;
	
					} finally {
						try {
							if (buffOutput != null) {
								buffOutput.close();
							}
						} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							buffOutput = null;
						}
	
						try {
							if (fileOutput != null) {
								fileOutput.close();
							}
						} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							fileOutput = null;
						}
	
						try {
							if (input != null) {
								input.close();
							}
						} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							input = null;
						}
					}
				}
			}
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (IOException e) {
			} catch (Exception e) {
			} finally {
				jarFile = null;
			}
		}
	}
}