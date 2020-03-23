package application.test;

public class StringTest {
    public static void main(String[] args) {
        String ss = "https://www.dingdiann.com/ddk153653/";
        String substring = "";
        if (ss.contains(".com")){
             substring = ss.substring(0, ss.indexOf("com/"))+"com";
        }
        if (ss.contains(".cn")){
             substring = ss.substring(0, ss.indexOf("cn/"))+"cn";
        }

        System.out.println(substring);
    }
}
