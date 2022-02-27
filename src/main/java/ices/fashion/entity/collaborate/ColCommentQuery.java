package ices.fashion.entity.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColCommentQuery {
    private Integer uid;
    private String username;
    private Integer cid;
    private Integer vid;
    private String content;
    private Integer status;
    private Timestamp createTime;
    private String canvas;

}
