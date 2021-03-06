//package com.company.star.db.model;
//
//import com.company.star.utils.Constants;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(
//        value = {"createdAt", "updatedAt"},
//        allowGetters = true
//)
//public abstract class AuditModel implements Serializable {
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at", nullable = false, updatable = false)
//    @CreatedDate
//    private Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_at", nullable = false)
//    @LastModifiedDate
//    private Date updatedAt;
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    	public static Carrier getInstance(int carrierID) {
//		if (carrierID == Constants.SHIPMENT_TYPE_FEDEX) {
//			return new Carrier();
//		} else if (carrierID == Constants.SHIPMENT_TYPE_UPS){
//			return new Carrier();
//		}
//		return null;
//}