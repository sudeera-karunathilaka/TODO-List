package com.todolist.itemlist;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The rest controller responsible for ItemLists.
 *
 * @author Sudeera Karunathilaka
 */
@RestController
public class ItemListRestController
{

  /** The item list repository. */
  private final ItemListRepository itemListRepository;

  /**
   * Instantiates a new item list rest controller.
   *
   * @param itemListRepository the item list repository
   */
  @Autowired
  ItemListRestController(ItemListRepository itemListRepository)
  {
    this.itemListRepository = itemListRepository;
  }

  /**
   * Read all item lists.
   *
   * @return the collection
   */
  @RequestMapping("/itemLists")
  public Collection<ItemList> readAllItemLists()
  {
    List<ItemList> itemLists = new ArrayList<>();
    this.itemListRepository.findAll().forEach(itemLists::add);
    return itemLists;
  }

  /**
   * Gets the item list.
   *
   * @param id the id
   * @return the item list
   */
  @RequestMapping("/itemLists/{id}")
  public ItemList getItemList(@PathVariable Long id)
  {
    return this.itemListRepository.findOne(id);
  }

  /**
   * Adds the item list.
   *
   * @param input the input
   * @return the response entity
   */
  @RequestMapping(method = RequestMethod.POST, value = "/itemLists")
  public ResponseEntity<?> addItemList(@RequestBody ItemList input)
  {
    ItemList result = itemListRepository.save(input);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  /**
   * Edits the item list.
   *
   * @param itemList the item list
   * @param id the id
   */
  @RequestMapping(method = RequestMethod.PUT, value = "/itemLists/{id}")
  public void editItemList(@RequestBody ItemList itemList, @PathVariable Long id)
  {
    itemListRepository.saveAndFlush(itemList);
  }

  /**
   * Deletes the item list.
   *
   * @param id the id
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/itemLists/{id}")
  public void deleteItemList(@PathVariable Long id)
  {
    itemListRepository.delete(id);
  }
}
