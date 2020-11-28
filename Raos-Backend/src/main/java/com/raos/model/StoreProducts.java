package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreProducts {

	int product_id;
	String product_name;
	String category_name;
	String subCategory_name;
	int status;
	int discount_flag;
	String imgUrl;
}
