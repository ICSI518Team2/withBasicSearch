package com.hemanshu;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;
	
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository=itemRepository;
	}
	
	
	public List<Item> showAllitems(){
		List<Item> items = new ArrayList<Item>();
		for(Item item : itemRepository.findAll()) {
			items.add(item);
		}
		return items;
	}
	
	public void deleteMyitem(int ID) {
		itemRepository.deleteById(ID);
	}

	
	
}
