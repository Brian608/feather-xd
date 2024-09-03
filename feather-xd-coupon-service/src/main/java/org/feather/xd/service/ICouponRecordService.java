package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.feather.xd.model.CouponRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.query.CouponRecordQuery;
import org.feather.xd.vo.CouponRecordVO;
import org.feather.xd.vo.CouponVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
public interface ICouponRecordService extends IService<CouponRecordDO> {

    /**
     * description: 分页查询优惠券领取记录
     * @param query
     * @return {@link Page<CouponRecordVO>}
     * @author: feather
     * @since: 2024-09-03 17:32
     **/
    Page<CouponRecordVO> pageCouponRecord(CouponRecordQuery query);


    /**
     * description: 根据id查询详情
     * @param recordId
     * @return {@link CouponRecordVO}
     * @author: feather
     * @since: 2024-09-03 17:41
     **/
    CouponRecordVO findById(long recordId);


}
