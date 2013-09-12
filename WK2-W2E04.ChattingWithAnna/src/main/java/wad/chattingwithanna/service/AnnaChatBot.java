package wad.chattingwithanna.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import javax.annotation.PostConstruct;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnnaChatBot implements ChatBot {

    private static final String REQUEST_URL_TEMPLATE =
            "http://ikanna-usen.artificial-solutions.com/ikanna-usen/"
            + "?ARTISOLCMD_TEMPLATE=STANDARDJSON&viewtype=STANDARDJSON"
            + "&viewname=STANDARDJSON&command=request&userinput={0}";
    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public String getName() {
        return "Anna";
    }

    @Override
    public String getAnswerForQuestion(String question) throws IOException {
        String urlEncodedQuestion =
                URLEncoder.encode(question, "UTF-8");
        String requestUrl =
                MessageFormat.format(REQUEST_URL_TEMPLATE, urlEncodedQuestion);

        String response = restTemplate.getForObject(requestUrl, String.class, question);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);
        JsonNode responseDataNode = rootNode.get("responseData");
        if (!responseDataNode.isObject()) {
            throw new IOException("Invalid response from chat bot (responseData)");
        }

        JsonNode answerNode = responseDataNode.get("answer");
        if (!answerNode.isValueNode()) {
            throw new IOException("Invalid response from chat bot (answer)");
        }

        String answer = answerNode.asText();

        return URLDecoder.decode(answer, "UTF-8");
    }
}
