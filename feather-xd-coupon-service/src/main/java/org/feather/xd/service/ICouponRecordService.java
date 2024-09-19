package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.model.CouponRecordDO;
import org.feather.xd.query.CouponRecordQuery;
import org.feather.xd.request.LockCouponRequest;
import org.feather.xd.vo.CouponRecordVO;

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

    /**
     * description: 锁定优惠券
     * 1:锁定优惠卷记录
     * 2：task 表记录
     * 3：发送延迟消息
     * @param request
     * @return
     * @author: feather
     * @since: 2024-09-18 15:55
     **/
   void lockCouponRecords(LockCouponRequest request);


}
