/**
 * All rights reserved. This material is confidential and proprietary to 7ROAD
 */
package com.lili.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author : cookie
 * @date : 2013-3-2
 * @version Zip压缩相关
 */
public class ZipUtil
{
    public static final String EXT = ".zip";
    private static final String BASE_DIR = "";

    // 符号"/"用来作为目录标识判断符
    private static final String PATH = "/";
    private static final int BUFFER = 1024;

    public static FileFilter XMLFilter = new FileFilter(".xml");

    /**
     * 压缩
     * 
     * @param srcFile
     * @param fileFilter
     * @param cprSonFile
     * @throws Exception
     */
    public static void compress(File srcFile, boolean cprSonFile,
            FilenameFilter fileFilter) throws Exception
    {
        String name = srcFile.getName();
        String basePath = srcFile.getParent() + PATH;
        String destPath = basePath + name + EXT;
        compress(srcFile, destPath, cprSonFile, fileFilter);
    }

    /**
     * 压缩
     * 
     * @param srcFile
     *            源路径
     * @param fileFilter
     * @param cprSonFile
     * @param destPath
     *            目标路径
     * @throws Exception
     */
    public static void compress(File srcFile, File destFile,
            boolean cprSonFile, FilenameFilter fileFilter) throws Exception
    {

        // 对输出文件做CRC32校验
        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                destFile), new CRC32());

        ZipOutputStream zos = new ZipOutputStream(cos);

        compress(srcFile, zos, BASE_DIR, cprSonFile, fileFilter);

        zos.flush();
        zos.close();
    }

    /**
     * 压缩文件
     * 
     * @param srcFile
     * @param destPath
     * @param fileFilter
     * @param cprSonFile
     * @throws Exception
     */
    public static void compress(File srcFile, String destPath,
            boolean cprSonFile, FilenameFilter fileFilter) throws Exception
    {
        compress(srcFile, new File(destPath), cprSonFile, fileFilter);
    }

    /**
     * 压缩
     * 
     * @param srcFile
     *            源路径
     * @param zos
     *            ZipOutputStream
     * @param basePath
     *            压缩包内相对路径
     * @param fileFilter
     * @param cprSonFile
     * @throws Exception
     */
    private static void compress(File srcFile, ZipOutputStream zos,
            String basePath, boolean cprSonFile, FilenameFilter fileFilter)
            throws Exception
    {
        if (srcFile.isDirectory())
        {
            compressDir(srcFile, zos, basePath, cprSonFile, fileFilter);
        }
        else
        {
            compressFile(srcFile, zos, basePath);
        }
    }

    /**
     * 压缩
     * 
     * @param srcPath
     * @throws Exception
     */
    public static void compress(String srcPath, boolean cprSonFile,
            FilenameFilter fileFilter) throws Exception
    {
        File srcFile = new File(srcPath);

        compress(srcFile, cprSonFile, fileFilter);
    }

    /**
     * 文件压缩
     * 
     * @param srcPath
     *            源文件路径
     * @param destPath
     *            目标文件路径
     * 
     */
    public static void compress(String srcPath, String destPath,
            boolean cprSonFile, FilenameFilter fileFilter) throws Exception
    {
        File srcFile = new File(srcPath);

        compress(srcFile, destPath, cprSonFile, fileFilter);
    }

    /**
     * 压缩目录
     * 
     * @param dir
     * @param zos
     * @param basePath
     * @param fileFilter
     * @param cprSonFile
     * @param compressSon
     *            是否压缩子目录
     * @throws Exception
     */
    private static void compressDir(File dir, ZipOutputStream zos,
            String basePath, boolean cprSonFile, FilenameFilter fileFilter)
            throws Exception
    {

        File[] files = dir.listFiles(fileFilter);

        // 构建空目录
        if (files.length < 1)
        {
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);

            zos.putNextEntry(entry);
            zos.closeEntry();
        }

        for (File file : files)
        {
            if (!cprSonFile && file.isDirectory())
            {
                continue;
            }
            // 递归压缩
            compress(file, zos, basePath + dir.getName() + PATH, cprSonFile,
                    fileFilter);

        }
    }

    /**
     * 文件压缩
     * 
     * @param file
     *            待压缩文件
     * @param zos
     *            ZipOutputStream
     * @param dir
     *            压缩文件中的当前路径
     * @throws Exception
     */
    private static void compressFile(File file, ZipOutputStream zos, String dir)
            throws Exception
    {

        /**
         * 压缩包内文件名定义
         * 
         * <pre>
         * 如果有多级目录，那么这里就需要给出包含目录的文件名 
         * 如果用WinRAR打开压缩包，中文名将显示为乱码
         * </pre>
         */
        ZipEntry entry = new ZipEntry(dir + file.getName());

        zos.putNextEntry(entry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                file));

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = bis.read(data, 0, BUFFER)) != -1)
        {
            zos.write(data, 0, count);
        }
        bis.close();

        zos.closeEntry();
    }

    /**
     * 文件过滤器
     * 
     * @author : Cookie
     * @date : 2012-1-12
     * @version
     * 
     */
    private static class FileFilter implements FilenameFilter
    {
        String fileNameEnd;

        public FileFilter(String regex)
        {
            super();
            this.fileNameEnd = regex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
         */
        @Override
        public boolean accept(File dir, String name)
        {
            return name.toLowerCase().endsWith(fileNameEnd);
        }
    }

    public static void main(String args[]) throws Exception
    {
        String path = "C:\\Documents and Settings\\cookie.hu.7ROAD\\桌面\\XProject\\dota\\server\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Request";
        // 压缩文件
        // ZipUtil.compress("d:\\f.txt");
        String destPath = "D:\\Request.zip";
        // 压缩目录
        ZipUtil.compress(path, false, XMLFilter);

        ZipUtil.compress(path, destPath, false, XMLFilter);

    }

    /**
     * 根据源数据，创建压缩文件
     * @param resultJson
     * @param destFileName
     * @param isCompress
     */
    public static void compress(String resultJson, String destFileName, boolean isCompress)
    {
        File file = new File(destFileName);
        FileOutputStream output;
        try
        {
            output = new FileOutputStream(file);
            if (!file.exists())
            {
                file.createNewFile();
            }
            if (isCompress)
            {
                output.write(CompressUtil.compress(resultJson));
            }
            else
            {
                output.write(resultJson.getBytes());
            }
            output.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
