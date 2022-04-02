package ices.fashion.entity.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColComment {
    private Integer id;
    private Integer vid;    //version id
    private String content;
    private Integer status;
    private Timestamp createTime;
    private Integer uid;
    private String canvas;
}
