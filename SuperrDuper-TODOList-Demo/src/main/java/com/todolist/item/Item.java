package com.todolist.item;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.itemlist.ItemList;
import com.todolist.reminder.Reminder;

/**
 * The Class Item.
 * 
 * @author Sudeera Karunathilaka
 */
@Entity
public class Item
{

  /** The item list. */
  @JsonIgnore
  @ManyToOne
  private ItemList itemList;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** The tag. */
  private boolean tag;

  /** The complete. */
  private boolean complete;

  /** The delete. */
  private boolean delete;

  /** The reminders. */
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  public Set<Reminder> reminders = new HashSet<>();

  /**
   * Instantiates a new item.
   */
  public Item()
  { // jpa only
  }

  /**
   * Instantiates a new item.
   *
   * @param itemList the item list
   * @param name the name
   * @param description the description
   * @param tag the tag
   * @param complete the complete
   * @param delete the delete
   */
  public Item(ItemList itemList, String name, String description, boolean tag, boolean complete, boolean delete)
  {
    this.name = name;
    this.description = description;
    this.itemList = itemList;
    this.tag = tag;
    this.complete = complete;
    this.delete = delete;
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
   * Gets the reminders.
   *
   * @return the reminders
   */
  public Set<Reminder> getReminders()
  {
    return reminders;
  }

  /**
   * Adds the reminder.
   *
   * @param reminder the reminder
   */
  public void addReminder(Reminder reminder)
  {
    this.reminders.add(reminder);
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

  /**
   * Checks if is tag.
   *
   * @return true, if is tag
   */
  public boolean isTag()
  {
    return tag;
  }

  /**
   * Sets the tag.
   *
   * @param tag the new tag
   */
  public void setTag(boolean tag)
  {
    this.tag = tag;
  }

  /**
   * Checks if is complete.
   *
   * @return true, if is complete
   */
  public boolean isComplete()
  {
    return complete;
  }

  /**
   * Sets the complete.
   *
   * @param complete the new complete
   */
  public void setComplete(boolean complete)
  {
    this.complete = complete;
  }

  /**
   * Checks if is delete.
   *
   * @return true, if is delete
   */
  public boolean isDelete()
  {
    return delete;
  }

  /**
   * Sets the delete.
   *
   * @param delete the new delete
   */
  public void setDelete(boolean delete)
  {
    this.delete = delete;
  }

  /**
   * Gets the item list.
   *
   * @return the item list
   */
  public ItemList getItemList()
  {
    return itemList;
  }

  /**
   * Sets the item list.
   *
   * @param itemList the new item list
   */
  public void setItemList(ItemList itemList)
  {
    this.itemList = itemList;
  }

}