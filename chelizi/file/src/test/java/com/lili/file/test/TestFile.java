/**
 * 
 */
package com.lili.file.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author linbo
 *
 */
public class TestFile
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
    }

}
