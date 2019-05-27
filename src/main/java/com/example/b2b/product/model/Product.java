package com.example.b2b.product.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Product Main Entity
 *
 * @author  atawakol
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "Product_PK_Generator")
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Long productId;

    @Column(nullable = false)
    @NotNull
    private Long vendorUID;

    @Column(unique = true, nullable = false, length = 60)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private String uuid = UUID.randomUUID().toString();

    @Size(min=3, max=255, message="The length of the product name has to be between 3 and 255")
    @NotNull
    private String title;

    @Size(min=3, max=255, message="The length of the product description has to be between 3 and 255")
    private String description;

    @Digits(integer = 5, fraction = 2, message = "The price has to be of maximum 5 integral digits and maximum of 2 fractional digits")
    @Min(value = 0, message = "Price can't be less than zero")
    @Column(precision =5, scale = 2)
    private BigDecimal price;

    @Column(length = 255)
    private String imagePath;

    @Min(value = 0, message = "The number of views can't be less than zero")
    private long numberOfViews;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="PRODUCTID", nullable=false)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<DietaryFlags> dietaryFlags = new HashSet<>();


    /**
     * Custom method to add set of Dietary Flags.
     * It handles the reverse relation of the entities so the objects remains in consistent state all the time.
     * It adds the flags to the product and vice verse.
     *
     * @param dietaryFlags
     */
    public void setDietaryFlags(Set<DietaryFlags> dietaryFlags) {
        this.dietaryFlags = dietaryFlags;
        dietaryFlags.forEach(t->t.setProduct(this));
    }

    /**
     * adds a dietaryFlag to this product and handle the reverse relation.
     *
     * @param dietaryFlag
     */
    public void addDietaryFlags(DietaryFlags dietaryFlag) {
        this.getDietaryFlags().add(dietaryFlag);
        dietaryFlag.setProduct(this);
    }

}
