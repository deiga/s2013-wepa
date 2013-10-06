package wad.familyalbum;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;
import wad.familyalbum.data.Image;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
@WebAppConfiguration
public class FamilyAlbumControllerTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc controllerMock;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        controllerMock = webAppContextSetup(waco).build();
        mapper = new ObjectMapper();
    }

    @Test
    @Points("W5E06.1")
    public void testPostNewImageAndDownloadIt() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-1.jpg");
        String contentType = "image/jpeg";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);

        downloadImage(imageId, contentType, file.length());
        matchResponseData(file, imageId, contentType, file.length());
    }

    @Test
    @Points("W5E06.1")
    public void testPostAnotherNewImageAndDownloadIt() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-2.png");
        String contentType = "image/png";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);
        downloadImage(imageId, contentType, file.length());
        matchResponseData(file, imageId, contentType, file.length());
    }

    @Test
    @Points("W5E06.2")
    public void testPostNewImageAndDownloadItAsAttachment() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-1.jpg");
        String contentType = "application/octet-stream";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);
        downloadImageAsAttachment(imageId, contentType, file.getName(), file.length());
        matchResponseData(file, imageId, contentType, file.length());
    }

    @Test
    @Points("W5E06.2")
    public void testPostAnotherNewImageAndDownloadItAsAttachment() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-2.png");
        String contentType = "application/x-random-stuff";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);
        downloadImageAsAttachment(imageId, contentType, file.getName(), file.length());
        matchResponseData(file, imageId, contentType, file.length());
    }

    @Test
    @Points("W5E06.3")
    public void testPostNewImageAndGetMetadataWhichShouldBeEscaped() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-1.jpg");
        String contentType = "application/octet-stream";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);
        getImageMetadata(imageId, contentType, file.getName(), StringEscapeUtils.escapeHtml4(randomDescription));
    }

    @Test
    @Points("W5E06.3")
    public void testPostNewImageAndGetMetadataList() throws Exception {
        String randomDescription = "description:" + UUID.randomUUID().toString();
        File file = new File("test-2.png");
        String contentType = "image/png";

        MockHttpServletResponse response = postNewImage(file, contentType, randomDescription);

        String imageId = getImageIdFromResponse(response);
        List<Image> images = getImageMetadataList();

        Image matchingImage = null;
        for (Image image : images) {
            if (image.getId() != null && image.getId().equals(imageId)) {
                matchingImage = image;
                break;
            }
        }

        if (matchingImage == null) {
            fail("Cannot find an image just sent in the JSON array response of GET-request to /app/images");
            return;
        }

        try {
            assertEquals("GET-request to /app/images (returning image metadata list) returns incorrect file name for image",
                    file.getName(), matchingImage.getFileName());
        } catch (Throwable t) {
            assertEquals("GET-request to /app/images (returning image metadata list) returns incorrect file name for image",
                    file.getName(), matchingImage.getFileName());
        }

        assertEquals("GET-request to /app/images (returning image metadata list) returns incorrect content type for image",
                contentType, matchingImage.getContentType());
        assertEquals("GET-request to /app/images (returning image metadata list) returns incorrect description for image",
                randomDescription, matchingImage.getDescription());
    }

    protected void matchResponseData(File file, String imageId, String contentType, long length) {
        try {
            MockHttpServletResponse response = downloadImage(imageId, contentType, length);
            byte[] responseBody = response.getContentAsByteArray();
                    
            byte[] original = IOUtils.toByteArray(new FileInputStream(file));
            
            assertTrue("Returned image data in response does not match the data in the image that was sent. Verify the byte array you are returning in the response!",
                    Arrays.equals(original, responseBody));
        } catch (Exception e) {
            throw new RuntimeException("Error matching image response data: " + e.toString(), e);
        }
    }

    protected String getImageIdFromResponse(MockHttpServletResponse response) {
        String imageIdString = response.getHeader("X-Image-Id");
        if (imageIdString == null) {
            fail("POST-request to /app/images does not contain header \"X-Image-Id\" in response");
            return null;
        }

        return imageIdString;
    }

    protected MockHttpServletResponse postNewImage(File file, String contentType, String description) throws Exception {
        byte[] content = IOUtils.toByteArray(new FileInputStream(file));
        MockMultipartFile mmf = new MockMultipartFile("file", file.getName(), contentType, content);
        
//        MediaType mt = MediaType.valueOf(contentType);
        MvcResult result = controllerMock
                .perform(fileUpload("/images").file(mmf).param("description", description).contentType(MediaType.MULTIPART_FORM_DATA))
                //                .andExpect(header().string("X-Image-Id", notNullValue()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        return result.getResponse();
    }

    protected MockHttpServletResponse downloadImage(String imageId, String expectedContentType,
            long expectedLength) throws Exception {
        
        MvcResult result = controllerMock.perform(get("/images/{id}/download/attachment", imageId))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertTrue(response.getHeader("Content-Type") != null && response.getHeader("Content-Type").contains(expectedContentType));
        assertEquals(String.valueOf(expectedLength), response.getHeader("Content-Length"));
        assertNotNull(response.getHeader("Content-Disposition"));

        return response;
    }

    protected MockHttpServletResponse downloadImageAsAttachment(String imageId, String expectedContentType,
            String expectedFileName, long expectedLength) throws Exception {

        MvcResult result = controllerMock.perform(get("/images/{id}/download/attachment", imageId))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertTrue(response.getHeader("Content-Type") != null && response.getHeader("Content-Type").contains(expectedContentType));
        assertEquals(String.valueOf(expectedLength), response.getHeader("Content-Length"));
        assertEquals(("attachment; filename=\"" + expectedFileName + "\"").replaceAll("\\s+", "").toLowerCase().trim(), response.getHeader("Content-Disposition").replaceAll("\\s+", "").toLowerCase().trim());

        return response;
    }

    protected Image getImageMetadata(String imageId, String expectedContentType,
            String expectedFileName, String expectedDescription) throws Exception {

        MvcResult result = controllerMock.perform(get("/images/{id}", imageId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();


        Image image = null;
        try {
            image = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Image>() {
            });
        } catch (Throwable t) {
            fail("Unable to convert the response for a GET request to images/" + imageId + " to an Image-object.");
        }

        assertEquals("The content type of the returned image did not match the expected content type.", expectedContentType, image.getContentType());
        assertEquals("The filename of the returned image did not match the expected filename.", expectedFileName, image.getFileName());
        assertEquals("The description of the returned image did not match the expected description.", expectedDescription, image.getDescription());

        assertNull("The image data should not be included in the response.", image.getData());

        return image;
    }

    protected List<Image> getImageMetadataList() throws Exception {
        MvcResult result = controllerMock.perform(get("/images"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        try {
            return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Image>>() {
            });
        } catch (Throwable t) {
            fail("Unable to convert data from making a GET to images to a list of Image-objects.");
        }

        return null;
    }
}
