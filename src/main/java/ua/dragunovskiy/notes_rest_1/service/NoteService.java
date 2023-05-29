package ua.dragunovskiy.notes_rest_1.service;

import ua.dragunovskiy.notes_rest_1.entity.Note;

import java.util.List;

public interface NoteService<Integer, Note> {
    List<Note> getAllNotes(int id);
    void addNote(int userId, Note note);
    void updateNote(int userId, Note note);
    void deleteNote(int userId, int noteId);
    Note findNoteById(List<Note> noteList, int noteId);
}
