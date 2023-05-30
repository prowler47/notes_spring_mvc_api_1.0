package ua.dragunovskiy.notes_mvc_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.notes_mvc_1.dao.NoteDao;
import ua.dragunovskiy.notes_mvc_1.entity.Note;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService<Integer, Note> {

    @Autowired
    NoteDao<Integer, Note> noteDao;

    @Override
    public List<Note> getAllNotes(int id) {
        return noteDao.getAllNotes(id);
    }

    @Override
    public void addNote(int userId, Note note) {
        noteDao.addNote(userId, note);
    }

    @Override
    public void updateNote(int userId, Note note) {
        noteDao.updateNote(userId, note);
    }

    @Override
    public void deleteNote(int userId, int noteId) {
        noteDao.deleteNote(userId, noteId);
    }

    @Override
    public Note findNoteById(List<Note> noteList, int noteId) {
        for (Note note : noteList) {
            if (note.getId() == noteId) {
                return note;
            }
        }
        return null;
    }
}
