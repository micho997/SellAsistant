package pl.seller.assistant.databases.entity;

import java.sql.Blob;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class ImagesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column
  @ElementCollection(targetClass = Blob.class)
  private List<Blob> images;

  ImagesEntity() {
  }

  public ImagesEntity(List<Blob> images) {
    this.images = images;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Blob> getImages() {
    return images;
  }

  public void setImages(List<Blob> images) {
    this.images = images;
  }
}
