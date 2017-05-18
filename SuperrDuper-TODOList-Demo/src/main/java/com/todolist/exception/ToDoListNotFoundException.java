package com.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ToDoListNotFoundException.
 * 
 * @author Sudeera Karunathilaka
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToDoListNotFoundException extends RuntimeException
{
  
  /**
   * The Enum TYPE.
   */
  public static enum TYPE
  {
    
    /** The Item list. */
    ItemList("item list"), 
 /** The Item. */
 Item("item");

    /** The type string. */
    private String typeString;

    /**
     * Instantiates a new type.
     *
     * @param typeString the type string
     */
    private TYPE(String typeString)
    {
      this.typeString = typeString;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString()
    {
      return this.typeString;
    }
  };

  /**
   * Instantiates a new to do list not found exception.
   *
   * @param type the type
   * @param itemListId the item list id
   */
  public ToDoListNotFoundException(TYPE type, Long itemListId)
  {
    super("could not find " + type.toString() + " '" + itemListId + "'.");
  }
}
