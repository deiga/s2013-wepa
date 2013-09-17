/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.game.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import wad.hangman.game.State;

/**
 *
 * @author timosand
 */
public class StateDeserializer extends JsonDeserializer<State> {

    @Override
    public State deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return State.fromString(jp.getValueAsString());
    }
    
}
