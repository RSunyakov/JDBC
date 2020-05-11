package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.Site;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteDto {
    private String store_name;

    public SiteDto fromSite(Site site){
        return SiteDto.builder().store_name(site.getStore_name()).build();
    }
}
