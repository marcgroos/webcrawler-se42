package util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by marc on 6/21/17.
 */
public abstract class StreamUtil {

    public static void closeOutputStream(OutputStream out) {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
