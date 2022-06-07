package ices.fashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TComment {

    private Integer id;
    private String uid;
    private String userName;
    private String content;
    private Timestamp createTime;
    private Integer commentDeleted;
    private Integer wid;
}
