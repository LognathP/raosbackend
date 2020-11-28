package com.raos.model;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Offers {

	int offer_id;
	String offer_name;
	String offer_code;
	Date offer_expiry;
	int offer_status;
}
