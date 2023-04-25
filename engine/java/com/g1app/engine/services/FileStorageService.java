package com.g1app.engine.services;

import com.g1app.engine.exceptions.FileStorageException;
import com.g1app.engine.exceptions.MyFileNotFoundException;
import com.g1app.engine.utils.FileStorageProperties;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String childCustomerID) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //boolean value = imageFile(fileName);
        try {
            boolean value1  = checkJPEG(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int dotIndex = fileName.lastIndexOf('.');
        String str = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String newFileName = fileName.replace(str,childCustomerID);

        try {
            // Check if the file's name contains invalid characters
            if(newFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + newFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + newFileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public static boolean imageFile(String fileName)
    {
        // Regex to check valid image file extension.
        String regex = "([^\\s]+(\\.(?i)(jpe?g|png))$)";

        Pattern p = Pattern.compile(regex);
        if (fileName == null) {
            return false;
        }
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    public boolean checkJPEG(String fileName) throws IOException {

        String newfileName = fileName.toUpperCase();

        boolean extension = newfileName.endsWith(".JPG") || newfileName.endsWith(".JPEG") || newfileName.endsWith(".PNG");
        if (!extension) {
            return false;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(newfileName);
            byte[] magic = new byte[3];
            int count = in.read(magic);
            if (count < 3) return false;
            return magic[0] == 0xFF && magic[1] == 0xD8 && magic[2] == 0xFF;
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ex) {}
        }
    }
}
