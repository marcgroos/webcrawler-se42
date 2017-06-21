package crypto;

import util.StreamUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by marc on 6/21/17.
 */
public abstract class AES {

    public static final String AES_KEY_PATH = "resources/key.aes";
    public static final String AES_KEY = "KANWTQ04I4289YOX";

    public static SecretKey generateKey(){
        return new SecretKeySpec(AES_KEY.getBytes(), "AES");
    }

    public static byte[] encrypt(Serializable object, SecretKey key) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);

        return Serializer.serialize(new SealedObject(object, c));
    }

    public static Object decrypt(byte[] bytes, SecretKey key) throws ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        SealedObject sealedObject = (SealedObject) Serializer.deserialize(bytes);
        return sealedObject.getObject(key);
    }

//    public static void saveKey(SecretKey key) {
//        FileOutputStream fileOut = null;
//        ObjectOutputStream out = null;
//
//        try {
//            fileOut = new FileOutputStream("resources/key.aes");
//            out = new ObjectOutputStream(fileOut);
//            out.writeObject(key);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            StreamUtil.closeOutputStream(fileOut);
//            StreamUtil.closeOutputStream(out);
//        }
//
//    }
//
//    public static SecretKey loadKey() {
//        SecretKey key = null;
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(AES_KEY_PATH))) {
//
//            key = ois.readObject();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return key;
//    }
}
