package test.java.com.todolist.itemlist;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
 * The Class ItemListRestControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Applicaction.class)
@WebAppConfiguration
public class ItemListRestControllerTest
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

  //	@Test
  //	public void readSingleItemList() throws Exception {
  //		mockMvc.perform(get("/itemList/" + this.itemList.getId())).andExpect(status().isOk())
  //				.andExpect(content().contentType(contentType))
  ////				.andExpect(jsonPath("$.id", is(this.itemList.getId().intValue())))
  //				.andExpect(jsonPath("$.name", is("Item List 1")))
  //				.andExpect(jsonPath("$.description", is("Item List 1 description")));
  //	}

  /**
  * Read item lists.
  *
  * @throws Exception the exception
  */
  @Test
  public void readItemLists() throws Exception
  {
    mockMvc.perform(get("/itemLists/")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(this.itemList.getId().intValue())))
        .andExpect(jsonPath("$[0].name", is(this.itemList.getName())))
        .andExpect(jsonPath("$[0].description", is(this.itemList.getDescription())));
  }

  /**
   * Creates the item list.
   *
   * @throws Exception the exception
   */
  @Test
  public void createItemList() throws Exception
  {
    String itemListJson = toJSON(new ItemList("Item List 10", "Dummy todo list"));

    this.mockMvc.perform(post("/itemLists").contentType(contentType).content(itemListJson))
        .andExpect(status().isCreated());
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
