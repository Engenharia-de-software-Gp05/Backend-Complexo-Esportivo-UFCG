package com.ufcg.es5.BackendComplexoEsportivoUFCG.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInternalErrorServerException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Component
@Profile("!test")
public class S3Uploader {

    private static final Set<String> VALID_PICTURE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "png", "jpeg"));

    private final AmazonS3 s3Client;

    private @Value("${aws.s3.bucketName}") String bucketName;
    private @Value("${aws.s3.picturesFolder}") String profilePicturesFolder;
    private @Value("${aws.s3.courtFolder}") String courtImagesFolder;
    private @Value("${aws.s3.endpointUrl}") String endpointUrl;

    @Autowired
    public S3Uploader(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadProfilePicture(MultipartFile picture) {
        this.checkPictureExtension(picture);

        //String bucket = this.getFullBucketName(this.profilePicturesFolder);
        String fileName = this.profilePicturesFolder + "/" + this.createFilename(picture);

        this.uploadFile(this.bucketName, fileName, picture);

        return this.getPictureUrl(this.profilePicturesFolder, fileName);
    }

    public String uploadCourtImage(MultipartFile courtImage) {
        this.checkPictureExtension(courtImage);

        // String bucket = this.getFullBucketName(this.courtImagesFolder);
        String fileName = this.courtImagesFolder + "/" + this.createFilename(courtImage);

        this.uploadFile(this.bucketName, fileName, courtImage);

        return this.getPictureUrl(this.courtImagesFolder, fileName);
    }

    private String getFullBucketName(String folder) {
        return this.bucketName + "/" + folder;
    }

    private String getPictureUrl(String folder, String fileName) {
        return String.format("https://%s.%s/%s/%s", this.bucketName, this.endpointUrl, folder, fileName);
    }

    private void uploadFile(String bucket, String fileName, MultipartFile multipartFile) {
        File file = this.multipartToFile(multipartFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file);
        this.s3Client.putObject(putObjectRequest);
        file.deleteOnExit();
    }

    private File multipartToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
            return file;
        } catch (IOException e) {
            throw new SaceInternalErrorServerException(e.getMessage());
        }
    }

    private String createFilename(MultipartFile multipartFile) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ", "_");
    }

    private String getFileExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName != null) {
            String[] splittedFileName = fileName.split("\\.");
            return splittedFileName[splittedFileName.length - 1];
        }
        return null;
    }

    private void checkPictureExtension(MultipartFile picture) {
        String extension = this.getFileExtension(picture);
        if (!VALID_PICTURE_EXTENSIONS.contains(extension)) {
            throw new SaceInvalidArgumentException("");
        }
    }
}