package ices.fashion.entity.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColWorkDescription {
    private String frontImg;
    private String backImg;
    private ArrayList<ColIntroduce> introduce;
    private ColVersion version;
}
