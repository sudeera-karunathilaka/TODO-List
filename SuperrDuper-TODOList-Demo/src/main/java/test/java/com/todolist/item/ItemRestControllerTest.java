package test.java.com.todolist.item;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.todolist.Applicaction;
import com.todolist.item.Item;
import com.todolist.item.ItemRepository;
import com.todolist.itemlist.ItemList;
import com.todolist.itemlist.ItemListRepository;
import com.todolist.reminder.Reminder;
import com.todolist.reminder.ReminderRepository;

/**
 * The Class ItemRestControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Applicaction.class)
@WebAppConfiguration
public class ItemRestControllerTest
{

  /** The content type. */
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  /** The mock mvc. */
  private MockMvc mockMvc;

  /** The item list name. */
  private String itemListName = "Item List 1";

  /** The mapping jackson 2 http message converter. */
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  /** The item list. */
  private ItemList itemList;

  /** The items list. */
  private List<Item> itemsList = new ArrayList<>();

  /** The item repository. */
  @Autowired
  private ItemRepository itemRepository;

  /** The web application context. */
  @Autowired
  private WebApplicationContext webApplicationContext;

  /** The item list repository. */
  @Autowired
  private ItemListRepository itemListRepository;

  /** The reminder repository. */
  @Autowired
  private ReminderRepository reminderRepository;

  /**
   * Sets the converters.
   *
   * @param converters the new converters
   */
  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters)
  {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

    assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
  }

  /**
   * Setup.
   *
   * @throws Exception the exception
   */
  @Before
  public void setup() throws Exception
  {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();

    this.itemListRepository.deleteAll();
    this.itemRepository.deleteAll();
    this.reminderRepository.deleteAll();

    this.itemList = itemListRepository.save(new ItemList(itemListName, itemListName + " description"));

    Item itemA = itemRepository.save(new Item(itemList, "Item A", "A description", false, false, false));
    reminderRepository.save(new Reminder(itemA, "Reminder 1"));
    reminderRepository.save(new Reminder(itemA, "Reminder 2"));

    Item itemB = itemRepository.save(new Item(itemList, "Item B", "B description", false, false, false));
    reminderRepository.save(new Reminder(itemB, "Reminder 10"));
    reminderRepository.save(new Reminder(itemB, "Reminder 11"));
    reminderRepository.save(new Reminder(itemB, "Reminder 12"));

    this.itemsList.add(itemA);
    this.itemsList.add(itemB);

  }

  /**
   * Read single item list.
   *
   * @throws Exception the exception
   */
  @Test
  public void readSingleItemList() throws Exception
  {
    mockMvc.perform(get("/itemLists/" + this.itemList.getId() + "/items")).andExpect(status().isOk())
        .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(itemsList.size())))
        .andExpect(jsonPath("$[0].id", is(this.itemsList.get(0).getId().intValue())))
        .andExpect(jsonPath("$[0].name", is(this.itemsList.get(0).getName())))
        .andExpect(jsonPath("$[0].description", is(this.itemsList.get(0).getDescription())))
        .andExpect(jsonPath("$[1].id", is(this.itemsList.get(1).getId().intValue())))
        .andExpect(jsonPath("$[1].name", is(this.itemsList.get(1).getName())))
        .andExpect(jsonPath("$[1].description", is(this.itemsList.get(1).getDescription())));
  }

  /**
   * Creates the item.
   *
   * @throws Exception the exception
   */
  @Test
  public void createItem() throws Exception
  {
    String itemJson = toJSON(new Item(itemList, "Item 100", "Dummy todo item", false, false, false));

    this.mockMvc.perform(post("/itemLists/" + itemList.getId() + "/items").contentType(contentType).content(itemJson))
        .andExpect(status().isCreated());
  }

  /**
   * Adds the reminders to an item.
   *
   * @throws Exception the exception
   */
  @Test
  public void addRemindersToAnItem() throws Exception
  {
    Item item = itemsList.get(0);
    item.addReminder(new Reminder(item, "Reminder 100"));
    mockMvc.perform(put("/itemLists/{id}/items/" + item.getId()+"/reminders", item).contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(item))).andExpect(status().isOk());

    verify(itemRepository, times(1)).findByItemListId(item.getId());
  }

  /**
   * Tag item.
   *
   * @throws Exception the exception
   */
  @Test
  public void tagItem() throws Exception
  {
    Item item = itemsList.get(0);
    item.setTag(true);
    mockMvc
        .perform(put("/itemLists/" + itemList.getId() + "/items/" + item.getId(), item)
            .contentType(MediaType.APPLICATION_JSON).content(toJSON(item)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.tag", is(true)));
    verify(itemRepository, times(1)).findByItemListId(item.getId());
  }

  /**
   * Complete item.
   *
   * @throws Exception the exception
   */
  @Test
  public void completeItem() throws Exception
  {
    Item item = itemsList.get(0);
    item.setComplete(true);
    mockMvc.perform(put("/itemLists/" + itemList.getId() + "/items/" + item.getId(), item).contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(item))).andExpect(status().isOk()).andExpect(jsonPath("$.complete", is(true)));
    verify(itemRepository, times(1)).findByItemListId(item.getId());
  }

  /**
   * Delete item. This will use "Soft Delete" method as a deleted item needs to be restored. Therefore cannot use "DELETE" method
   *
   * @throws Exception the exception
   */
  @Test
  public void deleteItem() throws Exception
  {
    Item item = itemsList.get(0);
    item.setDelete(true);
    mockMvc.perform(put("/itemLists/" + itemList.getId() + "/items/" + item.getId(), item).contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(item))).andExpect(status().isOk()).andExpect(jsonPath("$.delete", is(true)));
    verify(itemRepository, times(1)).findByItemListId(item.getId());
  }

  /**
   * Restore item.
   *
   * @throws Exception the exception
   */
  @Test
  public void restoreItem() throws Exception
  {
    Item item = itemsList.get(0);
    item.setDelete(false);
    mockMvc.perform(put("/itemLists/" + itemList.getId() + "/items/" + item.getId(), item).contentType(MediaType.APPLICATION_JSON)
        .content(toJSON(item))).andExpect(status().isOk()).andExpect(jsonPath("$.delete", is(true)));
    verify(itemRepository, times(1)).findByItemListId(item.getId());
  }

  /**
   * To JSON.
   *
   * @param o the o
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected String toJSON(Object o) throws IOException
  {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }
}
