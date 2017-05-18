package com.todolist.reminder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todolist.exception.ToDoListNotFoundException;
import com.todolist.item.ItemRepository;

/**
 * The rest controller responsible for Reminders in a Item.
 * 
 * @author Sudeera Karunathilaka
 */
@RestController
public class ReminderRestController
{

  /** The reminder repository. */
  private final ReminderRepository reminderRepository;

  /** The item repository. */
  private final ItemRepository itemRepository;

  /**
   * Instantiates a new reminder rest controller.
   *
   * @param itemRepository
   *            the item repository
   * @param itemListRepository
   *            the item list repository
   * @param reminderRepository
   *            the reminder repository
   */
  @Autowired
  ReminderRestController(ItemRepository itemRepository, ReminderRepository reminderRepository)
  {
    this.itemRepository = itemRepository;
    this.reminderRepository = reminderRepository;
  }

  /**
   * Read all reminders.
   *
   * @param itemId            the item id
   * @return the list
   * @throws ToDoListNotFoundException the to do list not found exception
   */
  @RequestMapping(method = RequestMethod.GET, value="/itemLists/{itemListId}/items/{id}/reminders")
  List<Reminder> readAllReminders(@PathVariable Long itemId) throws ToDoListNotFoundException
  {
    this.validateItem(itemId);
    List<Reminder> reminders = new ArrayList<>();
    this.reminderRepository.findAllByItemId(itemId).forEach(reminders::add);
    return reminders;
  }

  /**
   * Adds the.
   *
   * @param input
   *            the input
   * @return the response entity
   */
  @RequestMapping(method = RequestMethod.POST, value="/itemLists/{itemListId}/items/{id}/reminders")
  ResponseEntity<?> add(@RequestBody Reminder input)
  {
    Reminder result = reminderRepository.save(input);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
        .toUri();
    return ResponseEntity.created(location).build();

  }

  /**
   * Validate item.
   *
   * @param itemId
   *            the item id
   * @throws ItemNotFoundException
   *             the item not found exception
   */
  private void validateItem(Long itemId) throws ToDoListNotFoundException
  {
    if (this.itemRepository.findOne(itemId) == null)
    {
      throw new ToDoListNotFoundException(ToDoListNotFoundException.TYPE.Item, itemId);
    }
  }
}
