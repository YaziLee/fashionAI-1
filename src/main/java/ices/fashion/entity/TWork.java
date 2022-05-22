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
public class TWork {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String phone;
    private String userName;
    private String workDescription;
    private String category;
    private String coverUrl;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer workDeleted;
    private Integer workShared;
    private String title;
    private String wordDescription;
}
