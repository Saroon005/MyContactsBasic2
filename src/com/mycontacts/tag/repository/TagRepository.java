/**
 * Use Case 11 - Create and Manage Tags
 *
 * Small repository contract for storing and retrieving tags per user.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.tag.repository;

import java.util.List;

public interface TagRepository {
    boolean addTagForUser(String userEmail, String tag);

    List<String> getTagsForUser(String userEmail);

    boolean deleteTagForUser(String userEmail, String tag);
}
