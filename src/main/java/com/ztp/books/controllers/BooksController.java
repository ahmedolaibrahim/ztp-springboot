package com.ztp.books.controllers;


/**
* Package Dependencies
*/

import org.bson.types.ObjectId;
import com.ztp.books.models.Books;
import com.ztp.books.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;




//==============================================================================
/**
*Rest Controller Routes
*/

@RestController
@RequestMapping("/api/books")
public class BooksController {
  @Autowired
  private BooksRepository repository;

/**
 * 
 * Returns lists of all books
 * @param  none
 * @return {List}  Book List
 */

@RequestMapping(value = "/", method = RequestMethod.GET, headers="Accept=application/json")
public List<Books> getAllBooks() {
  
  return repository.findAll();
}

/**
 * 
 * Returns Book based on book_id input
 * @param  {String} Book Object Id 
 * @param  {HttpServletResponse} http response
 * @param  {WebRequest}  web request
 * @return {Object}    Book Object
 */

@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers="Accept=application/json")
public Books getBookById(@PathVariable("id") ObjectId id, HttpServletResponse httpResponse, WebRequest request) {
  httpResponse.setStatus(HttpStatus.CREATED.value());
  httpResponse.setHeader("Location", String.format("%s/api/books/%s", 
                                request.getContextPath(),id));  
  return repository.findBy_id(id);
}

/**
 * 
 * Update Book Object based on id params
 * @param  {String} Book Object Id 
 * @param  {HttpServletResponse} http response
 * @param  {WebRequest}  web request
 * @return {Object}    Updated Book Object
 */

@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers="Accept=application/json")
  public void modifyBookById(@PathVariable("id") ObjectId id, @Valid @RequestBody Books books,HttpServletResponse httpResponse, WebRequest request) {
    books.set_id(id);
    httpResponse.setStatus(204);
    repository.save(books);
  }

 /**
 * 
 * Add Book
 * @param  {Json} Book values
 * @param  {HttpServletResponse} http response
 * @param  {WebRequest}  web request
 * @return {Object}    Created Book
 */

  
  @RequestMapping(value = "/", method = RequestMethod.POST, headers="Accept=application/json")
  public Books createBook(@Valid @RequestBody Books books, HttpServletResponse httpResponse, WebRequest request) {
   
    books.set_id(ObjectId.get());
    httpResponse.setStatus(HttpStatus.CREATED.value());
    httpResponse.setHeader("Location", String.format("%s/api/books/%s", 
                                  request.getContextPath(),books._id));  
    repository.save(books);
    return books;
  }


/**
 * Deletebook
 * @param  {String} Book id
 * @param  {HttpServletResponse} http response
 * @param  {WebRequest}  web request
 * @return 
 */

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers="Accept=application/json")
  public void deleteBook(@PathVariable ObjectId id,  HttpServletResponse httpResponse) {
    repository.delete(repository.findBy_id(id));
    httpResponse.setStatus(204);
  }

}