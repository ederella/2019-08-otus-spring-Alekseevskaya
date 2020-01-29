package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import main.dao.BookRepository;

@Controller
public class BookControllerUser {

	private final BookRepository bookRepository;
	
	@Autowired
	public BookControllerUser(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@RequestMapping(value="/library", method=RequestMethod.POST)
	public String userGetReturnBook(@RequestParam(value = "selected", required=false) String bookSelectedId, @RequestParam("button") String button, Model model) {
		if(bookSelectedId !=null && bookSelectedId.length()>0) {
			long id = new Long(bookSelectedId).longValue();
			int count = bookRepository.findById(id).getCount();
			if (button.equalsIgnoreCase("return")) {
				bookRepository.updateBookCountById(id, count + 1);
			} 
			else if (button.equalsIgnoreCase("get")) {
				if(count > 0)
					bookRepository.updateBookCountById(id, count - 1);
			}
		}
		return listPage(model);
	}
	@GetMapping("/library")
	public String listPage(Model model) {
		model.addAttribute("books", bookRepository.findAll());
        return "user/library_list";
	}
	
	@GetMapping("/")
    public String defaultPage() {
        return "index.html";
    }
}
