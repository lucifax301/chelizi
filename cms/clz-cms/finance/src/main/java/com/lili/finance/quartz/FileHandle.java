package com.lili.finance.quartz;

import com.lili.finance.model.cms.TaskFile;
import com.lili.finance.quartz.parser.IFileParser;

public class FileHandle {
	private IFileParser fileParser;
	
	public FileHandle(IFileParser fileParser) {
		this.fileParser = fileParser;
	}
	
	public void parserFile(TaskFile taskFile){
		fileParser.parserFile(taskFile);
	}

}
