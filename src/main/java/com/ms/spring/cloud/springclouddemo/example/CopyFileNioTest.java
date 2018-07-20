package com.ms.spring.cloud.springclouddemo.example;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Sherlock
 * @Description:
 * @Date: Created in 15:44 2018/7/20
 * @Modified By:
 */
public class CopyFileNioTest {

    public void copyFileByNIO(File file, File newFile) {
        long startTime = System.currentTimeMillis();
        int status = 0;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            String destFilePath = newFile.getParent();
            File destPath = new File(destFilePath);
            if (!destPath.exists()) {
                destPath.mkdirs();
                System.out.println("---------该目录不存在，已创建成功: " + destFilePath);
            }
            fcin = new FileInputStream(file).getChannel();
            fcout = new FileOutputStream(newFile, true).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            while (fcin.read(bb) != -1) {
                bb.flip();
                fcout.write(bb);
                bb.clear();
            }
        } catch (IOException e) {
            System.out.println("---复制文件出错： " + e.getMessage());
        } finally {
            try {
                fcin.close();
                fcout.close();
            } catch (IOException e) {
                System.out.println("---文件复制失败： " + e.getMessage());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("--复制完成，总共耗时： " + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {

        // 83628ms

        String file = "C:\\Users\\Manzi\\Desktop\\123456.mp4";
        String target = "C:\\Users\\Manzi\\Desktop\\123456的复制.mp4";

        File source = new File(file);
        File dest = new File(target);

        System.out.println("--------start----------");

        CopyFileNioTest test = new CopyFileNioTest();
        test.copyFileByNIO(source, dest);
    }

}
