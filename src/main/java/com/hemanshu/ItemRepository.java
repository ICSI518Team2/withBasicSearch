package com.hemanshu;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Items, Integer> {
	
	public Items findByItemIDAndItemname(String item,String itemname);
	
}
