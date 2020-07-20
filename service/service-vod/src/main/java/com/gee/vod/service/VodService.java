package com.gee.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Gee
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String id);

    void removeMoreAliVideo(List<String> videoIdList);
}
