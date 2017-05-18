package com.todolist.itemlist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface ItemListRepository.
 * 
 * @author Sudeera Karunathilaka
 */
public interface ItemListRepository extends JpaRepository<ItemList, Long>
{

  /**
   * Find by id.
   *
   * @param itemListId the item list id
   * @return the optional
   */
  Optional<ItemList> findById(Long itemListId);

}
