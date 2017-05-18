package com.todolist.itemlist;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.todolist.item.Item;

/**
 * The Class ItemList.
 * 
 * @author Sudeera Karunathilaka
 */
@Entity
public class ItemList
{

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The name. */
  private String name;

  /** The items. */
  @OneToMany(mappedBy = "itemList", cascade = CascadeType.ALL)
  private Set<Item> items = new HashSet<>();

  /** The description. */
  private String description;

  /**
   * Instantiates a new item list.
   */
  ItemList()
  { // jpa only
  }

  /**
   * Instantiates a new item list.
   *
   * @param name the name
   * @param description the description
   */
  public ItemList(String name, String description)
  {
    this.name = name;
    this.description = description;
  }

  /**
   * Instantiates a new item list.
   *
   * @param id the id
   * @param name the name
   * @param description the description
   */
  public ItemList(Long id, String name, String description)
  {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Gets the items.
   *
   * @return the items
   */
  public Set<Item> getItems()
  {
    return items;
  }

  /**
   * Adds the item.
   *
   * @param item the item
   */
  public void addItem(Item item)
  {
    this.items.add(item);
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId()
  {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id)
  {
    this.id = id;
  }
}
