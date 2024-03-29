package wad.eightball;

import org.springframework.stereotype.Service;

@Service
public class HardcodedEightballService implements EightballService {

    private String[] answers = new String[]{"It is certain",
        "It is decidedly so",
        "Without a doubt",
        "Yes definitely",
        "You may rely on it",
        "As I see it yes",
        "Most likely",
        "Outlook good",
        "Yes",
        "Signs point to yes",
        "Reply hazy try again",
        "Ask again later",
        "Better not tell you now",
        "Cannot predict now",
        "Concentrate and ask again",
        "Don't count on it",
        "My reply is no",
        "My sources say no",
        "Outlook not so good",
        "Very doubtful"};

    @Override
    public String getAnswer() {
        return answers[(int) (Math.random() * answers.length)];
    }

    public String[] getAllAnswers() {
        return answers;
    }
}
