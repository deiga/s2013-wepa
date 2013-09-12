package wad.chattingwithanna;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import wad.chattingwithanna.controller.ChatControllerInterface;
import wad.chattingwithanna.service.ChatBot;
import wad.chattingwithanna.service.MessageService;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Points("W2E04.2")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/test/resources/application-context-test.xml",
    "file:src/main/webapp/WEB-INF/spring/spring-base.xml" })
public class SpringControllerTest {
    @Autowired
    private ChatControllerInterface chatController;

    @Autowired
    private ChatBot chatBotMock;

    @Autowired
    private MessageService messageServiceMock;

    @Before
    public void beforeTest() {
        reset(chatBotMock);
        reset(messageServiceMock);
    }

    @Test
    public void testAddMessageMethodWithNullMessage() throws Exception {
        String nullMessage = null;
        String randomAnswer = "Answer:" + UUID.randomUUID().toString();
        String randomBotName = "BotName:" + UUID.randomUUID().toString();

        stub(chatBotMock.getName()).toReturn(randomBotName);
        stub(chatBotMock.getAnswerForQuestion(anyString())).toReturn(randomAnswer);

        String result;
        try {
            result = chatController.addMessage(nullMessage);
        } catch (Exception e) {
            fail("Method addMessage threw an exception when the given message was null: " + e.toString());
            return;
        }

        assertTrue("Method addMessage should issue a HTTP redirect to path \"list\" when the given message is null. " +
                "Now the returned view was: " + result,
                "redirect:list".equals(result));

        verify(chatBotMock, never()).getName();
        verify(chatBotMock, never()).getAnswerForQuestion(anyString());
        verify(messageServiceMock, never()).addMessage(anyString());
    }

    @Test
    public void testAddMessageMethodWithEmptyMessage() throws Exception {
        String emptyMessage = "";
        String randomAnswer = "Answer:" + UUID.randomUUID().toString();
        String randomBotName = "BotName:" + UUID.randomUUID().toString();

        stub(chatBotMock.getName()).toReturn(randomBotName);
        stub(chatBotMock.getAnswerForQuestion(anyString())).toReturn(randomAnswer);

        String result;
        try {
            result = chatController.addMessage(emptyMessage);
        } catch (Exception e) {
            fail("Method addMessage threw an exception when the given message was empty: " + e.toString());
            return;
        }

        assertTrue("Method addMessage should issue a HTTP redirect to path \"list\" when the given message is empty. " +
                "Now the returned view was: " + result,
                "redirect:list".equals(result));

        verify(chatBotMock, never()).getName();
        verify(chatBotMock, never()).getAnswerForQuestion(anyString());
        verify(messageServiceMock, never()).addMessage(anyString());
    }

    @Test
    public void testAddMessageMethod() throws Exception {
        String randomMessage = "Message:" + UUID.randomUUID().toString();
        String randomAnswer = "Answer:" + UUID.randomUUID().toString();
        String randomBotName = "BotName:" + UUID.randomUUID().toString();

        stub(chatBotMock.getName()).toReturn(randomBotName);
        stub(chatBotMock.getAnswerForQuestion(anyString())).toReturn(randomAnswer);

        String result;
        try {
            result = chatController.addMessage(randomMessage);
        } catch (Exception e) {
            fail("Method addMessage threw an exception: " + e.toString());
            return;
        }

        assertTrue("Method addMessage should issue a HTTP redirect to path \"list\". " +
                "Now the returned view was: " + result,
                "redirect:list".equals(result));

        verify(chatBotMock).getName();
        verify(chatBotMock).getAnswerForQuestion(randomMessage);

        ArgumentCaptor<String> messageArgument =
                ArgumentCaptor.forClass(String.class);

        verify(messageServiceMock, times(2)).addMessage(
                messageArgument.capture());

        List<String> values = messageArgument.getAllValues();

        String storedMessage = values.get(0);

        assertTrue("ChatController.addMessage-method should add first the message " +
                "containing the text \"You:\" and the user's input using " +
                "MessageService.addMessage-method. Now the first added message was: " +
                storedMessage,
                storedMessage.startsWith("You:") && storedMessage.contains(randomMessage));

        String storedAnswer = values.get(1);

        assertTrue("ChatController.addMessage-method should add a second message " +
                "containing the chat bot's name \"" + randomBotName + ":\" and the bot's answer using " +
                "MessageService.addMessage-method",
                storedAnswer.startsWith(randomBotName + ":") &&
                storedAnswer.contains(randomAnswer));
    }

    @Test
    public void testListMethod() throws Exception {
        ExtendedModelMap model = new ExtendedModelMap();

        List<String> testList = new ArrayList<String>();
        testList.add(UUID.randomUUID().toString());

        stub(messageServiceMock.list()).toReturn(testList);

        String result;
        try {
            result = chatController.list(model);
        } catch (Exception e) {
            fail("Method list threw an exception: " + e.toString());
            return;
        }

        assertTrue("Method list should display view \"list\". " +
                "Now the returned view was: " + result,
                "list".equals(result));

        verify(messageServiceMock).list();

        assertTrue("ChatController.list-method should add the list of messages from " +
                "MessageService.list-method to the model with key \"messages\"",
                model.containsKey("messages"));

        assertTrue("ChatController.list-method should add the list of messages from " +
                "MessageService.list-method to the model -- now it added something else",
                testList.equals(model.get("messages")));
    }
}
