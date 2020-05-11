package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {
    private Product data;
}
