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
package org.jivesoftware.messenger.user;

import org.jivesoftware.util.Cacheable;

/**
 * <p>Represents a persistently stored roster item.</p>
 * <p/>
 * <p>The interface is primarily a marker interface to differentiate
 * the roster information passed around via XMPP XML roster packets,
 * and the persistently stored roster associated with a particular account.</p>
 *
 * @author Iain Shigeoka
 */
public interface CachedRosterItem extends RosterItem, Cacheable {
    /**
     * <p>Obtain the roster ID associated with this particular roster item.</p>
     * <p/>
     * <p>Databases can use the roster ID as the key in locating roster items.</p>
     *
     * @return The roster ID
     */
    public long getID();

    /**
     * <p>Update the cached item as a copy of the given item.</p>
     * <p/>
     * <p>A convenience for getting the item and setting each attribute.</p>
     *
     * @param item The item who's settings will be copied into the cached copy
     */
    void setAsCopyOf(RosterItem item);
}
