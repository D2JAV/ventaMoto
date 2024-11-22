/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ASUS
 */
public class Encriptar {
        public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";

    private static String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    public static String getStringMessageDigest(String message, String algorithm) {


        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    }


    public static String getContrasenia(String cOfuscar) {
        String trash = "trash";
        String textoOfuscado = "";

        textoOfuscado = getStringMessageDigest(cOfuscar.toLowerCase() + trash, "SHA-1");
        textoOfuscado = getStringMessageDigest(textoOfuscado + trash, "SHA-256");

        return textoOfuscado;
    }

    public static String getCodigoVerificacion(String cOfuscar) {
        String trash = "kekw";
        String textoOfuscado = "";

        textoOfuscado = getStringMessageDigest(cOfuscar.toLowerCase() + trash, "SHA-1");
        textoOfuscado = getStringMessageDigest(textoOfuscado + trash, "SHA-256");

        return textoOfuscado;
    }
}
