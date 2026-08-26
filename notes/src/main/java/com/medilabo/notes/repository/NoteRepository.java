package com.medilabo.notes.repository;

import com.medilabo.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data Mongo Repository for {@link Note}
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    /**
     * Find all notes for a Patient
     * @param patId patient's identifier
     * @return list of {@link Note}
     */
    List<Note> findByPatId(Long patId);
}
