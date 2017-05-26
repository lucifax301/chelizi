package com.lili.configfile.manager.parser;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.redis.RedisUtil;

public class ResourceListener {
	
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

	private WatchService watcher;

	private String listenerPath;
	
	RedisUtil redisUtil;
	
	private ResourceListener(String path, RedisUtil redisUtil) {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			this.listenerPath = path;
			this.redisUtil = redisUtil;
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		fixedThreadPool.execute(new Listener(watcher, this.listenerPath, this.redisUtil));
	}

	public static void addListener(String path, RedisUtil redisUtil) throws IOException {
		ResourceListener resourceListener = new ResourceListener(path, redisUtil);
		Path p = Paths.get(path);
		p.register(resourceListener.watcher, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_CREATE);
	}

	/*public static void main(String[] args) throws IOException {
		ResourceListener.addListener("E:\\aaa\\");
	}*/
}

class Listener implements Runnable {
	private Logger logger = LoggerFactory.getLogger(ResourceListener.class);
	
	private WatchService service;
	
	private String rootPath;
	
	 RedisUtil redisUtil;
	
	private final String MODIFY = "ENTRY_MODIFY";
	
	private final String CREATE = "ENTRY_CREATE";

	public Listener(WatchService service, String rootPath, RedisUtil redisUtil) {
		this.service = service;
		this.rootPath = rootPath;
		this.redisUtil = redisUtil;
	}

	public void run() {
		try {
			while (true) {
				WatchKey watchKey = service.take();
				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
				for (WatchEvent<?> event : watchEvents) {
					// TODO 根据事件类型采取不同的操作。。。。。。。
					logger.info("[" + rootPath + "/" + event.context() + "]文件发生了[" + event.kind() + "]事件");
					WatchEvent.Kind kind = event.kind();
					if (kind == OVERFLOW) {// 事件可能lost or discarded
						continue;
					}
					
					WatchEvent<Path> e = (WatchEvent<Path>) event;
					Path fileName = e.context();
					String fileNameStr = fileName.toString();
					logger.info("Event " + kind.name() + " has happened,which fileName is " + fileName);
					if (kind.name().equals(MODIFY) || kind.name().equals(CREATE)) { // 修改文件，更新缓存
						logger.info("********************************CREATE or Modify File And Update Redis: ");
						
						if (fileNameStr != null && !"".equals(fileNameStr)) {
							Integer handleType = 1;
							logger.info("*********************************  " + fileNameStr.substring(fileNameStr.lastIndexOf("_") + 1, fileNameStr.lastIndexOf(".txt")));
							handleType = Integer.parseInt(fileNameStr.substring(fileNameStr.lastIndexOf("_") + 1, fileNameStr.lastIndexOf(".txt")));
							FileHandle fileHandle = null;
							// 1 表示通用处理；2-微课
							switch (handleType) {
							case 1:
								fileHandle = new FileHandle(new CommonFileParser(redisUtil));
								fileHandle.parserFile(rootPath, fileName.toString());
								break;
							case 2:
								fileHandle = new FileHandle(new AdFileParser(redisUtil));
								fileHandle.parserFile(rootPath, fileName.toString());
								break;
							default:
								break;
							}
						}
					}
				}
				watchKey.reset();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				service.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}