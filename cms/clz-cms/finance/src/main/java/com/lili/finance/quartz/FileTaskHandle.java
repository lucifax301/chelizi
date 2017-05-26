package com.lili.finance.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.finance.manager.cms.IFileTaskManager;
import com.lili.finance.model.cms.TaskFile;
import com.lili.finance.quartz.parser.CarFileParser;
import com.lili.finance.quartz.parser.CoachFileParser;
import com.lili.finance.quartz.parser.StudentFileParser;
import com.lili.finance.quartz.parser.TrfieldFileParser;

/**
 * 定时处理上传的基础数据文件
 * 
 * @author lzb
 *
 */
public class FileTaskHandle {

	private static final Logger logger = Logger.getLogger(FileTaskHandle.class);

	@Autowired
	IFileTaskManager fileTaskManager;



	public void work() {
		logger.info("****************************Start Task For FileTaskHandle***************************");
		List<TaskFile> fileList = fileTaskManager.queryHandleList();
		if (fileList.size() > 0) {
			handleChinaPay(fileList);
		}
		logger.info("****************************End Task For FileTaskHandle***************************");
	}

	/**
	 * 任务调度：策略模式
	 * @param fileList
	 */
	public void handleChinaPay(List<TaskFile> fileList) {
		logger.info("****************************Start Task ! ***************************");
		try {
			for (TaskFile taskFile : fileList) {
				FileHandle fileHandle = null;
				switch (taskFile.getFileType()) {
				case 1:
					fileHandle = new FileHandle(new CarFileParser());
					fileHandle.parserFile(taskFile);
					break;
				case 2:
					fileHandle = new FileHandle(new TrfieldFileParser());
					fileHandle.parserFile(taskFile);
					break;
				case 3:
					fileHandle = new FileHandle(new StudentFileParser());
					fileHandle.parserFile(taskFile);
					break;
				case 4:
					fileHandle = new FileHandle(new CoachFileParser());
					fileHandle.parserFile(taskFile);
				default:
					break;
				}
			}
			logger.info("****************************End Task ! ***************************");
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
