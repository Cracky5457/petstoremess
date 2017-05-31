package com.petstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_IMAGE_PET")
public class PetImageEntity extends AbstractEntity {
	
    @Id
    @GeneratedValue
    @Column(name = "IDT_IMAGE_PET")
    private long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PET", referencedColumnName="IDT_PET")
    private PetEntity pet;
    
    @Column(name = "IMAGE_NAME")
    private String fileName;
    
    @Column(columnDefinition = "varbinary(max)",name = "DATA")
    private byte[] data;
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public byte[] getData() {
        return data;
    }
 
    public void setData(byte[] data) {
        this.data = data;
    }

	public PetEntity getPet() {
		return pet;
	}

	public void setPet(PetEntity pet) {
		this.pet = pet;
	}
    
    
}