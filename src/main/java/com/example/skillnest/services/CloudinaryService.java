package com.example.skillnest.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.skillnest.dto.requests.SignatureRequest;
import com.example.skillnest.dto.responses.CloudinaryResponse;
import com.example.skillnest.dto.responses.ImageUploadResponse;
import com.example.skillnest.dto.responses.SignatureResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public SignatureResponse getSignature(String courseId, String sectionId, String lessonId) {
        try {
            Long timestamp = System.currentTimeMillis() / 1000;
            Map<String, Object> params = new HashMap<>();
            params.put("timestamp", timestamp);
            params.put("folder", "course/course_" + courseId +
                                "/section/section_" + sectionId +
                                "/lesson/lesson_" + lessonId
            );
            String signature = cloudinary.apiSignRequest(params, cloudinary.config.apiSecret);

            return SignatureResponse
                    .builder()
                    .timestamp(timestamp)
                    .signature(signature)
                    .cloudName(cloudinary.config.cloudName)
                    .apiKey(cloudinary.config.apiKey)
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException("Lỗi tạo chữ ký Cloudinary", e);
        }
    }

    public ImageUploadResponse uploadImage(MultipartFile file, String folder, String publicId) {
        try {
            Map uploadRes = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "public_id", publicId,
                            "overwrite", true
                    )
            );

            return ImageUploadResponse.builder()
                    .imageUrl(uploadRes.get("secure_url").toString())
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }
}
