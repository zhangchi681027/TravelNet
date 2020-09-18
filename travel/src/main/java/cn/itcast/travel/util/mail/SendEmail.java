package cn.itcast.travel.util.mail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by wly on 2018/3/7.
 */
public class SendEmail {
    public static void sendEmailMessage(String email,String validateCode) {
       try {
           String host = "smtp.163.com";   //发件人使用发邮件的电子信箱服务器
           String from = "18818278901@163.com";    //发邮件的出发地（发件人的信箱）
           String to = email;   //发邮件的目的地（收件人信箱）
           // Get system properties
           Properties props = System.getProperties();

           // Setup mail server
           props.put("mail.smtp.host", host);

           // Get session
           props.put("mail.smtp.auth", "true"); //这样才能通过验证

           MyAuthenticator myauth = new MyAuthenticator(from, "KRKMGMEBKTWUHJOS");
           Session session = Session.getDefaultInstance(props, myauth);

//    session.setDebug(true);

           // Define message
           MimeMessage message = new MimeMessage(session);


           // Set the from address
           message.setFrom(new InternetAddress(from));

           // Set the to address
           message.addRecipient( Message.RecipientType.TO,
                   new InternetAddress(to));

           // Set the subject
           message.setSubject("旅游网激活邮件通知");

           // Set the content
           message.setContent( "感谢您注册旅游网帐号！请于24小时内<a href=\"http://localhost:8080/travel/activecodeServlet?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">点击激活</a>","text/html;charset=gb2312");
           message.saveChanges();

           Transport.send(message);

           System.out.println( "send validateCode to " + email );
       }catch (Exception e){

           System.out.println( "Send Email Exception:"+e.getMessage() );
       }

    }
}
