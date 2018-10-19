package com.ztp.books.models;

/**
* Package Dependencies
*/
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class Books {
  @Id
  public ObjectId _id;

  public String title;
  public String author;
  public String description;

  // Constructors
  public Books() {}

  public Books(ObjectId _id, String title, String author, String description) {
    this._id = _id;
    this.title = title;
    this.author = author;
    this.description = description;
  }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }

    public void set_id(ObjectId _id) { this._id = _id; }

    public String getTitle() { return title; }
    public void setName(String title) { this.title = title; }
   
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getDescription() { return description; }
    public void setBreed(String description) { this.description = description; }
  
}