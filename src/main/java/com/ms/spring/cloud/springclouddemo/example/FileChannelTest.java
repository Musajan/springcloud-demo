package com.ms.spring.cloud.springclouddemo.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 * @Author: Sherlock
 * @Description:
 * @Date: Created in 16:11 2018/7/20
 * @Modified By:
 */
public class FileChannelTest {

    /**
     * 使用文件通道的方式复制文件
     * @param source 源文件
     * @param target 目标文件
     */
    public static void fileChannelCopy(File source, File target) {


        FileInputStream in = null;
        FileOutputStream out = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(target);
            inChannel = in.getChannel();//得到对应的文件通道
            outChannel = out.getChannel();//得到对应的文件通道
            inChannel.transferTo(0, inChannel.size(), outChannel);//连接两个通道，并且从inChannel通道读取，然后写入outChannel通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                inChannel.close();
                out.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void copyPath2Path(String source, String target){

        long startTime, elapsedTime;

        Path copy_from = Paths.get(source);
        Path copy_to = Paths.get(target);

        try {
            startTime = System.nanoTime();

            Files.copy(copy_from, copy_to, NOFOLLOW_LINKS);

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " ms");
        }catch (IOException e) {
            System.err.println(e);
        }
    }


    public static void main(String[] args) {

        // 83628ms

        //long begin = System.currentTimeMillis();

        String file = "C:\\Users\\Manzi\\Desktop\\123456.mp4";
        String target = "C:\\Users\\Manzi\\Desktop\\123456的复制.mp4";

        //File source = new File(file);
        //File dest = new File(target);

        System.out.println("--------start----------");

        //FileChannelTest.fileChannelCopy(source, dest);

        //long end = System.currentTimeMillis();
        //System.out.println("总耗时： " + (end - begin) / 1000 + "seconds");

        FileChannelTest.copyPath2Path(file, target);
    }
}
