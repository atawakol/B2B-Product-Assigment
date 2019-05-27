package com.example.b2b.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Dietary Flags that are assigned to each product.
 *
 * This class is based on assumption that dietary flags is NOT fixed list.
 * Therefore, each product will has its own set of flags that rapidly dynamically change.
 *
 * However, if the flags are fixed list and don't change frequently, or if the list has to be of specific static values
 * then we could have created a lookup table for flags and map a Many to Many relationship with product.
 *
 *
 * @author atawakol
 */
@Entity
@Data
@NoArgsConstructor
public class DietaryFlags {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "Dietary_PK_Generator")
    @EqualsAndHashCode.Exclude
    private Long dietaryId;

    private String dietaryFlagName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCTID", nullable=false, insertable = false, updatable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

}
