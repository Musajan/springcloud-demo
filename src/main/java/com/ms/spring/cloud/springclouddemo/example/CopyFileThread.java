package com.ms.spring.cloud.springclouddemo.example;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Author: Sherlock
 * @Description:
 * @Date: Created in 14:28 2018/7/20
 * @Modified By:
 */
public class CopyFileThread extends Thread {

    //分块数
    private static int blockCount = 4;

    private String srcPath;// 源文件地址
    private String destPath;// 目标文件地址
    private long start, end;// start起始位置， end结束位置

    public CopyFileThread(String srcPath, String destPath, long start, long end) {
        this.srcPath = srcPath;
        this.destPath = destPath;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            long beginTime = System.currentTimeMillis();
            System.out.println("开始时间： " + beginTime);
            // 创建只读的随机访问文件
            RandomAccessFile in = new RandomAccessFile(srcPath, "r");
            // 创建可读可写的随机访问文件
            RandomAccessFile out = new RandomAccessFile(destPath, "rw");
            // 将输入跳到指定位置
            in.seek(start);
            // 从指定位置开始写
            out.seek(start);
            // 文件输入通道
            FileChannel inChannel = in.getChannel();
            // 文件输出通道
            FileChannel outChannel = out.getChannel();
            // 锁住需要操作的区域， false代表锁住
            FileLock lock = outChannel.lock(start, (end - start), false);
            // 将字节从此通道的文件传输到给定的可写入字节的输出通道
            inChannel.transferTo(start, (end - start), outChannel);
            lock.release();// 释放锁
            out.close();// 从里到外关闭文件
            in.close();// 关闭文件
            //计算耗时
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "_"
                    + "结束时间： " + endTime + "_" + "共用时： "
                    + (endTime - beginTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        long begin = System.currentTimeMillis();

        String file = "C:\\Users\\Manzi\\Desktop\\123456.mp4";
        String target = "C:\\Users\\Manzi\\Desktop\\123456的复制.mp4";

        File source = new File(file);
        long len = source.length();// 文件总长度
        long oneLen = len / blockCount;// 一块

        System.out.println("--------start----------");

        for (int i = 0; i < blockCount - 1; i++) {
            new CopyFileThread(file, target, oneLen * i, oneLen * (i + 1)).start();
        }

        // 文件长度不能整除的部分放到最后一段处理
        new CopyFileThread(file, target, oneLen * (blockCount - 1), len).start();

        long end = System.currentTimeMillis();
        System.out.println("------结束: " + (end - begin) + "ms");
    }
}