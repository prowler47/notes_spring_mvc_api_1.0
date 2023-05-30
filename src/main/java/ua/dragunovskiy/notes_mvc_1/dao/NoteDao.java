package ua.dragunovskiy.notes_mvc_1.dao;

import java.util.List;

public interface NoteDao<Integer, Note> {
    List<Note> getAllNotes(int id);
    void addNote(int userId, Note note);
    void updateNote(int userId, Note note);
    void deleteNote(int userId, int noteId);
}
