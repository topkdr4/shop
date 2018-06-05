package ru.vetoshkin.store.mail.dao;


import ru.vetoshkin.store.mail.Template;
import ru.vetoshkin.store.settings.Settings;
import ru.vetoshkin.store.user.dao.UserStorage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class MailService {
    private static final Properties props = System.getProperties();
    private static final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private static final ExecutorService executors = Executors.newFixedThreadPool(10);
    private static Session session = null;
    private static Address addressFrom = null;


    public static void init() {
        Settings settings = Settings.getInstance();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", settings.getHost());
        props.put("mail.smtp.port", settings.getPort());

        final String username = settings.getEmail();
        final String password = settings.getPassword();

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            addressFrom = new InternetAddress(username);
            poolInit();
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }



    /**
     * Отправка письма
     */
    public static void send(String to, String subjet, final String text) {
        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(addressFrom);
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //тема сообщения
            message.setSubject(subjet);
            //текст
            message.setContent(text, "text/html; charset=utf-8");

            queue.offer(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    /**
     * Инициализация пула рассылки
     */
    private static void poolInit() {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                try {
                    while (true) {
                        Transport.send(queue.take());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }



    /**
     * Разослать всем пользговатям
     */
    public static void sendAll(Template template) {
        List<String> emails = UserStorage.getAllEmailsForSend();
        for (String email : emails) {
            send(email, template.getDesc(), template.getValue());
        }
    }
}
