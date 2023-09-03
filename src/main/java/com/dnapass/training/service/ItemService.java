package com.dnapass.training.service;

import com.dnapass.training.entity.Item;
import com.dnapass.training.entity.ItemDetails;
import com.dnapass.training.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService
{
    @Autowired
    private ItemRepository itemRepository;



    //To add new item
    public Item addNewItem(Item item) {
    return itemRepository.save(item);

}

    //To display all item details and its associated user for the trader
    public List<ItemDetails> getAllItems(){
        return itemRepository.findAllItems();

    }

    //To delete the item based on id
    public void deleteItemDetails(Integer itemId) {
        itemRepository.deleteById(itemId);

    }

    //To fetch single item based on their id
    public Item getItemById(Integer itemId)
    {
        return itemRepository.findById(itemId).orElse(null);
    }

    //To display all item details when searching based on their name
    public List<ItemDetails> searchItem(String city) {
        return itemRepository.getItemDetailsBasedCity(city);

    }

    //To display all items for a particular user
    public List<Item> getAllItemById(Integer id) {

        return itemRepository.findAllItem(id);
    }
}