package com.chenyou.admin.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/8.
 */

public class SysRole implements Serializable {
    private Long roleId;
    private String roleName;
    private String roleAuths;
    private Long roleVersion;

    public SysRole() {
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleAuths() {
        return roleAuths;
    }

    public void setRoleAuths(String roleAuths) {
        this.roleAuths = roleAuths;
    }

    public Long getRoleVersion() {
        return roleVersion;
    }

    public void setRoleVersion(Long roleVersion) {
        this.roleVersion = roleVersion;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleAuths='" + roleAuths + '\'' +
                ", roleVersion=" + roleVersion +
                '}';
    }
}
