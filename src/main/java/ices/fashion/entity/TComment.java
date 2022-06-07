package ices.fashion.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TComment {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uid;
    private String userName;
    private String content;
    private Timestamp createTime;
    private Integer commentDeleted;
    private Integer wid;
}
