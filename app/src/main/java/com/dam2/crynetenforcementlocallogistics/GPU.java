package com.dam2.crynetenforcementlocallogistics;

import android.net.Uri;
import android.util.JsonReader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Global Policy Updates
 */
@SuppressWarnings("ALL")
class GPU {
    private static final String GPU_DATE = "fecha";             //NON-NLS
    private static final String GPU_DESC = "description";       //NON-NLS
    private static final String GPU_URL = "URL";                //NON-NLS
    private static final String GPU_CRITICAL = "criticality";   //NON-NLS



    private Date date;
    private String description;
    private Uri uri;
    private int criticality;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Uri getUri() {
        return uri;
    }

    private void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCriticality() {
        return criticality;
    }

    public void setCriticality(int criticality) {
        this.criticality = criticality;
    }


    public static GPU parseJson(JsonReader json){
        try {
            GPU gpu = new GPU();
            while(json.hasNext()){
                String name = json.nextName();
                switch (name){
                    case GPU_DATE:
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            gpu.setDate(formatter.parse(json.nextString()));
                        } catch (Exception e) {
                            gpu.setDate(null);
                        }
                        break;
                    case GPU_DESC:
                        gpu.setDescription(json.nextString());
                        break;
                    case GPU_URL:
                        gpu.setUri(Uri.parse(json.nextString()));
                        break;
                    case GPU_CRITICAL:
                        gpu.setCriticality(json.nextInt());
                        break;
                    default:
                        json.skipValue();
                        break;
                }
            }
            return gpu;
        } catch (IOException ex) {
            return null;
        }
    }
}
