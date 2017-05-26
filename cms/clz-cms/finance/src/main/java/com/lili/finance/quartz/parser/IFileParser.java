package com.lili.finance.quartz.parser;

import com.lili.finance.model.cms.TaskFile;

public interface IFileParser {
	
		void parserFile(TaskFile taskFile);
}
