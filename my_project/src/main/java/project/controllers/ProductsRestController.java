package project.controllers;

import javassist.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.dto.ResponseFavouritesDto;
import project.dto.ResponseProductDto;
import project.dto.ResponseProductsDto;
import project.models.FavouriteProduct;
import project.models.Product;
import project.models.User;
import project.models.Who;
import project.security.details.UserDetailsImpl;
import project.services.FavouriteProductsService;
import project.services.ProductService;
import project.services.UserService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/products-management")
@PreAuthorize("permitAll()")
public class ProductsRestController {


    @Autowired
    private ProductService productService;
    @Autowired
    private FavouriteProductsService favouriteProductsService;


    @GetMapping("/products/pagination")
    public ResponseProductsDto getProducts(@RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size,
                                           @RequestParam("sort") String sort) {
        return ResponseProductsDto.builder()
                .data(productService.getProducts(page, size, sort))
                .build();
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseProductsDto> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return ResponseEntity.ok(ResponseProductsDto.builder()
                .data(productService.findAll())
                .build());
//        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/products/{who}")
    public ResponseEntity<ResponseProductsDto> getAllProductsForSomeone(@PathVariable("who") Who who,
                                                                        @RequestParam(required = false) Integer sort) {
        if (sort != null) {
            if (sort == 2) {
                return ResponseEntity.ok(ResponseProductsDto.builder()
                        .data(productService.findAllByWhoAndSort(false, who))
                        .build());
            } else {
                return ResponseEntity.ok(ResponseProductsDto.builder()
                        .data(productService.findAllByWhoAndSort(true, who))
                        .build());
            }
        } else {
            return ResponseEntity.ok(ResponseProductsDto.builder()
                    .data(productService.findAllByWho(who))
                    .build());
        }
//        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/products/{who}/{store-id}")
    public ResponseEntity<ResponseProductsDto> getAllProductsForSomeoneFromSomeStore(@PathVariable("who") Who who,
                                                                                     @PathVariable("store-id") Long storeId,
                                                                                     @RequestParam(required = false) Integer sort) {
        if (sort != null) {
            if (sort == 2) {
                return ResponseEntity.ok(ResponseProductsDto.builder()
                        .data(productService.findAllByWhoAndStoreAndSort(false, who, storeId))
                        .build());
            } else {
                return ResponseEntity.ok(ResponseProductsDto.builder()
                        .data(productService.findAllByWhoAndStoreAndSort(true, who, storeId))
                        .build());
            }
        } else {
            return ResponseEntity.ok(ResponseProductsDto.builder()
                    .data(productService.findAllByStoreAndWho(storeId, who))
                    .build());
        }
//        return ResponseEntity.ok(productService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/products/favourite")
    public ResponseEntity<String> addFavourite(@RequestParam(name = "product") Long productId, Authentication auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Add favourite: " + authentication);
        System.out.println("Add favourite 2: " + auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        Optional<Product> productCandidate = productService.find(productId);
        if (productCandidate.isPresent()) {
            Long id = favouriteProductsService.save(FavouriteProduct.fromProduct(productCandidate.get(), userDetails.getUser().getId()));
            if (id == 0L){
                return ResponseEntity.ok("Товар уже находится в избранном");
            }
            return ResponseEntity.ok("Товар успешно добавлен");
        } else {
            return ResponseEntity.status(404).body("Товар не найден");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/products/favourite")
    public ResponseEntity<ResponseFavouritesDto> getFavourites() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Get favourite: " + authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        return ResponseEntity.ok(ResponseFavouritesDto.builder()
                .data(favouriteProductsService.findAll(userDetails.getUser().getId()))
                .build());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/products/favourite")
    public ResponseEntity<String> deleteFavourite(@RequestParam(name = "product") Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Delete favourite: " + authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        boolean response = favouriteProductsService.delete(productId, userDetails.getUser().getId());
        return response ? ResponseEntity.ok("Товар успешно удален") : ResponseEntity.status(404).body("Товар не найден");
    }
}
