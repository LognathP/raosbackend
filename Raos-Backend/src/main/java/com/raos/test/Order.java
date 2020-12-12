package com.raos.test;


import java.util.Date;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Order {

	@Qualifier("order_id")
	@JsonProperty("order_id")
    public int order_id;
    @Qualifier("order_message")
    @JsonProperty("order_message")
    public String order_message;
    @Qualifier("order_placed_date")
    @JsonProperty("order_placed_date")
    public String order_placed_date;
    @Qualifier("order_updated_at")
    @JsonProperty("order_updated_at")
    public String order_updated_at;
    @NotBlank
    @Qualifier("customer_id")
    @JsonProperty("customer_id")
    public int customer_id;
    @Qualifier("order_status")
    @JsonProperty("order_status")
    public int order_status;
    @NotBlank
    @Qualifier("retailer_id")
    @JsonProperty("retailer_id")
    public int retailer_id;
    @NotBlank
    @Qualifier("order_total")
    @JsonProperty("order_total")
    public String order_total;
    @NotBlank
    @Qualifier("order_grand_total")
    @JsonProperty("order_grand_total")
    public String order_grand_total;
    
    
}

