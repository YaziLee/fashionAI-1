package ices.fashion.service.recommendation;

import ices.fashion.service.recommendation.dto.MaterialPageDto;
//import sun.nio.cs.ext.IBM037;

import java.io.IOException;
import java.util.List;

public interface RecService {
    /**
     * 搭配推荐相关
     */
    List<MaterialPageDto> reqRecommendations(List<Integer> itemIds, String matchType)throws IOException;
}
