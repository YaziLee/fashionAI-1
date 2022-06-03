package ices.fashion.service.dto.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColVersionDto {
    private Integer id;
    private String canvas;
    private String backCanvas;
    private String image;
    private String backImage;
    private Integer parent_version;
    private String create_time;
    private Integer saved;
    private ArrayList<Integer> childrenIndex;
}
