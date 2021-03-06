/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * @copyright Copyright (C) 2015 (20/10/15) ) Neblina Software. Derechos reservados.
 * @license Licencia BSD; vea LICENSE.txt
 */

package com.neblina.balero.util;

import com.neblina.balero.domain.Media;
import com.neblina.balero.domain.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaManager {

    private static final Logger log = LogManager.getLogger(MediaManager.class.getName());

    /**
     * Get Relative Path Of Specific Folder
     * @author Anibal Gomez
     * @return
     */
    public String getResourcePath() {
        return System.getProperty("user.dir") +
                File.separator + "src" + File.separator  +
                "main" + File.separator  + "resources" + File.separator;
    }

    /**
     * Gets Uploads's Relative Path Folder
     * @return
     */
    public String getResourceUploadsPath() {
        return getResourcePath() +
                "static" + File.separator + "images" + File.separator +
                "uploads" + File.separator;
    }

    public String getResourceTemplatesPath() {
        return getResourcePath() +
                "templates" + File.separator;
    }

    /**
     * Create Image Gallery Info Object
     * @author Anibal Gomez
     * Based on:
     * http://stackoverflow.com/questions/6300675/java-mixed-arraylists
     * http://stackoverflow.com/questions/672916/how-to-get-image-height-and-width-using-java
     * @return
     * @throws IOException
     */
    public List<Media> retrieveImageGalleryList() throws IOException {
        List<Media> list = new ArrayList<>();
        // Java 8 List Directory
        Files.walk(Paths.get(getResourceUploadsPath())).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    // |jpg|png|jpeg|bmp|gif|
                    if(filePath.toString().endsWith(".jpg") ||
                            filePath.toString().endsWith(".png") ||
                            filePath.toString().endsWith(".jpeg") ||
                            filePath.toString().endsWith(".bmp") ||
                            filePath.toString().endsWith(".gif")) {
                        // Image info (width, height, etc)
                        BufferedImage bimg = ImageIO.read(new File(filePath.toString()));
                        // Build FileGallery Object
                        // Merge Objects
                        Media fileGallery = new Media();
                        fileGallery.setFileName(filePath.getFileName().toString());
                        fileGallery.setWidth(bimg.getWidth());
                        fileGallery.setHeight(bimg.getHeight());
                        // Add object
                        list.add(fileGallery);
                        // Clean
                        bimg = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return list;
    }

    public List<Template> retrieveTemplates() throws IOException {
        List<Template> list = new ArrayList<>();
        File dir= new File(getResourceTemplatesPath());
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(!file.getName().equals("authorized")) {
                    Template template = new Template();
                    template.setName(file.getName());
                    list.add(template);
                }
            }
        }
        return list;
    }

    public void deleteResourceFile(String fileName) {
        try {
            File file = new File(getResourceUploadsPath() + fileName);
            if(!file.exists()) {
                throw new Exception("File Do Not Exists!" + file);
            }
            file.delete();
            log.debug("Deleting: " + file);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
