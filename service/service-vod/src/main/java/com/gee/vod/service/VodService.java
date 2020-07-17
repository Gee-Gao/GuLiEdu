package com.gee.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gee
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String id);
}
