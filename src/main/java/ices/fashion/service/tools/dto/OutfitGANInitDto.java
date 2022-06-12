package ices.fashion.service.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitGANInitDto {

    private List<String> upperList;
    private List<String> shoesList;
    private List<String> lowerList;
    private List<String> bagList;
}
