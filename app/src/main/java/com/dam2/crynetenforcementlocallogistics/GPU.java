package com.dam2.crynetenforcementlocallogistics;

import android.net.Uri;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Global Policy Updates
 */
@SuppressWarnings("ALL")
class GPU {
    private static final String GPUS = "GPUs";        //NON-NLS
    private static final String GPU = "GPU";        //NON-NLS
    private static final String URL = "Uri";        //NON-NLS
    private static final String ns = null;

    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    private void setUri(Uri uri) {
        this.uri = uri;
    }

    public static List<GPU> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private static List<GPU> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<GPU> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, GPUS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(GPU)) {
                entries.add(readGpu(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private static GPU readGpu(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, GPU);
        GPU task = new GPU();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case URL:
                    task.setUri(Uri.parse(readUri(parser)));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return task;
    }
    // Processes title tags in the feed.
    private static String readUri(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, URL);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, URL);
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
