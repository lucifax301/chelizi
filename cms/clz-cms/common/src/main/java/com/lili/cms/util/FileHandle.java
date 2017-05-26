package com.lili.cms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * 文件处理类
 * 
 * @author lzb
 *
 */
public class FileHandle {
	Logger logger = Logger.getLogger(FileHandle.class);

	public static File upLoadFile(InputStream is, String tagFileName) {
		File outfile = new File(tagFileName);
		OutputStream os = null;

		try {
			os = new FileOutputStream(outfile);
			byte buffer[] = new byte[4 * 1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return outfile;
	}
}
