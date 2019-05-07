package com.dam2.crynetenforcementlocallogistics;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class Task {
    private static final String TASKS = "Tasks";        //NON-NLS
    private static final String TASK = "Task";        //NON-NLS
    private static final String TITLE = "Title";        //NON-NLS
    private static final String DETAIL = "Detail";      //NON-NLS
    private static final String ns = null;

    private String title;
    private String detail;

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    private void setDetail(String detail) {
        this.detail = detail;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public static List<Task> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, "UTF-8");   //NON-NLS
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private static List<Task> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Task> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, TASKS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(TASK)) {
                entries.add(readTask(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private static Task readTask(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TASK);
        Task task = new Task();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case TITLE:
                    task.setTitle(readTitle(parser));
                    break;
                case DETAIL:
                    task.setDetail(readDetail(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return task;
    }
    // Processes title tags in the feed.
    private static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TITLE);
        return title;
    }

    // Processes title tags in the feed.
    private static String readDetail(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, DETAIL);
        String detail = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, DETAIL);
        return detail;
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
