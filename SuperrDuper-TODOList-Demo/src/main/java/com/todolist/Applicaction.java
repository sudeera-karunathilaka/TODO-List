package com.todolist;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.todolist.item.Item;
import com.todolist.item.ItemRepository;
import com.todolist.itemlist.ItemList;
import com.todolist.itemlist.ItemListRepository;
import com.todolist.reminder.Reminder;
import com.todolist.reminder.ReminderRepository;

/**
 * The Class Application.
 * 
 * @author Sudeera Karunathilaka
 */
@SpringBootApplication
public class Applicaction
{

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args)
  {
    SpringApplication.run(Applicaction.class, args);
  }

  /**
   * Creates a set of dummy data.
   *
   * @param itemListRepository the item list repository
   * @param itemRepository the item repository
   * @param reminderRepository the reminder repository
   * @return the command line runner
   */
  @Bean
  CommandLineRunner init(ItemListRepository itemListRepository, ItemRepository itemRepository,
                         ReminderRepository reminderRepository)
  {
    return (evt) -> Arrays.asList("Todo List 1, Todo List 2, Todo List 3".split(",")).forEach(a -> {
      ItemList itemList = itemListRepository.save(new ItemList(a, a + " demo"));
      Item item = itemRepository.save(new Item(itemList, "Item A", "A description", false, false, false));
      reminderRepository.save(new Reminder(item, "Reminder 1"));
      reminderRepository.save(new Reminder(item, "Reminder 2"));

      item = itemRepository.save(new Item(itemList, "Item B", "B description", false, false, false));
      reminderRepository.save(new Reminder(item, "Reminder 10"));
      reminderRepository.save(new Reminder(item, "Reminder 11"));
      reminderRepository.save(new Reminder(item, "Reminder 12"));
    });
  }
}
