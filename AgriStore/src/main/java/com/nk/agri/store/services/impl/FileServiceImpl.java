package com.nk.agri.store.services.impl;

import com.nk.agri.store.exceptions.BadApiRequest;
import com.nk.agri.store.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename :  " + originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWIthFileName = path + File.separator + fileNameWithExtension;
        //  File.separator -> / {it's a slash}

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            //Save file
            File folder = new File(path);
            if (!folder.exists()) {
                //create the folder
                folder.mkdirs();
            }

            //upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathWIthFileName));
            return fileNameWithExtension;

        } else {
            throw new BadApiRequest("File with this extension " + extension + " not allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path+File.separator+name;

        return new FileInputStream(fullPath);
    }
}
