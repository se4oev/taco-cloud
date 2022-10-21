package ru.se4oev.tacocloud.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createDate = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToOne
    @JoinColumn(name = "taco_order")
    private TacoOrder tacoOrder;

    private int tacoOrderKey;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @JoinTable(name = "ingredient_ref", joinColumns = @JoinColumn(name = "taco"), inverseJoinColumns = @JoinColumn(name = "ingredient"))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

}
