package project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favourite_products")
public class FavouriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;
    private String productName;
    @Column(unique = true)
    private String productUrl;
    private String imgUrl;
    private String newPrice;
    private String oldPrice;
    private String who;

    public static FavouriteProduct fromProduct(Product product, Long userId){
        String who = "";
        if (product.getWho().equals(Who.WOMAN)){
            who = "Женская";
        } else if (product.getWho().equals(Who.MAN)){
            who = "Мужская";
        }else {
            who = "Детская";
        }
        return FavouriteProduct.builder()
                .user_id(userId)
                .site(product.getSite())
                .productName(product.getProductName())
                .productUrl(product.getProductUrl())
                .imgUrl(product.getImgUrl())
                .newPrice(product.getNewPrice())
                .oldPrice(product.getOldPrice())
                .who(who)
                .build();
    }
}
