package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenderInitDto {

    private List<String> jacketList;
    private List<String> topList;
    private List<String> jeansList;
    private List<String> shortsList;
    private List<String> skirtList;
    private List<String> bagList;
    private List<String> hatList;
}
