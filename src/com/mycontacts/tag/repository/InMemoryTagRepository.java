/**
 * Use Case 11 - Create and Manage Tags
 *
 * In-memory repository for tags. Stores: userEmail -> Set of tags.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.tag.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class InMemoryTagRepository implements TagRepository {
    private final Map<String, Set<String>> tagsByUserEmail = new ConcurrentHashMap<>();

    @Override
    public boolean addTagForUser(String userEmail, String tag) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            return false;
        }
        if (tag == null || tag.trim().isEmpty()) {
            return false;
        }

        String key = userEmail.toLowerCase();
        Set<String> tags = tagsByUserEmail.computeIfAbsent(
                key,
                k -> new ConcurrentSkipListSet<>(String.CASE_INSENSITIVE_ORDER)
        );

        return tags.add(tag.trim());
    }

    @Override
    public List<String> getTagsForUser(String userEmail) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            return List.of();
        }

        Set<String> tags = tagsByUserEmail.get(userEmail.toLowerCase());
        if (tags == null || tags.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(tags);
    }

    @Override
    public boolean deleteTagForUser(String userEmail, String tag) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            return false;
        }
        if (tag == null || tag.trim().isEmpty()) {
            return false;
        }

        Set<String> tags = tagsByUserEmail.get(userEmail.toLowerCase());
        if (tags == null || tags.isEmpty()) {
            return false;
        }

        return tags.remove(tag.trim());
    }
}
