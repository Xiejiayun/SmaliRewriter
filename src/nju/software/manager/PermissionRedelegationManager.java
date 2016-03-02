package nju.software.manager;

import java.util.Set;

/**
 * 权限再授权攻击检测方法
 *
 * Created by Xie on 2016/3/2.
 */
public class PermissionRedelegationManager {

    /**
     * 由被调用者权限集合和调用者权限集合做差集，如果剩下的权限集合中存在元素，那么表明发生了权限再授权攻击
     * @param callerPermissions 调用者权限
     * @param calleePermissions 被调用者权限
     * @return  是否权限再授权攻击
     */
    public boolean isPermissionRedelegation(Set<String> callerPermissions, Set<String> calleePermissions) {
        boolean isPermissionRedelegation = false;
        calleePermissions.removeAll(calleePermissions);
        if (calleePermissions.size() > 0)
            isPermissionRedelegation = true;
        return isPermissionRedelegation;
    }
}
