package com.chenyou.admin.controller;

import com.chenyou.admin.Utils.ToolUtil;
import com.chenyou.admin.domain.SysAuthority;
import com.chenyou.admin.domain.SysManage;
import com.chenyou.admin.domain.SysRole;
import com.chenyou.admin.service.SysAuthorityService;
import com.chenyou.admin.service.SysManageService;
import com.chenyou.admin.service.SysRoleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shell Li on 2017/11/27.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/authority")
public class SysAuthorityController {

    final static Logger LOG = LoggerFactory.getLogger(SysAuthorityController.class);

    @Autowired
    private SysAuthorityService sysAuthorityService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysManageService sysManageService;

    @RequestMapping(value = "/authlist", method = {RequestMethod.GET})
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("authlist");
        try {
            Long parentId = 0L;
            List<SysAuthority> list = sysAuthorityService.findByParent(parentId);
            mav.addObject("pnode", list);
        } catch (Exception e) {
            LOG.error("authslist error: ", e);
        }
        return mav;
    }

    @RequestMapping(value = "/authsdata", method = {RequestMethod.GET, RequestMethod.POST})
    public String authsData(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "50", required = false) int size) {
        String jsonResult = "";
        try {
            List<SysAuthority> list = sysAuthorityService.findAll(page, size);
            int total = sysAuthorityService.getTotal();
            if (null != list && list.size() > 0) {
                jsonResult = ToolUtil.tableFormat(list, total);
            }
        } catch (Exception e) {
            LOG.error("authsdata error: ", e);
            JSONObject err = new JSONObject();
            err.put("result", e.getMessage());
            jsonResult = err.toString();
        } finally {
            return jsonResult;
        }
    }

    @RequestMapping(value = "/authssave", method = {RequestMethod.POST})
    public String authsSave(HttpServletRequest request) {
        String jsonResult = "success";
        Map<String, String> result = new HashMap<>();
        result.put("result", jsonResult);
        try {
            String authId = request.getParameter("authId"), authName = request.getParameter("authName"),
                    authUrl = request.getParameter("authUrl"), authParent = request.getParameter("authParent"),
                    authSort = request.getParameter("authSort"), authDescript = request.getParameter("authDescription");
            SysAuthority authority;
            if (null != authId && !"".equals(authId)) {
                authority = sysAuthorityService.findOne(Long.valueOf(authId));
            } else {
                authority = new SysAuthority();
            }
            authority.setAuthUrl(authUrl);
            authority.setAuthSort(authSort);
            authority.setAuthParent(Long.valueOf(authParent));
            authority.setAuthName(authName);
            authority.setAuthDescription(authDescript);
            if (authority.getAuthId() == null) {
                sysAuthorityService.insertAuthority(authority);
            } else {
                sysAuthorityService.updateAuthority(authority);
            }

            jsonResult = JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            LOG.error("authssave error: ", e);
            result.put("result", e.getMessage());
            jsonResult = JSONObject.fromObject(result).toString();
        } finally {
            return jsonResult;
        }
    }


    /**
     * 角色列表
     */
    @RequestMapping(value = {"/rolelist"}, method = {RequestMethod.GET})
    public ModelAndView roleList() {
        ModelAndView mav = new ModelAndView("rolelist");
        try {
            List<SysAuthority> list = sysAuthorityService.findAll();
            mav.addObject("pnode", list);
        } catch (Exception e) {
            LOG.error("rolelist error: ", e);
        }
        return mav;
    }

    @RequestMapping(value = "/roledata", method = {RequestMethod.GET})
    public String roleData(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "50", required = false) int size) {
        String jsonResult = "";
        try {
            List<SysRole> list = sysRoleService.findAll(page, size);
            int total = sysRoleService.getTotal();
            if (null != list && list.size() > 0) {
                jsonResult = ToolUtil.tableFormat(list, total);
            }
        } catch (Exception e) {
            LOG.error("roledata error: ", e);
            JSONObject err = new JSONObject();
            err.put("result", e.getMessage());
            jsonResult = err.toString();
        } finally {
            return jsonResult;
        }
    }

    /**
     * 角色保存
     * @param request
     * @return
     */
    @RequestMapping(value = "/rolesave", method = {RequestMethod.POST})
    public String roleSave(HttpServletRequest request) {
        String jsonResult = "success";
        Map<String, String> result = new HashMap<>();
        result.put("result", jsonResult);
        try {
            String roleId = request.getParameter("roleId"), roleName = request.getParameter("roleName"),
                    roleAuths = request.getParameter("roleAuths"), roleVersion = request.getParameter("roleVersion");
            SysRole role;
            if (null != roleId && !"".equals(roleId)) {
                role = sysRoleService.findOne(Long.valueOf(roleId));
                role.setRoleVersion(Long.valueOf(roleVersion));
            } else {
                role = new SysRole();
                role.setRoleVersion(System.currentTimeMillis());
            }
            role.setRoleAuths(roleAuths);
            role.setRoleName(roleName);
            if (role.getRoleId() == null) {
                sysRoleService.insertRole(role);
            } else {
                sysRoleService.updateRole(role);
            }
            jsonResult = JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            LOG.error("rolesave error: ", e);
            result.put("result", e.getMessage());
            jsonResult = JSONObject.fromObject(result).toString();
        }
        return jsonResult;
    }

    /**
     * 管理员列表
     * @return
     */
    @RequestMapping(value = {"/managerlist"}, method = {RequestMethod.GET})
    public ModelAndView manageList() {
        ModelAndView mav = new ModelAndView("managerlist");
        try {
            List<SysRole> list = sysRoleService.findAll();
            mav.addObject("pnode", list);
        } catch (Exception e) {
            LOG.error("managelist error: ", e);
        }
        return mav;
    }

    @RequestMapping(value = {"/managedata"}, method = {RequestMethod.GET})
    public String manageData(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "50", required = false) int size) {
        String jsonResult = "success";
        try {
            List<SysRole> list = sysRoleService.findAll();
            Map<Long, SysRole> map = new HashMap<>();
            if (null != list && list.size() > 0) {
                for (SysRole role : list) {
                    map.put(role.getRoleId(), role);
                }
            }
            Map<String, Object> param = new HashMap<>();
            param.put("page", page);
            param.put("size", size);

            JSONArray array = new JSONArray();
            List<SysManage> manages = sysManageService.findAll(param);
            int total = sysManageService.getTotal();
            if (null != manages && manages.size() > 0) {
                for (SysManage item : manages) {
                    JSONObject object = JSONObject.fromObject(item);
                    Long roleId = item.getManRole();
                    SysRole role = map.get(roleId);
                    if (null != role) {
                        object.put("roleName", role.getRoleName());
                    } else {
                        object.put("roleName", "unknow");
                    }
                    array.add(object);
                }
            }
            jsonResult = ToolUtil.tableFormat(total, size, array);
        } catch (Exception e) {
            LOG.error("managedata error: ", e);
            JSONObject err = new JSONObject();
            err.put("result", e.getMessage());
            jsonResult = err.toString();
        }
        return jsonResult;
    }

    @RequestMapping(value = {"/managesave"}, method = {RequestMethod.POST})
    public String manageSave(HttpServletRequest request) {
        String jsonResult = "success";
        Map<String, String> result = new HashMap<>();
        result.put("result", jsonResult);
        try {
            String manageId = request.getParameter("manId"), manageAccount = request.getParameter("manAccount"),
                    managePassword = request.getParameter("manPasswd"), manageName = request.getParameter("manName"),
                    manageStatus = request.getParameter("manStatus"), manageRole = request.getParameter("manRole"),
                    manageVersion = request.getParameter("manVersion");
            SysManage manage;
            if (null != manageId && !"".equals(manageId)) {
                manage = sysManageService.findOne(Long.valueOf(manageId));
                manage.setManVersion(Long.valueOf(manageVersion));
            } else {
                manage = new SysManage();
                manage.setManVersion(System.currentTimeMillis());
                manage.setManAddTime(new Date());
            }
            if (null != managePassword && !"".equals(managePassword)) {
                PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
                manage.setManPasswd(passwordEncoder.encode(managePassword));
            }
            if (null == manageStatus || "".equals(manageStatus)) {
                manageStatus = "Y";
            }
            manage.setManAccount(manageAccount);
            manage.setManName(manageName);
            manage.setManRole(Long.valueOf(manageRole));
            manage.setManStatus(manageStatus);
            if (manage.getManId() == null) {
                sysManageService.insertManager(manage);
            } else {
                sysManageService.updateManager(manage);
            }

            jsonResult = JSONObject.fromObject(result).toString();
        } catch (Exception e) {
            LOG.error("rolesave error: ", e);
            result.put("result", e.getMessage());
            jsonResult = JSONObject.fromObject(result).toString();
        }
        return jsonResult;
    }
}
