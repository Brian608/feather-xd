package org.feather.xd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.enums.AddressStatusEnum;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.model.AddressDO;
import org.feather.xd.mapper.AddressMapper;
import org.feather.xd.model.LoginUser;
import org.feather.xd.request.AddressAddRequest;
import org.feather.xd.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.util.ParamCheckUtil;
import org.feather.xd.vo.AddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 电商-公司收发货地址表 服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@Slf4j
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO> implements IAddressService {

    @Override
    public Boolean add(AddressAddRequest request) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        addressDO.setUserId(loginUser.getId());

        BeanUtils.copyProperties(request,addressDO);
        //是否有默认收货地址
        if(addressDO.getDefaultStatus() == AddressStatusEnum.DEFAULT_STATUS.getStatus()){
            //查找数据库是否有默认地址
            AddressDO defaultAddressDO = this.getOne (new QueryWrapper<AddressDO>()
                    .eq("user_id",loginUser.getId())
                    .eq("default_status",AddressStatusEnum.DEFAULT_STATUS.getStatus()));

            if(defaultAddressDO != null){
                //修改为非默认收货地址
                defaultAddressDO.setDefaultStatus(AddressStatusEnum.COMMON_STATUS.getStatus());
                this.update(defaultAddressDO,new QueryWrapper<AddressDO>().eq("id",defaultAddressDO.getId()));
            }
        }

        boolean save = this.save(addressDO);
        log.info("新增收货地址:result={},data={}",save,addressDO);
        return save;
    }

    @Override
    public AddressVO detail(Long id) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();

        AddressDO addressDO = this.getOne(new QueryWrapper<AddressDO>().eq("id",id).eq("user_id",loginUser.getId()));
        ParamCheckUtil.checkObjectNonNull(addressDO,BizCodeEnum.ADDRESS_NO_EXITS);
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO,addressVO);
         return   addressVO;
    }

    @Override
    public Boolean del(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<AddressVO> listUserAllAddress() {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        List<AddressDO> list = this.list(new LambdaQueryWrapper<AddressDO>().eq(AddressDO::getUserId,loginUser.getId()).orderByDesc(AddressDO::getCreateTime));
        return list.stream().map(obj->{
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(obj,addressVO);
            return addressVO;
        }).collect(Collectors.toList());
    }
}
