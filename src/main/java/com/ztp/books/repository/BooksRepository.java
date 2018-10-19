
/**
 * created by Ahmed on 17-10-2018
 * Updated by Ahmed on 19-10-2018
 */
//============================================================================

package com.ztp.books.repository;


/**
* Package Dependencies
*/

import com.ztp.books.models.Books;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BooksRepository extends MongoRepository<Books, String> {
  Books findBy_id(ObjectId _id);
}