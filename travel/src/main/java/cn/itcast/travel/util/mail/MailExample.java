package cn.itcast.travel.util.mail;

/**
 * Created by wly on 2018/3/7.
 */

public class MailExample {

    public static void main (String args[]) throws Exception {
        String email = "18818278901@163.com";
        String validateCode = "hahahahahaha";
        SendEmail.sendEmailMessage(email,validateCode);

    }
}
