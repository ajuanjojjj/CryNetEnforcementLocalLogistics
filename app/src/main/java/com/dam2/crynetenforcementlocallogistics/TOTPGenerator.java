package com.dam2.crynetenforcementlocallogistics;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class TOTPGenerator {
    private static final int TIME_STEP_SECONDS = 30;
    /**
     * Generates a TOTP key from the key time specified
     */
    public String generateCurrentNumber(byte[] key, long currentTimeMillis) throws GeneralSecurityException {
        byte[] data = new byte[8];
        long value = currentTimeMillis / 1000 / TIME_STEP_SECONDS;
        for (int i = 7; value > 0; i--) {
            data[i] = (byte) (value & 0xFF);
            value >>= 8;
        }

        // encrypt the data with the key and return the SHA1 of it in hex
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1"); //NON-NLS
        // if this is expensive, could put in a thread-local
        Mac mac = Mac.getInstance("HmacSHA1");//NON-NLS
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        // take the 4 least significant bits from the encrypted string as an offset
        int offset = hash[hash.length - 1] & 0xF;

        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = offset; i < offset + 4; ++i) {
            truncatedHash <<= 8;
            // get the 4 bytes at the offset
            truncatedHash |= (hash[i] & 0xFF);
        }
        // cut off the top bit
        truncatedHash &= 0x7FFFFFFF;

        // 0111 1111 1111 1111 1111 1111 1111 1111
        // 0010 0100 1101 1101 1011 0010 1111 0101

        // the token is then the last 6 digits in the number
        truncatedHash %= 1000000;

        return truncatedHash + "";
    }

}
