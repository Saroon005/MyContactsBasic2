/**
 * Use Case 11 - Create and Manage Tags
 *
 * Service for creating, listing, and deleting user-defined tags.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.tag.service;

import java.util.List;

import com.mycontacts.tag.repository.TagRepository;
import com.mycontacts.user.exception.ValidationException;

public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean createTag(String ownerEmail, String tag) throws ValidationException {
        if (ownerEmail == null || ownerEmail.trim().isEmpty()) {
            throw new ValidationException("Owner email is required.");
        }
        if (tag == null || tag.trim().isEmpty()) {
            throw new ValidationException("Tag is required.");
        }

        return tagRepository.addTagForUser(ownerEmail, tag);
    }

    public List<String> listTags(String ownerEmail) throws ValidationException {
        if (ownerEmail == null || ownerEmail.trim().isEmpty()) {
            throw new ValidationException("Owner email is required.");
        }

        return tagRepository.getTagsForUser(ownerEmail);
    }

    public boolean deleteTag(String ownerEmail, String tag) throws ValidationException {
        if (ownerEmail == null || ownerEmail.trim().isEmpty()) {
            throw new ValidationException("Owner email is required.");
        }
        if (tag == null || tag.trim().isEmpty()) {
            throw new ValidationException("Tag is required.");
        }

        return tagRepository.deleteTagForUser(ownerEmail, tag);
    }
}
