package com.dongfang.advanced.concurrent.thread;

public class DownloadDemo extends Thread {
    private String url;
    private String fileName;

    public DownloadDemo(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    /**
     * 下载图片的线程执行体
     */
    @Override
    public void run() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.download(url, fileName);
    }

    public static void main(String[] args) {
        DownloadDemo thread1 = new DownloadDemo("https://github.com/changgyhub/leetcode_101/raw/master/LeetCode%20101%20-%20A%20LeetCode%20Grinding%20Guide%20(C%2B%2B%20Version).pdf", "D:\\ubuntu\\learn\\temp\\thread1.pdf");
        DownloadDemo thread2 = new DownloadDemo("https://github.com/changgyhub/leetcode_101/raw/master/LeetCode%20101%20-%20A%20LeetCode%20Grinding%20Guide%20(C%2B%2B%20Version).pdf", "D:\\ubuntu\\learn\\temp\\thread2.pdf");
        DownloadDemo thread3 = new DownloadDemo("https://github.com/changgyhub/leetcode_101/raw/master/LeetCode%20101%20-%20A%20LeetCode%20Grinding%20Guide%20(C%2B%2B%20Version).pdf", "D:\\ubuntu\\learn\\temp\\thread3.pdf");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
