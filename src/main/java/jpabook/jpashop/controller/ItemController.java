package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new Book());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book book = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,  @ModelAttribute("form") BookForm bookForm) {

//        Book book = new Book();
//        book.setId(bookForm.getId());
//        book.setName(bookForm.getName());
//        book.setPrice(bookForm.getPrice());
//        book.setStockQuantity(bookForm.getStockQuantity());
//        book.setAuthor(bookForm.getAuthor());
//        book.setIsbn(bookForm.getIsbn());
        // 이런식으로 하나씩 set하는것 보다는 book.change() 이런 식으로 entity단에서 의미있는 변경방식을 추천.

        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity()); // 우리는 merge 방식으로 준영속 엔티티를 관리했음

        // 추가적으로 유저가 임의로 저 itemId값을 변경해도 권한이 없도록 만들어줘야한다..
        return "redirect:items";
    }
    /*
    * book은 준영속 엔티티. 영속성 컨텐츠가 더이상 관리하지 않는 엔티티
    * new Book으로 객체를 새로 생성했지만 setId를 사용할 수 있는걸 보니 JPA에 담궜다 온 애임
    * 한번 DB에 저장되어있으니 id값이 설정되어 있음.
    *
    * 문제는 준영속 엔티티(new로 Book을 만든)니까 JPA가 더이상 얘를 관리해주지 않음
    * Transaction이 일어나도, JPA는 얘를 보지 않고 있당..
    * 그럼 나중에 얘를 수정해야되면 어떻게 해야될까?
    *
    * 추후 수정하는 방식은 변경감지와 merge가 있따.
    * */
}







