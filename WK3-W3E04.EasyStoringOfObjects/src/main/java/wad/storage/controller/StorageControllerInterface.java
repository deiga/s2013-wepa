package wad.storage.controller;

import org.springframework.ui.Model;

public interface StorageControllerInterface {
    String view(Model model);
    String add(String name);
    String increaseCount(Long itemId);
}
