package org.eisen.file.controller;

import org.eisen.bios.fileopreate.CopyFile;
import org.eisen.file.component.FileCheck;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

/**
 * @Author Eisen
 * @Date 2018/12/12 20:28
 * @Description:
 **/
@RestController
public class FileController {

    static String filePath = "D:/path/";

    static {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request) throws IOException {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!cmr.isMultipart(request)) {
            return 0;
        }
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
        Iterator ite = mreq.getFileNames();
        MultipartFile mFile;
        while (ite.hasNext()) {
            mFile = mreq.getFile(ite.next().toString());
            FileInputStream in = (FileInputStream) mFile.getInputStream();

            if (mFile == null || mFile.isEmpty()) {
                continue;
            }

            String path = filePath + mFile.getOriginalFilename();
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            try {
                mFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }

        }
        return 1;
    }


    @RequestMapping("/uploadCloud")
    public Object uploadCloud(HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!cmr.isMultipart(request)) {
            return 0;
        }
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
        Iterator ite = mreq.getFileNames();
        MultipartFile mFile;
        while (ite.hasNext()) {
            mFile = mreq.getFile(ite.next().toString());
            if (mFile == null || mFile.isEmpty()) {
                continue;
            }
            FileInputStream in = (FileInputStream) mFile.getInputStream();
            String sha_512 = FileCheck.getCheckValue(in, FileCheck.SHA_512);
            String dirPath = filePath + sha_512.substring(0, 4) + "/" + sha_512.substring(4, 8) + "/";
            String path = dirPath + sha_512;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            CopyFile.copyFile(in, new FileOutputStream(file));
        }
        return 1;
    }

    public static void main(String[] args) {
        String s = "012345678";
        System.out.println(s.substring(0, 4));
        System.out.println(s.substring(4, 8));
    }


}
