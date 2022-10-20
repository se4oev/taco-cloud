package ru.se4oev.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.se4oev.tacocloud.repository.IngredientRepository;
import ru.se4oev.tacocloud.entity.Ingredient;
import ru.se4oev.tacocloud.entity.Ingredient.Type;
import ru.se4oev.tacocloud.entity.Taco;
import ru.se4oev.tacocloud.entity.TacoOrder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors())
            return "design";

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute
    public Taco taco() {
        return new Taco();
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        List<Ingredient> list = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            if (ingredient.getType().equals(type))
                list.add(ingredient);
        });
        return list;
    }

}
