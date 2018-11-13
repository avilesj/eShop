package com.javiles.eshop.minio;

import com.javiles.eshop.minio.repositories.MinioRepository;
import com.javiles.eshop.minio.services.MinioStorageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class MinioStorageServiceTests
{
    @Autowired
    private MinioStorageService minioStorageService;
    @Autowired
    private MinioRepository minioRepository;
    @Autowired
    private MinioProperties minioProperties;

    private MockMultipartFile mockMultipartFile;

    private final String TEST_FILE_NAME = "green-tshirt";
    private String serviceFileName;
    private String BUCKET_URL;

    @Before
    public void init() throws Exception
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Sphinx of black quartz, judge my vow: Used by Adobe InDesign to display font samples. ");
        builder.append("(29 letters)\n");
        builder.append("Jackdaws love my big sphinx of quartz: Similarly, used by Windows XP for some fonts. ");
        builder.append("(31 letters)\n");
        builder.append("Pack my box with five dozen liquor jugs: According to Wikipedia, this one is used on ");
        builder.append("NASAs Space Shuttle. (32 letters)\n");
        builder.append("The quick onyx goblin jumps over the lazy dwarf: Flavor text from an Unhinged Magic Card. ");
        builder.append("(39 letters)\n");
        builder.append("How razorback-jumping frogs can level six piqued gymnasts!: Not going to win any brevity ");
        builder.append("awards at 49 letters long, but old-time Mac users may recognize it.\n");
        builder.append("Cozy lummox gives smart squid who asks for job pen: A 41-letter tester sentence for Mac ");
        builder.append("computers after System 7.\n");
        builder.append("A few others we like: Amazingly few discotheques provide jukeboxes; Now fax quiz Jack! my ");
        builder.append("brave ghost pled; Watch Jeopardy!, Alex Trebeks fun TV quiz game.\n");
        builder.append("---\n");


        // Create a InputStream for object upload.
        ByteArrayInputStream inputStream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
        this.mockMultipartFile = new MockMultipartFile("filename.png", "originalfile.png", "image/png", inputStream);

        this.BUCKET_URL = minioProperties.getUrl() + "/" + minioProperties.getBucket() + "/";
    }

    @After
    public void terminate()
    {
        minioRepository.deleteFileFromBucket(serviceFileName);
    }

    @Test
    public void shouldStoreFileWithUniqueName() throws Exception
    {
        this.serviceFileName = minioStorageService.storeFile(mockMultipartFile, TEST_FILE_NAME);

        boolean isFileLengthHigherThanZero = serviceFileName.length() > 0;
        boolean containsTestFileName = this.serviceFileName.contains(TEST_FILE_NAME);

        URL url = new URL(BUCKET_URL + serviceFileName);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();

        assertTrue(isFileLengthHigherThanZero);
        assertTrue(containsTestFileName);
        assertEquals(200, responseCode);
    }

    @Test
    public void shouldDeleteFile() throws Exception
    {
        this.serviceFileName = minioStorageService.storeFile(mockMultipartFile, TEST_FILE_NAME);

        boolean isFileLengthHigherThanZero = serviceFileName.length() > 0;
        boolean containsTestFileName = this.serviceFileName.contains(TEST_FILE_NAME);

        URL url = new URL(BUCKET_URL + serviceFileName);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();

        //Verify successful creation of the file
        assertTrue(isFileLengthHigherThanZero);
        assertTrue(containsTestFileName);
        assertEquals(200, responseCode);

        //Begin file deletion
        String fileToDelete = this.serviceFileName.replace(minioProperties.getUrl(), "");
        fileToDelete = fileToDelete.replace(minioProperties.getBucket(), "");
        fileToDelete = fileToDelete.replace("/", "");

        minioStorageService.deleteFile(fileToDelete);

        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        responseCode = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();
        //Verify successful deletion of the file
        assertEquals(404, responseCode);

    }

    @Test
    public void shouldRetrieveFileFullUrl()
    {
        this.serviceFileName = minioStorageService.storeFile(mockMultipartFile, TEST_FILE_NAME);
        String url = minioStorageService.getFileUrl(this.serviceFileName);
        assertEquals(url, this.BUCKET_URL + this.serviceFileName);
    }
}
