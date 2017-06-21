package util;

import javafx.scene.media.VideoTrack;
import entities.AudioEntity;
import entities.ImageEntity;
import entities.ResourceEntity;
import entities.VideoEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * @author Yannic on 8-6-17.
 */
public class ResourceManager {

    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());

    private String resourceDir;

    public ResourceManager() {
        resourceDir = System.getProperty("user.dir") + "/core/src/main/resources/foundFiles";
    }

    public AudioEntity getAudioResource(URL url) {
        try {
            URLConnection connection = url.openConnection();
            AudioEntity audioEntity = new AudioEntity(url.getFile(), url.toString(), connection.getContentLength());
            String contentType = connection.getContentType();
            return audioEntity;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not get connection: " + e.getMessage());
        }
        return null;
    }

    public ImageEntity getImageResource(URL url) {
        try {
            //get connection (
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            int fileSize = urlConnection.getContentLength();
            //skip hier als het bestand te groot is.
            BufferedImage image = ImageIO.read(urlConnection.getInputStream());
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();
            ImageEntity imageEntity = new ImageEntity(url.getFile(), url.toString(), fileSize, imageWidth, imageHeight);
            return imageEntity;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to url: " + e.getMessage());
        }
        return null;
    }

    public VideoEntity getVideoResource(URL url) {
        LOGGER.log(Level.FINE, "Starting unfinished method");
        try {
            Object vid = url.getContent();
            if(vid.getClass().isInstance(VideoTrack.class)){
                System.out.println(vid.getClass().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        VideoEntity entity = new VideoEntity();
        return null;
    }

    public ResourceEntity getUnknownResource(URL url) {
        return null;
    }

    public Boolean saveResourceFile(ResourceEntity resource) {
        String filename = resource.getURL().getHost() + "_" + resource.getName();
        OutputStream fileOut = null;
        BufferedInputStream buffIn = null;
        try {
            //prepare download
            File file = new File(resourceDir + filename);
            file.mkdirs();
            fileOut = new FileOutputStream(file);
            buffIn = new BufferedInputStream(resource.getURL().openStream());
            //download the file.
            int i;
            while ((i = buffIn.read()) != -1) {
                fileOut.write(i);
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while downloading file: " + filename + "\n" + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeQuietly(fileOut);
            closeQuietly(buffIn);
        }
        LOGGER.log(Level.FINEST, "Saved file to: " + resourceDir);
        return true;
    }
}
