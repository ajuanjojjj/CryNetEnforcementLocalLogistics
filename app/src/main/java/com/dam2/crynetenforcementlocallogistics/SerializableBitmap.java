package com.dam2.crynetenforcementlocallogistics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

class SerializableBitmap implements Serializable {
    private Bitmap bitmap;

    public SerializableBitmap(Bitmap mapa) {
        this.bitmap = mapa;
    }

    public static SerializableBitmap parseBase64(String photo) {
        byte[] data = Base64.decode(photo, Base64.DEFAULT);
        Bitmap foto = BitmapFactory.decodeByteArray(data, 0, data.length);
        return new SerializableBitmap(foto);
    }

    public Bitmap getMapa() {
        return bitmap;
    }


    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte[] bitmapBytes = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte[] bitmapBytes = byteStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}

