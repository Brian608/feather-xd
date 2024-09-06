package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.feather.xd.model.BannerDO;
import org.feather.xd.mapper.BannerMapper;
import org.feather.xd.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.vo.BannerVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDO> implements IBannerService {

    @Override
    public List<BannerVO> bannerList() {
        List<BannerDO> bannerDOList = this.list(new LambdaQueryWrapper<BannerDO>().orderByDesc(BannerDO::getWeight));
        return BeanUtil.copyToList(bannerDOList, BannerVO.class);
    }
}
