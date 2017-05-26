package com.lili.configfile.manager.parser;

import com.lili.common.vo.ReqResult;
import com.lili.configfile.manager.FileParserManager;

public class FileHandle {
	private FileParserManager fileParser;
	
	
	public FileHandle(FileParserManager fileParser) {
		this.fileParser = fileParser;
	}
	
	public ReqResult parserFile(String path, String fileName){
		return fileParser.parserFile(path, fileName);
	}

}
