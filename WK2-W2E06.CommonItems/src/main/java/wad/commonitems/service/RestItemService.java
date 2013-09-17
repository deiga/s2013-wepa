/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.commonitems.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wad.commonitems.domain.Item;

/**
 *
 * @author timosand
 */
@Service
public class RestItemService implements ItemService {
    
    RestTemplate restTemplate;
    
    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void add(Item item) {
        restTemplate.postForObject("http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items", item, Item.class);
    }

    @Override
    public void delete(Integer id) {
        restTemplate.delete("http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items/{id}", id);
    }

    @Override
    public List<Item> list() {
        return restTemplate.getForObject("http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items", List.class);
    }
    
}
