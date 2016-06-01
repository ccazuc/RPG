package com.mideas.rpg.v2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha1 {
	
	private static MessageDigest digest;
	
	public static final String hash(final String input) throws NoSuchAlgorithmException {
		if(digest == null) {
			digest = MessageDigest.getInstance("SHA1");
		}
        final byte[] result = digest.digest(input.getBytes());
        final StringBuilder sb = new StringBuilder();
        int i = -1;
        while(++i < result.length) {
            sb.append(Integer.toString((result[i]&0xff)+0x100, 16).substring(1));
        }
        return sb.toString();
    }
	
}
