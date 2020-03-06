package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.bgm.service.impl.PubNoticeTrgtServiceImpl;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysRoleFuncRel;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.mapper.SysRoleMapper;
import com.scrcu.sys.service.SysRoleFuncRelService;
import com.scrcu.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 角色信息接口实现层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 21:58
 */
@Service("sysRoleServiceImpl")
@Transactional
public class SysRoleServiceImpl
        extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
	private PubNoticeTrgtServiceImpl pubNoticeTrgtService;
    @Autowired
    private SysRoleFuncRelService sysRoleFuncRelService;

    @Override
    public boolean insert(SysRole sysRole) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.insert(sysRole);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String roleId) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.deleteById(roleId);
            if (count > 0) {
                bool = true;
            }
            this.baseMapper.delRoleFuncByRoleId(roleId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysRole sysRole) {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysRole);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        return bool;
    }

    @Override
    public SysRole getById(String roleId) throws Exception {
        SysRole sysRole;
        try {
            sysRole = this.baseMapper.selectById(roleId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysRole;
    }

    @Override
    public IPage getByPage(Page<SysRole> page, SysRole sysRole) throws Exception {
        IPage iPage;
        try {
            QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
            if (!CommonUtil.isEmpty(sysRole.getRoleNm())) {
                queryWrapper.like("role_nm", sysRole.getRoleNm());
            }
            if (!CommonUtil.isEmpty(sysRole.getRoleHrchCd())) {
                queryWrapper.eq("role_hrch_cd", sysRole.getRoleHrchCd());
            }
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public boolean isExists(String roleId) throws Exception {
        boolean bool = false;
        try {
            SysRole sysRole = this.baseMapper.selectById(roleId);
            if (null != sysRole) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean saveRoleFuncRel(String roleId, List<String> funcIds) throws Exception {
        if (null == roleId || null == funcIds) {
            return false;
        }
        boolean bool = false;
        int count = 1;
        try {
            //-删除角色分配的功能菜单
            this.baseMapper.delRoleFuncByRoleId(roleId);
            //-新增角色分配的功能菜单
            List<SysRoleFuncRel> relList = new ArrayList<>();
            for (String funcId : funcIds) {
                SysRoleFuncRel rel = new SysRoleFuncRel();
                rel.setId(Integer.parseInt(sysRoleFuncRelService.selectMaxId()) + count);
                rel.setFuncId(funcId);
                rel.setRoleId(roleId);
                relList.add(rel);
                count ++;
            }
            //-批量新增
            sysRoleFuncRelService.insertRoleFuncs(relList);
            if (count > 1) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }


    @Override
    public List<SysRole> getRoleListByUserId(String userId) throws Exception {
        List<SysRole> roleList;
        try {
            roleList = this.baseMapper.getRoleListByUserId(userId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return roleList;
    }

    @Override
    public List<SysRole> getRoleListByLoginUser(List<SysRole> roleList) throws Exception {
        List<SysRole> sysRoleList;
        try {
            String roles = "";
            for (SysRole sysRole : roleList) {
                roles += "'" + sysRole.getRoleId() + "',";
            }
            if (roles.contains("ADMIN")) {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.ne("role_id", "ADMIN");
                sysRoleList = this.baseMapper.selectList(queryWrapper);
            } else {
                roles = roles.substring(0,roles.lastIndexOf(","));
                sysRoleList = this.baseMapper.getRoleListByLoginUser(roles);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysRoleList;
    }

	@Override
    public List<DataList> getRoleListWithPubNotice(String pubNoticeId) {
        List<SysRole> roles = this.baseMapper.getRoleListWithPubNotice(pubNoticeId);
        return getDataList(roles);
    }

    @Override
    public List<DataList> getRoleListNotInPubNotice(String pubNoticeId) {
        List<SysRole> roles = this.baseMapper.getRoleListNotInPubNotice(pubNoticeId);
        return getDataList(roles);
    }

    private List<DataList> getDataList(List<SysRole> roles) {
        List<DataList> result = new ArrayList<>(roles.size());
        DataList dataList;
        for (SysRole role : roles) {
            dataList = new DataList();
            dataList.setText(role.getRoleNm());
            dataList.setValue(role.getRoleId());
            result.add(dataList);
        }
        return result;
    }

    @Override
    public DataGrid getShowRoleUser(PageParameters pageParameters, SysUser sysUser, SysRole sysRole){
        Page<SysUser> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try{
            page.setRecords(this.baseMapper.getSysUserByRole(page,sysUser,sysRole));
            List<SysUser> list = page.getRecords();
            for (SysUser user : list){
                user.setStus(EhcacheUtil.getSingleSysDictryCdByCache(user.getStus(),"SYS05").getDictryNm());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("用户组列表：用户对应用户角色列表查询失败");
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public AjaxResult isExistUserUnderRole(String roleId) {
        AjaxResult result = null;
        try{
            List<SysUser> list = this.baseMapper.getUserUnderRole(roleId);
            if (list != null && list.size() > 0){
                result = new AjaxResult(false,"","","");
            }else{
                result = new AjaxResult(true,"","","");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
