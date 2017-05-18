package com.todolist.reminder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.item.Item;

/**
 * The Class Reminder.
 * 
 * @author Sudeera Karunathilaka
 */
@Entity
public class Reminder
{

  /** The item. */
  @JsonIgnore
  @ManyToOne
  private Item item;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The name. */
  private String name;

  /**
   * Instantiates a new reminder.
   */
  public Reminder()
  {

  }

  /**
   * Instantiates a new reminder.
   *
   * @param item the item
   * @param name the name
   */
  public Reminder(Item item, String name)
  {
    this.item = item;
    this.name = name;
  }

  /**
   * Gets the item.
   *
   * @return the item
   */
  public Item getItem()
  {
    return item;
  }

  /**
   * Sets the item.
   *
   * @param item the new item
   */
  public void setItem(Item item)
  {
    this.item = item;
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

}
