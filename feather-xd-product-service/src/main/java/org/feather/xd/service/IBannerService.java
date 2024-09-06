package org.feather.xd.service;

import org.feather.xd.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.vo.BannerVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
public interface IBannerService extends IService<BannerDO> {

    /**
     * description: 轮播图列表
     * @param
     * @return {@link List<BannerVO>}
     * @author: feather
     * @since: 2024-09-06 11:44
     **/
    List<BannerVO> bannerList();

}
