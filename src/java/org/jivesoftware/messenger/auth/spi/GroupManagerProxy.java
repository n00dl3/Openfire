/**
 * $RCSfile$
 * $Revision$
 * $Date$
 *
 * Copyright (C) 1999-2003 CoolServlets, Inc. All rights reserved.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */
package org.jivesoftware.messenger.auth.spi;

import org.jivesoftware.util.BasicResultFilter;
import org.jivesoftware.util.JiveConstants;
import org.jivesoftware.messenger.Entity;
import org.jivesoftware.messenger.auth.*;
import org.jivesoftware.messenger.user.spi.UserGroupIteratorProxy;
import java.util.Iterator;

/**
 * Protection proxy for the GroupManager class. It restricts access to
 * protected methods by throwing UnauthorizedExceptions when necessary.
 *
 * @author Iain Shigeoka
 * @see org.jivesoftware.messenger.auth.GroupManager
 */
public class GroupManagerProxy implements GroupManager {

    private GroupManager groupManager;
    private AuthToken auth;
    private Permissions permissions;

    /**
     * Creates a new GroupManagerProxy.
     */
    public GroupManagerProxy(GroupManager groupManager, AuthToken auth, Permissions permissions) {
        this.groupManager = groupManager;
        this.auth = auth;
        this.permissions = permissions;
    }

    public Group createGroup(String name)
            throws UnauthorizedException, GroupAlreadyExistsException {
        if (permissions.hasPermission(Permissions.SYSTEM_ADMIN)) {
            Group group = groupManager.createGroup(name);
            return new GroupProxy(group, auth, permissions);
        }
        else {
            throw new UnauthorizedException();
        }
    }

    public Group getGroup(long groupID) throws GroupNotFoundException {
        Group group = groupManager.getGroup(groupID);
        Permissions groupPermissions = group.getPermissions(auth);
        Permissions newPermissions = new Permissions(permissions, groupPermissions);

        return new GroupProxy(group, auth, newPermissions);
    }

    public Group getGroup(String name) throws GroupNotFoundException {
        Group group = groupManager.getGroup(name);
        Permissions groupPermissions = group.getPermissions(auth);
        Permissions newPermissions =
                new Permissions(permissions, groupPermissions);
        return new GroupProxy(group, auth, newPermissions);
    }

    public void deleteGroup(Group group) throws UnauthorizedException {
        if (permissions.hasPermission(Permissions.SYSTEM_ADMIN)) {
            groupManager.deleteGroup(group);
        }
        else {
            throw new UnauthorizedException();
        }
    }

    public int getGroupCount() {
        return groupManager.getGroupCount();
    }

    public Iterator getGroups() {
        Iterator iterator = groupManager.getGroups();
        return new UserGroupIteratorProxy(JiveConstants.GROUP, iterator, auth, permissions);
    }

    public Iterator getGroups(BasicResultFilter filter) {
        Iterator iterator = groupManager.getGroups(filter);
        return new UserGroupIteratorProxy(JiveConstants.GROUP, iterator, auth, permissions);
    }

    public Iterator getGroups(Entity entity) {
        Iterator iterator = groupManager.getGroups(entity);
        return new UserGroupIteratorProxy(JiveConstants.GROUP, iterator, auth, permissions);
    }
}
