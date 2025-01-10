package org.rossijr.awss3crud.controller;

import org.rossijr.awss3crud.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    /**
     * Uploads a file to the S3 bucket.
     * <p>Important to note:</p>
     *     <ul>
     *         <li>When uploading a file, it is important to validate the file size, type, and content.</li>
     *         <li>It is also important to handle the file name, avoiding conflicts and security issues.</li>
     *         <li>Pay attention in the key name (in Postman in the form-data tab), in this case it must be called file.</li>
     *     </ul>
     * @param file the file to be uploaded.
     * @return a {@link ResponseEntity} with a success message.
     */
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = s3Service.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully with key: " + fileName);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Object> deleteFile(@PathVariable String key) {
        s3Service.deleteFile(key);
        return ResponseEntity.noContent().build();
    }

    /**
     * Downloads a file from the S3 bucket.
     * <p>Important to note:</p>
     *    <ul>
     *        <li>The file, when using a browser, the "Content-Disposition" header will make the browser download the file instead of opening it.
     *        If you want to open it, you can remove the "Content-Disposition" header (make sure to exhibit in a proper way in the browser (for example PDF, images, etc.))</li>
     *        </li
     *     </ul>
     * @param key the key of the file to be downloaded.
     * @return a {@link ResponseEntity} with the file content.
     */
    @GetMapping("/{key}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String key) {
        byte[] fileContent = s3Service.downloadFile(key);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + key + "\"")
                .body(fileContent);
    }

    @PutMapping("/{key}")
    public ResponseEntity<String> updateFile(@PathVariable String key, @RequestParam("file") MultipartFile file) {
        String updatedKey = s3Service.updateFile(key, file);
        return ResponseEntity.ok("File updated successfully with key: " + updatedKey);
    }
}
