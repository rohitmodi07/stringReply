package com.autodesk.stringreply;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {ReplyController.class})
@ExtendWith(SpringExtension.class)
class ReplyControllerTest {
    @Autowired
    private ReplyController replyController;

    /**
     * Method under test: {@link ReplyController#replying()}
     */
    @Test
    void testReplying() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reply");
        MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Message is empty\"}"));
    }

    /**
     * Method under test: {@link ReplyController#replyingV2(String)}
     */
    @Test
    void testReplyingV2_with_null_input() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController()).replyingV2(null);
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(400, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("Invalid input", actualReplyingV2Result.getBody().getMessage());
    }

    /**
     * Method under test: {@link ReplyController#replyingV2(String)}
     */
    @Test
    void testReplyingV2_with_empty_input() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController()).replyingV2("");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(400, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("Invalid input", actualReplyingV2Result.getBody().getMessage());
    }

    /**
     * Method under test: {@link ReplyController#replyingV2(String)}
     */
    @Test
    void testReplyingV2_forInvalidInput() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("13-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(400, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("Invalid input", actualReplyingV2Result.getBody().getMessage());
    }

    /**
     * Method under test: {@link ReplyController#replyingV2(String)}
     */
    @Test
    void testReplyingV2_with_valid_input_reverseAndHash() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("12-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(200, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("5a8973b3b1fafaeaadf10e195c6e1dd4", actualReplyingV2Result.getBody().getMessage());
    }

    @Test
    void testReplyingV2_with_valid_input_reverseStringMultiple() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("11-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(200, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("kbzw9ru", actualReplyingV2Result.getBody().getMessage());
    }

    @Test
    void testReplyingV2_with_valid_input_reverseString() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("1-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(200, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("ur9wzbk", actualReplyingV2Result.getBody().getMessage());
    }

    @Test
    void testReplyingV2_with_valid_input_calculateHash() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("2-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(200, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("0fafeaae780954464c1b29f765861fad", actualReplyingV2Result.getBody().getMessage());
    }

    @Test
    void testReplyingV2_with_valid_input_calculateHashMul() {

        ResponseEntity<ReplyMessage> actualReplyingV2Result = (new ReplyController())
                .replyingV2("22-kbzw9ru");
        assertTrue(actualReplyingV2Result.hasBody());
        assertTrue(actualReplyingV2Result.getHeaders().isEmpty());
        assertEquals(200, actualReplyingV2Result.getStatusCodeValue());
        assertEquals("e8501e64cf0a9fa45e3c25aa9e77ffd5", actualReplyingV2Result.getBody().getMessage());
    }

    /**
     * Method under test: {@link ReplyController#convertMessageToMD5Format(String)}
     */
    @Test
    void testConvertMessageToMD5Format() {
        assertEquals("0e9c20d9b237aecc65de77a491061be5",
                replyController.convertMessageToMD5Format("27c7cf400229103e00c6d8830029e29b"));
    }


    /**
     * Method under test: {@link ReplyController#replying(String)}
     */
    @Test
    void testReplying_with_empty_value() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reply/{message}", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Message is empty\"}"));
    }

    /**
     * Method under test: {@link ReplyController#replying(String)}
     */
    @Test
    void testReplying_with_value() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reply/{message}",
                "kbzw9ru");
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"kbzw9ru\"}"));
    }
}

