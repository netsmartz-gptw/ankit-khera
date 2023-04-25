package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.User;
import com.g1app.engine.repositories.UserRepository;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.services.FileStorageService;
import com.g1app.engine.userdirectory.response.ResponseUploadFile;
import com.g1app.engine.utils.UrlConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository repository;
   

//    @PostMapping("/uploadFile")
//    public ResponseUploadFile uploadFile(@RequestParam("file") MultipartFile file) {
//        String fileName = fileStorageService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//        return new ResponseUploadFile(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
//    }



    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

        /**
         *
         * This API is for uploading user profile Image.
         *
         * */
        @PostMapping(value = "/uploadFile" ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<ResponseUploadFile> uploadImage(@RequestParam("file") MultipartFile file,
                                                              @RequestParam("customerID") String customerID,
                                                              @RequestHeader("Authorization") String token) {
            ResponseUploadFile responseUploadFile = new ResponseUploadFile();
            if(file.isEmpty() || customerID.isEmpty() || token.isEmpty()){
                responseUploadFile.message = ResponseStatusMessages.PLEASE_TRY_AGAIN.getValue();
                responseUploadFile.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseUploadFile, HttpStatus.OK);
            }
            String bearer = tokenProvider.resolveToken(token);

            if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
                String parentCustomerId = tokenProvider.getUsername(bearer);
                String childCustomerID = customerID;
                User user = repository.findByCustomerID(UUID.fromString(childCustomerID));

                String fileName = fileStorageService.storeFile(file,childCustomerID);
                responseUploadFile.fileName = fileName;
                responseUploadFile.fileType = file.getContentType();
                responseUploadFile.size = file.getSize();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(UrlConstant.DOWNLOAD_FILES)
                        .path(fileName)
                        .toUriString();
                responseUploadFile.fileDownloadUri = fileDownloadUri;
                user.setProfileImage(fileDownloadUri);
                repository.save(user);
                responseUploadFile.message = ResponseStatusMessages.PROFILE_IMAGE_UPLOADED.getValue();
                responseUploadFile.statusCode = ResponseStatusCode.SUCCESS.getValue();
                return new ResponseEntity<>(responseUploadFile, HttpStatus.OK);

            }else{
                responseUploadFile.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
                responseUploadFile.statusCode = ResponseStatusCode.SUCCESS.getValue();
                return new ResponseEntity<>(responseUploadFile, HttpStatus.NOT_FOUND);
            }
        }

//    @PostMapping("/uploadMultipleFiles")
//    public List<ResponseUploadFile> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadImage(file))
//                .collect(Collectors.toList());
//    }

}