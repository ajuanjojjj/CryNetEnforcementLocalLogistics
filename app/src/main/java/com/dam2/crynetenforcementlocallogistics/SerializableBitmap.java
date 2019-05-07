package com.dam2.crynetenforcementlocallogistics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

class SerializableBitmap implements Serializable {
    private final Bitmap mapa;

    public SerializableBitmap(Bitmap mapa) {
        this.mapa = mapa;
    }

    public static SerializableBitmap parseBase64(String photo) {
        byte[] data = Base64.decode(photo, Base64.DEFAULT);
        Bitmap foto = BitmapFactory.decodeByteArray(data, 0, data.length);
        return new SerializableBitmap(foto);
    }

    public Bitmap getMapa() {
        return mapa;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mapa.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();

        out.writeInt(byteArray.length);
        out.write(byteArray);

    }

    private SerializableBitmap readObject(java.io.ObjectInputStream in) throws IOException {
        int bufferLength = in.readInt();

        byte[] byteArray = new byte[bufferLength];

        int pos = 0;
        do {
            int read = in.read(byteArray, pos, bufferLength - pos);

            if (read != -1) {
                pos += read;
            } else {
                break;
            }

        } while (pos < bufferLength);

        return new SerializableBitmap(BitmapFactory.decodeByteArray(byteArray, 0, bufferLength));

    }
}
