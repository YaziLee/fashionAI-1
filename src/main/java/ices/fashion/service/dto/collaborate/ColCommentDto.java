package ices.fashion.service.dto.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColCommentDto {
    private Integer uid;
    private String username;
    private Integer cid;
    private Integer vid;
    private List<String> content;
    private Integer status;
    private List<String> createTime;
    private List<String> canvas;
}
