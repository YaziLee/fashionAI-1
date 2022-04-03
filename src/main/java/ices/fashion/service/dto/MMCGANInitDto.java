package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MMCGANInitDto {

    private List<String> topList;
    private List<String> jeansList;
    private List<String> jacketList;
    private List<String> pantsList;
    private List<String> shortsList;
    private List<String> skirtList;
    private List<String> outerwearList;
    private List<String> sweatshirtHoodyList;
}
