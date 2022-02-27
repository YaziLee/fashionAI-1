package ices.fashion.entity.collaborate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColVersion {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String canvas;
    private String image;
    private Integer parentVersion;
    private Timestamp createTime;
    private Timestamp updateTime;
}
