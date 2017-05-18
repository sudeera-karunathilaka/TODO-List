package com.todolist.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface ItemRepository.
 * 
 * @author Sudeera Karunathilaka
 */
public interface ItemRepository extends JpaRepository<Item, Long>
{

  /**
   * Find by item list id.
   *
   * @param itemListId the item list id
   * @return the list
   */
  List<Item> findByItemListId(Long itemListId);

}
