package edu.spring.td1.controllers;

import edu.spring.td1.models.Category
import edu.spring.td1.services.UiMessage
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@SessionAttributes("categories")
public class ItemsControllers {

    private fun getCategoryByLibelle(libelle:String,categories:Set<Category>):Category?=categories.find { it.libelle==libelle }

    @get:ModelAttribute("categories")
    val categories: Set<Category>
        get() {

            val categories = HashSet<Category>()
            val cat = Category("Foo")
            cat.addAll("a", "b", "c")
            //categories.add(Category("Foo"))
            categories.add(cat)
            return categories
        }

    @RequestMapping("/")
    fun indexAction(
            @RequestAttribute("msg") msg:UiMessage.Message?
    ):String{
        return "index"
    }

    @GetMapping("/new/{categorie}")
    fun newAction(@PathVariable categorie:String):String{
        return "newForm"
    }



    @PostMapping("/addNew/{categorie}")
    fun addNewAction(
            @ModelAttribute("nom") item:String,
            @PathVariable("categorie") libelle: String,
            @SessionAttribute("categories") categories:HashSet<Category>,
            attrs: RedirectAttributes
    ):RedirectView{
        val categorie=getCategoryByLibelle(libelle,categories)
        var msg:UiMessage.Message
        if(categorie?.add(item)?:false){
            msg= UiMessage.success("Ajout", "{$item.nom} ajouté aux items de la catégorie $libelle.")

        }else{
            msg=UiMessage.error("Ajout", "$item existe déjà !")

        }
        attrs.addFlashAttribute("msg", msg)
        return RedirectView("/")
    }

    @GetMapping("/delete/{nom}")
    fun deleteAction(
            @PathVariable("nom") nom:String,
            @SessionAttribute("categories") items:HashSet<Category>,
            attrs: RedirectAttributes
    ):RedirectView{
        if(items.remove(Category(nom))){
            attrs.addFlashAttribute("msg",
                    UiMessage.success("Suppression", "$nom supprimé."))
        }
        return RedirectView("/")
    }


}