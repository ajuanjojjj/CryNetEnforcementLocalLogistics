package com.dam2.crynetenforcementlocallogistics;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


class Mail implements Serializable {
    //XML
    private static final String MAIL_ID = "id";                 //NON-NLS
    private static final String MAIL_SUBJECT = "subject";       //NON-NLS
        private static final String MAIL_BODY = "body";         //NON-NLS
    private static final String MAIL_SENDER = "sender";         //NON-NLS
    private static final String MAIL_RECIEVER = "reciever";     //NON-NLS

    private int id;
    private String subject;
    private String body;
    private Employee sender;
    private Employee reciever;


    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    private void setSubject(String subject) {
        this.subject = subject;
    }

    private void setBody(String body) {
        this.body = body;
    }

    public Employee getReciever() {
        return reciever;
    }

    public void setReciever(Employee reciever) {
        this.reciever = reciever;
    }

    public Employee getSender() {
        return sender;
    }

    public void setSender(Employee sender) {
        this.sender = sender;
    }

    public static Mail parseJson(JsonReader json) {
        try {
            Mail mail = new Mail();
            while (json.hasNext()) {
                String name = json.nextName();
                switch (name) {
                    case MAIL_ID:
                        mail.id = json.nextInt();
                        break;
                    case MAIL_SUBJECT:
                        mail.setSubject(json.nextString());
                        break;
                    case MAIL_BODY:
                        mail.setBody(json.nextString().replace("<br/>", "\n          "));
                        break;
                    case MAIL_SENDER:
                        json.beginObject();
                        Employee empl = Employee.parseJson(json);
                        json.endObject();
                        mail.setSender(empl);
                        break;
                    case MAIL_RECIEVER:
                        json.beginObject();
                        Employee emp = Employee.parseJson(json);
                        json.endObject();
                        mail.setReciever(emp);
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            return mail;
        } catch (IOException ex) {
            return null;
        }
    }
}
