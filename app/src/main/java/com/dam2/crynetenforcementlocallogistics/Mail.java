package com.dam2.crynetenforcementlocallogistics;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
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
    private static final String MAILS = "Mails";        //NON-NLS
    private static final String MAIL = "Mail";          //NON-NLS
    private static final String BODY = "Body";          //NON-NLS
    private static final String SUBJECT = "Subject";    //NON-NLS

    private static final String ns = null;
    private static final String LOCKHART_CRYNET_NET = "lockhart@crynet.net";

    private String subject;
    private String body;
    private User remitente;

    public User getRemitente() {
        return remitente;
    }

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

    private void setRemitente(User remitente) {
        this.remitente = remitente;
    }

    public static List<Mail> parse(InputStream in, Resources res) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, "UTF-8");   //NON-NLS
            parser.nextTag();
            return readFeed(parser, res);
        } finally {
            in.close();
        }
    }

    private static List<Mail> readFeed(XmlPullParser parser, Resources res) throws XmlPullParserException, IOException {
        List<Mail> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, MAILS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(MAIL)) {
                entries.add(readMail(parser, res));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private static Mail readMail(XmlPullParser parser, Resources res) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, MAIL);
        Mail mail = new Mail();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case SUBJECT:
                    mail.setSubject(readSubject(parser));
                    break;
                case BODY:
                    mail.setBody(readBody(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
            User remitente = new User();
            remitente.setMail(LOCKHART_CRYNET_NET);
            remitente.setUsername("LockHart"); //NON-NLS
            remitente.setIcono(BitmapFactory.decodeResource(res, R.drawable.icono_test));
            mail.setRemitente(remitente);
        }
        return mail;
    }
    // Processes title tags in the feed.
    private static String readSubject(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, SUBJECT);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, SUBJECT);
        return title;
    }

    // Processes title tags in the feed.
    private static String readBody(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, BODY);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, BODY);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
