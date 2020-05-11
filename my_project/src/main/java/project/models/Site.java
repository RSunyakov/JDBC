package project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "site_table")
@ToString
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String store_name;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Url> saleUrls;
    private String productsTag;
    private String productItemTag;
    private String siteUrl;
    private String productLinkTag;
    private String productLink;
    private String productImgTag;
    private String productImg;
    private String productName;
    private String newPriceTag;
    private String oldPriceTag;
    private Boolean hasJS;

    @Override
    public String toString() {
        return String.valueOf(this.hashCode());
    }
}
