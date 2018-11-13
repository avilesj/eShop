package com.javiles.eshop.minio;


import com.javiles.eshop.minio.repositories.MinioRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class MinioRepositoryTests
{
    @Autowired
    private MinioRepository minioRepository;
    @Autowired
    private MinioProperties minioProperties;

    private ByteArrayInputStream object;

    private final String FILENAME = "testfile.txt";
    private String BUCKET_URL;

    @Before
    public void init()
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
        this.object = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
        this.BUCKET_URL = minioProperties.getUrl() + "/" + minioProperties.getBucket() + "/";
    }

    @After
    public void terminate()
    {
        minioRepository.deleteFileFromBucket(FILENAME);
    }

    @Test
    public void shouldCreateFileOnMinioBucketAndReturnItsUrl() throws Exception
    {
        minioRepository.storeFileInBucket(this.object, (long) this.object.available(), FILENAME, "text/plain");

        URL url = new URL(BUCKET_URL + FILENAME);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();

        assertEquals(200, responseCode);
    }

    @Test
    public void shouldRemoveFileFromMinioBucket() throws Exception
    {
        minioRepository.storeFileInBucket(this.object, (long) this.object.available(), FILENAME, "text/plain");
        minioRepository.deleteFileFromBucket(FILENAME);

        URL url = new URL(BUCKET_URL + FILENAME);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        httpURLConnection.disconnect();

        assertEquals(404, responseCode);
    }
}
