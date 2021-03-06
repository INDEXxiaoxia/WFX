package com.example.service.impl;

import com.example.mapper.SysModuleMapper;
import com.example.model.SysModule;
import com.example.model.util.ZTreeBean;
import com.example.service.SysModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Override
    public PageInfo<SysModule> findAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<SysModule>(sysModuleMapper.selectAll());
    }

    /**
     * 将数据库中保存的菜单信息，转换为 ztree树型菜单所需的数据结构
     *
     * @return
     */
    @Override
    public List<ZTreeBean> findAllMenus() {
        List<SysModule> moduleList = sysModuleMapper.selectAll();

        List<ZTreeBean> zTreeBeanList = new ArrayList<>();
        for (SysModule sysModule : moduleList) {
            ZTreeBean zTreeBean = new ZTreeBean();
            zTreeBean.setId(sysModule.getModuleCode());
            zTreeBean.setPId(sysModule.getParentModule());
            zTreeBean.setName(sysModule.getModuleName());
            zTreeBean.setOpen("1".equals(sysModule.getExpanded()));//查出来时，直接将值进行转换
            zTreeBeanList.add(zTreeBean);
        }

        return zTreeBeanList;
    }

    @Override
    public List<ZTreeBean> findModuleByRoleId(String roleId) {
        //查询所有给前端的菜单列表
        List<ZTreeBean> allMenus = findAllMenus();
        if (StringUtils.isNotBlank(roleId)) {
            //查询出当前角色下，能看到的菜单列表
            List<SysModule> hasPermByModule = sysModuleMapper.findModuleByRoleId(roleId);

            //循环遍历所有的菜单列表，如果循环中菜单在hasPermByModule，则将checked设置为true
            for (ZTreeBean allMenu : allMenus) {
                //判断当前菜单是否属于这个角色的用户
                if (checkedModulesContainsModule(hasPermByModule, allMenu)) {
                    //设置具备这个权限的菜单 设置为 选中
                    allMenu.setChecked(true);
                    //同时将这个菜单的父级菜单设置为 展开。找到它的父级菜单对象，再将这个对象的open设置为true
                    ZTreeBean parentTree = findParentZTreeByParentCode(allMenus, allMenu.getPId());
                    if (parentTree != null) {
                        parentTree.setOpen(true);
                    }
                }
            }
        }

        return allMenus;
    }

    /**
     * 根据当前设置选中的菜单对象，来找它的父级菜单
     *
     * @param allMenus
     * @param parentModule
     * @return
     */
    private ZTreeBean findParentZTreeByParentCode(List<ZTreeBean> allMenus, String parentModule) {
        for (ZTreeBean allMenu : allMenus) {
            if (parentModule.equals(allMenu.getId())) {
                return allMenu;
            }
        }
        return null;
    }

    //自定义方法：判断参数2是否在参数1的集合中
    private boolean checkedModulesContainsModule(List<SysModule> list, ZTreeBean zTreeBean) {
        for (SysModule sysModule : list) {
            if (sysModule.getModuleCode().equals(zTreeBean.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SysModule> findModuleListByUserId(String userId) {
        // 根据当前用户ID查询 角色权限范围内的 菜单列表
        List<SysModule> moduleList =  sysModuleMapper.findModuleByUserId(userId);
        //对查询出来的菜单列表进行结构改造
        //1.先根据顶层菜单的ID查询直接子菜单
        List<SysModule> firstMenus = findModuleListByParentModule(moduleList,"0101");// 这是查询的第1层菜单
        //2.再循环每一个子菜单 ，查询子菜单的下一级子菜单
        for (SysModule firstMenu : firstMenus) {// 查询的是第2层菜单
            List<SysModule> childModules = findModuleListByParentModule(moduleList,firstMenu.getModuleCode());
            //将查询出来的子菜单，赋给当前的父菜单
            firstMenu.setChildModules(childModules);
        }

        return firstMenus;
    }

    /**
     * 工具类：
     *  根据父级菜单ID，查询父级下的所有子级菜单
     * @param list
     * @param parentModule
     * @return
     */
    private List<SysModule> findModuleListByParentModule(List<SysModule> list,String parentModule){
        List<SysModule> childList = new ArrayList<SysModule>();//保存查询出来的子级菜单的结果集

        for (SysModule sysModule : list) {
            if (sysModule.getParentModule().equals(parentModule)){
                childList.add(sysModule);
            }
        }
        return childList;
    }
}
