package com.company.star.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.company.star.db.model.Carrier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentForm extends Package implements Form {

	@NotNull(message = "carrier id field is required")
	@Min(value = 1, message = "carrier id should be bigger than or equal to one")
	private int carrierId;

	@NotNull(message = "shipmentId is required")
	private String shipmentServiceID;

//	private Carrier carrier;

//	@NotNull(message = "name is required")
//	private String name;

	@NotNull(message = "carrierType is required")
	@Min(value = 3, message = "carrierType should be bigger than or equal to one")
	@Max(value = 3,  message = "carrierType should be bigger than or equal to one")
	private int carrierType;

	@NotNull(message = "actionType is required")
	private int actionType;

	@NotNull(message = "actionValue is required")
	private Object actionValue;

//	@NotNull(message = "status is required")
//	private String status;
	private String localizedTitle;
	private String localizedBody;

	public String getShipmentServiceID() {
		return shipmentServiceID;
	}

	public void setShipmentServiceID(String shipmentServiceID) {
		this.shipmentServiceID = shipmentServiceID;
	}

	public int getCarrierType(int i) {
		return carrierType;
	}

	public int getCarrierId(Carrier i) {
		return carrierId;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrier(int carrierId) {
		this.carrierId = carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public void setCarrierType(int carrierType) {
		this.carrierType = carrierType;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public Object getActionValue() {
		return actionValue;
	}

	public void setActionValue(Object actionValue) {
		this.actionValue = actionValue;
	}



//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

	public String getLocalizedTitle() {
		return localizedTitle;
	}

	public void setLocalizedTitle(String localizedTitle) {
		this.localizedTitle = localizedTitle;
	}

	public String getLocalizedBody() {
		return localizedBody;
	}

	public void setLocalizedBody(String localizedBody) {
		this.localizedBody = localizedBody;
	}

}
