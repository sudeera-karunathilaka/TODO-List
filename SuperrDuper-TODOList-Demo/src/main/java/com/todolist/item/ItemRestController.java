package com.todolist.item;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todolist.exception.ToDoListNotFoundException;
import com.todolist.itemlist.ItemList;
import com.todolist.itemlist.ItemListRepository;

/**
 * The rest controller responsible for Items in a ItemList.
 *
 * @author Sudeera Karunathilaka
 */
@RestController
class ItemRestController
{

  /** The item repository. */
  private final ItemRepository itemRepository;

  /** The item list repository. */
  private final ItemListRepository itemListRepository;

  /**
   * Instantiates a new item rest controller.
   *
   * @param itemRepository
   *            the item repository
   * @param itemListRepository
   *            the item list repository
   */
  @Autowired
  ItemRestController(ItemRepository itemRepository, ItemListRepository itemListRepository)
  {
    this.itemRepository = itemRepository;
    this.itemListRepository = itemListRepository;
  }

  /**
   * Read item.
   *
   * @param itemListId the item id
   * @return the all item
   */
  @RequestMapping("/itemLists/{itemListId}/items")
  public List<Item> getAllItem(@PathVariable Long itemListId)
  {
    this.validateItemList(itemListId);
    List<Item> items = new ArrayList<>();
    this.itemRepository.findByItemListId(itemListId).forEach(items::add);;
    return items;
  }
  
  /**
   * Gets the item.
   *
   * @param id the id
   * @return the item
   */
  @RequestMapping("/itemLists/{itemListId}/items/{id}")
  public Item getItem(@PathVariable Long id)
  {
    return this.itemRepository.findOne(id);
  }
  

  /**
   * Creates an item and assign it to the given item list.
   *
   * @param itemListId
   *            the item list id
   * @param input
   *            the input
   * @return the response entity
   */
  @RequestMapping(method = RequestMethod.POST, value="/itemLists/{itemListId}/items")
  ResponseEntity<?> add(@PathVariable Long itemListId, @RequestBody Item input)
  {
    this.validateItemList(itemListId);

    return this.itemListRepository.findById(itemListId).map(itemList -> {
      Item result = itemRepository
          .save(new Item(itemList, input.getName(), input.getDescription(), false, false, false));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
          .toUri();

      return ResponseEntity.created(location).build();
    }).orElse(ResponseEntity.noContent().build());

  }

  /**
   * Edits the item. This method will be used when "Tag", "Complete",
   * "Delete/Restore" or "add reminders" to an item. Since a delete item needs
   * to be restored, have to use soft delete method instead if "DELETE"
   * method)
   *
   * @param editedItem
   *            the edited item
   * @param id
   *            the id
   */
  @RequestMapping(method = RequestMethod.PUT, value = "/itemLists/{itemListId}/items/{id}")
  public void editItem(@RequestBody @Valid Item editedItem, @PathVariable("id") Long id, @PathVariable Long itemListId)
  {
    editedItem.setItemList(new ItemList(itemListId, "", ""));
    itemRepository.saveAndFlush(editedItem);
  }

  /**
   * Validates if the given item id is already saved.
   *
   * @param itemListId
   *            the item list id
   */
  private void validateItemList(Long itemListId)
  {
    this.itemListRepository.findById(itemListId)
        .orElseThrow(() -> new ToDoListNotFoundException(ToDoListNotFoundException.TYPE.ItemList, itemListId));
  }
}