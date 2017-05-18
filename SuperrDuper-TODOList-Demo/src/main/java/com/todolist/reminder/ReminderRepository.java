package com.todolist.reminder;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface ReminderRepository.
 * 
 * @author Sudeera Karunathilaka
 */
public interface ReminderRepository extends JpaRepository<Reminder, Long>
{

  /**
   * Find all by item id.
   *
   * @param itemId the item id
   * @return the collection
   */
  Collection<Reminder> findAllByItemId(Long itemId);
}
