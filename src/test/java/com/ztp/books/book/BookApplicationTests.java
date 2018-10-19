/**
 * created by Ahmed on 17-10-2018
 * Updated by Ahmed on 19-10-2018
 */
//============================================================================

package com.ztp.books.book;


/**
*Test Package Dependencies
*/

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ztp.books.repository.BooksRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookApplicationTests {
  @Autowired
	private MockMvc mockMvc;

	@Autowired
	private BooksRepository booksRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		booksRepository.deleteAll();
	}

	@Test
  public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/api/books/")).andDo(print()).andExpect(status().isOk());
  }
  
  @Test
	public void shouldCreateBook() throws Exception {

		mockMvc.perform(post("/api/books/").content(
        "{\"title\": \"testTitle\", \"author\":\"TestAuthor\", \"description\":\"TestDescription\"}")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
          status().isCreated()).andExpect(
              header().string("Location", containsString("books/")));
  }
  
  @Test
	public void shouldRetrieveBook() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/api/books/").content(
      "{\"title\": \"testTitle\", \"author\":\"TestAuthor\", \"description\":\"TestDescription\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(
          status().isCreated()).andReturn();
         
        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location)).andExpect(status().isCreated()).andExpect(
            jsonPath("$.title").value("testTitle")).andExpect(
                jsonPath("$.author").value("TestAuthor"));
  }

  @Test
	public void shouldUpdateBook() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/api/books/").content(
      "{\"title\": \"testTitle\", \"author\":\"TestAuthor\", \"description\":\"TestDescription\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(
          status().isCreated()).andReturn();
		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location).content(
      "{\"title\": \"testTitle2\", \"author\":\"TestAuthor2\", \"description\":\"TestDescription\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(
						status().isNoContent());
    location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isCreated()).andExpect(
      jsonPath("$.title").value("testTitle2")).andExpect(
        jsonPath("$.author").value("TestAuthor2"));
  }
  

  @Test
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/api/books/").content(
      "{\"title\": \"testTitle\", \"author\":\"TestAuthor\", \"description\":\"TestDescription\"}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(
          status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());
	}



}
